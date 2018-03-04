package org.knowm.xchange.idex

import org.apache.commons.codec.binary.*
import org.knowm.xchange.currency.*
import org.knowm.xchange.currency.Currency
import org.knowm.xchange.dto.*
import org.knowm.xchange.dto.Order.OrderType.*
import org.knowm.xchange.dto.marketdata.*
import org.knowm.xchange.dto.trade.*
import org.knowm.xchange.exceptions.*
import org.knowm.xchange.idex.dto.*
import org.knowm.xchange.idex.service.*
import org.knowm.xchange.idex.util.*
import org.knowm.xchange.service.trade.*
import org.knowm.xchange.service.trade.params.*
import org.knowm.xchange.service.trade.params.orders.*
import org.knowm.xchange.utils.*
import org.web3j.crypto.*
import org.web3j.crypto.Hash.*
import org.web3j.crypto.Sign.*
import java.math.*
import java.math.BigDecimal.*
import java.util.*

class IdexTradeService(val idexExchange: IdexExchange) : TradeService, TradeApi() {
    val IdexContractAddress by lazy { contractAddress().address }
    val apiKey inline get() = idexExchange.exchangeSpecification.apiKey
    val idexServerNonce get() = nextNonce(NextNonceReq().address(apiKey)).nonce


    init {

        if (IdexExchange.debugMe) {
            apiClient = ApiClient()
            IdexExchange.setupDebug(apiClient)
        }
    }


    override fun cancelOrder(orderParams: CancelOrderParams): Boolean = when (orderParams) {
        is IdexCancelOrderParams -> cancel(orderParams).success!!.toInt() == 1
        else -> throw ApiException("cancel order requires " + IdexCancelOrderParams::class.java.canonicalName)
    }

    /**returns OrderHash so you can fetch it and cancel it...  but there is a OrderNumber that you can intercept if you need to.
     */
    override fun placeLimitOrder(placeOrder: LimitOrder): String {
        val type = placeOrder.type
        val baseCurrency = placeOrder.currencyPair.base
        val counterCurrency = placeOrder.currencyPair.counter
        val originalAmount = placeOrder.originalAmount
        val limitPrice = placeOrder.limitPrice
        val orderReq = createNormalizedLimitOrderReq(baseCurrency, counterCurrency, type, limitPrice, originalAmount,
                                                     expires = 100000.toBigInteger())

        return order(orderReq).orderHash
    }


    fun createNormalizedLimitOrderReq(baseCurrency: Currency,
                                      counterCurrency: Currency,
                                      type: Order.OrderType, limitPrice: BigDecimal,
                                      originalAmount: BigDecimal,
                                      contractAddress: String = contractAddress().address,
                                      nonce: BigInteger = idexServerNonce,
                                      expires: BigInteger = 100000.toBigInteger()): OrderReq {
        idexExchange.exchangeMetaData.currencies[baseCurrency]
        idexExchange.exchangeMetaData.currencies[counterCurrency]


        var c = listOf(baseCurrency,//OMG
                       counterCurrency//ETH
        )
        if (type == ASK) c = c.reversed()

        val buy_currency = idexExchange.exchangeMetaData.currencies[c[0]] as IdexCurrencyMeta
        val sell_currency = idexExchange.exchangeMetaData.currencies[c[1]] as IdexCurrencyMeta


        val divide = originalAmount.divide(limitPrice,
                                           MathContext.DECIMAL128)
        var amount_buy = divide.multiply("1e${buy_currency.decimals.toBigDecimal()}".toBigDecimal(),
                                         MathContext.DECIMAL128).toBigInteger()
        var amount_sell = originalAmount.multiply("1e${sell_currency.decimals.toBigDecimal()}".toBigDecimal(),
                                                  MathContext.DECIMAL128).toBigInteger()


        val buyc = buy_currency.address
        val sellc = sell_currency.address
        val hash_data: List<List<String>> = listOf(
                listOf("contractAddress", contractAddress, "address"),
                listOf("tokenBuy", buyc, "address"),
                listOf("amountBuy", amount_buy.toString(), "uint256"),
                listOf("tokenSell", sellc, "address"),
                listOf("amountSell", amount_sell.toString(), "uint256"),
                listOf("expires", "" + expires, "uint256"),
                listOf("nonce", ""+nonce, "uint256"),
                listOf("address", apiKey, "address")
        )

        val sig = generateSignature(idexExchange.exchangeSpecification.secretKey, hash_data)!!
        val v = sig.v.toByte()
        val r = sig.r
        val s = sig.s
        val orderReq = OrderReq()
                .address(apiKey)
                .nonce( nonce)
                .tokenBuy(buyc)
                .amountBuy(amount_buy.toString())
                .tokenSell(sellc)
                .amountSell(amount_sell.toString())
                .expires(expires)
                .r("0x" + Hex.encodeHexString(r))
                .s("0x" + Hex.encodeHexString(s))
                .v(v.toInt().toBigInteger())


        return orderReq
    }


    override fun createOpenOrdersParams() = OpenOrdersParams { it is IdexLimitOrder }


    override fun createTradeHistoryParams(): TradeHistoryParams = IdexTradeHistoryReq()

    override fun placeStopOrder(stopOrder: StopOrder?): String =
            throw org.knowm.xchange.exceptions.NotAvailableFromExchangeException()


    override fun cancelOrder(orderHash: String): Boolean {

        val idexServerNonce1 = idexServerNonce

        val hash_data: List<List<String>> = listOf(
                listOf("orderHash", orderHash, "address"),
                listOf("nonce", ""+idexServerNonce1, "uint256")
        )

        val sig = generateSignature(idexExchange.exchangeSpecification.secretKey, hash_data)!!
        val cancelReq = CancelReq().orderHash(orderHash).nonce(idexServerNonce1).address(apiKey)
                .v(sig.v .toInt().toBigInteger())
                .r("0x" + Hex.encodeHex(sig.r))
                .s("0x" + Hex.encodeHex(sig.s))

        val cancel = cancel(cancelReq)
        return (cancel.success == BigInteger.ONE).also {
            cancel.error?.also { System.err.println("cancel error: " + it) }
        }
    }


    /**
     * all orders are limit orders -- we cannot guarantee a market result from the API
     */
    override fun placeMarketOrder(m: MarketOrder): String {
        throw NotAvailableFromExchangeException()
    }

    override fun getTradeHistory(params: TradeHistoryParams): UserTrades {
        if (params !is IdexTradeHistoryReq) {
            throw  ApiException("tradehistory requires " + IdexTradeHistoryReq::class.java.canonicalName)
        }

        val m = params.market.split("_")
        val currencyPair = CurrencyPair(m[0], m[1])
        val tradeHistory = MarketApi().tradeHistory(params)
        val map = tradeHistory.map {
            UserTrade(enumValueOf(it.type),
                      it.amount.toBigDecimalOrNull() ?: ZERO,
                      currencyPair,
                      it.price.toBigDecimalOrNull() ?: ZERO,
                      DateUtils.fromISO8601DateString(it.date),
                      it.uuid, it.orderHash, ZERO, currencyPair.base)
        }
        var ret: UserTrades? = null
        if (params.lastId != null)
            params.lastId.let { ret = UserTrades(map, it, params.sort) }
        return ret ?: UserTrades(map, params.sort)
    }

    override fun verifyOrder(
            limitOrder: LimitOrder?) = throw org.knowm.xchange.exceptions.NotAvailableFromExchangeException()

    override fun verifyOrder(
            marketOrder: MarketOrder?) = throw org.knowm.xchange.exceptions.NotAvailableFromExchangeException()

    override fun getOpenOrders(): OpenOrders = OpenOrders(openOrders(OpenOrdersReq().address(apiKey)).map {
        val c2 = it.params.run { listOf(buySymbol, sellSymbol) }
        LimitOrder(BID[it.type], it.amount.toBigDecimalOrNull() ?: ZERO,
                   CurrencyPair(c2[0], c2[1]), it.orderHash.toString(), Date(),
                   it.price.toBigDecimalOrNull() ?: ZERO)
    })

    override fun getOpenOrders(params: OpenOrdersParams): OpenOrders = OpenOrders(
            openOrders.openOrders.filter(params::accept))

    override fun getOrder(vararg orderIds: String?): MutableCollection<Order> =
            getOpenOrders { it.id in orderIds }.openOrders.toMutableList()


    companion object {
        class IdexLimitOrder(type: OrderType?, originalAmount: BigDecimal?,
                             currencyPair: CurrencyPair?, id: String?,
                             timestamp: Date?, limitPrice: BigDecimal?,
                             val r: String,
                             val s: String,
                             val v: Int,
                             val orderHash: String,
                             /**amountBuy (uint256) - The amount of the token you will receive when the order is fully filled
                              */
                             val amountBuy: String,
                             /**amountSell (uint256) - The amount of the token you will give up when the order is fully filled
                              */
                             val amountSell: String,
                             /**tokenBuy (address string) - The address of the token you will receive as a result of the trade
                              */
                             val tokenBuy: String,
                             /**tokenSell (address string) - The address of the token you will lose as a result of the trade
                              */
                             val tokenSell: String,
                             /**expires (uint256) - DEPRECATED this property has no effect on your limit order but is still REQUIRED to submit a limit order as it is one of the parameters that is hashed. It must be a numeric type
                              */
                             val expires: Long = Random().nextLong()

        ) : LimitOrder(type, originalAmount, currencyPair, id, timestamp, limitPrice)

        class IdexTradeHistoryReq(val sort: Trades.TradeSortType? = Trades.TradeSortType.SortByTimestamp,
                                  val lastId: Long? = null) : TradeHistoryParams, TradeHistoryReq()

        /**Generate v, r, s values from payload*/
        fun generateSignature(
                apiSecret: String,
                data: List<List<String>>
        ): Sign.SignatureData? {
            // pack parameters based on type
//            var sig_str = ""
            var sig_arr= byteArrayOf()
            if(IdexMarketDataService.debugMe){
                System.err.println(data.toString())
            }
            data.forEach {
                d: List<String> ->
                val data: String = d[1]
                sig_arr += when (d[2]) {
                    "address" /* remove 0x prefix and convert to bytes*/ -> {
                        val padded = ByteArray(20) { 0 }
                        val r: ByteArray = data.toLowerCase().split("0x").last().toBigInteger(16).toByteArray()

                        val slice: ByteArray = padded.slice(0..(padded.size - r.size)).toByteArray()
                        val bytes: ByteArray = slice  + r
                        assert(bytes.size==20)
                        bytes
                    }
                    "uint256" /* encode, pad and convert to bytes*/ -> {
                        val padded = ByteArray(32) { 0 }
                        val r: ByteArray = data.toBigInteger().toByteArray()

                        val slice: ByteArray = padded.slice(0..(padded.size - r.size)).toByteArray()
                        val bytes: ByteArray = slice+ r
                        assert(bytes.size==32)
                        bytes
                    }
                    else -> /*never*/ TODO("review signature target with bad type key here")
                }
            }
            // hash the packed string
            val rawhash: ByteArray = sha3(sig_arr)

            // salt the hashed packed string
            val saltBytes: ByteArray = "\u0019Ethereum Signed Message:\n32".toByteArray()
            assert(Hex.encodeHexString(saltBytes).toLowerCase()=="19457468657265756d205369676e6564204d6573736167653a0a3332" )
            val bytes: ByteArray = saltBytes + rawhash
            val salted: ByteArray = sha3(bytes)

            apiSecret.split("0x").last()

            val payloadBytesCrypted: ByteArray = salted
            val toBigInteger: BigInteger = apiSecret.split("0x").last().toBigInteger(16)
            val ecKeyPair: ECKeyPair = ECKeyPair.create(toBigInteger)
            return signMessage(
                   /* message = */payloadBytesCrypted,
                   /* keyPair=  */ ecKeyPair
            )
        }
    }
}
