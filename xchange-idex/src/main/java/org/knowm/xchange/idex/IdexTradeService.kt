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
import java.math.*
import java.math.BigDecimal.*
import java.util.*
import kotlin.text.Charsets.UTF_8

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
        is IdexCancelOrderParams -> cancel(orderParams).success!! == 1
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
        val orderReq = createNormalizedLimitOrderReq(baseCurrency, counterCurrency, type, limitPrice, originalAmount)

        return order(orderReq).orderHash
    }


    fun createNormalizedLimitOrderReq(baseCurrency: Currency,
                                      counterCurrency: Currency,
                                      type: Order.OrderType, limitPrice: BigDecimal,
                                      originalAmount: BigDecimal,
                                      contractAddress: String = contractAddress().address,
                                      nonce: String = idexServerNonce): OrderReq {
        idexExchange.exchangeMetaData.currencies[baseCurrency]
        idexExchange.exchangeMetaData.currencies[counterCurrency]


        var c = listOf(baseCurrency,//OMG
                       counterCurrency//ETH
        )
        if (type == ASK) c = c.reversed()

        val buy_currency = idexExchange.exchangeMetaData.currencies[c[0]] as IdexCurrencyMeta
        val sell_currency = idexExchange.exchangeMetaData.currencies[c[1]] as IdexCurrencyMeta


        var amount_buy = (originalAmount / limitPrice).multiply(
                "1e+${buy_currency.decimals.toBigDecimal()}".toBigDecimal()).toBigInteger()
        var amount_sell = (originalAmount).multiply(
                "1e+${sell_currency.decimals.toBigDecimal()}".toBigDecimal()).toBigInteger()

        val nextInt = Random().nextInt()

        val hash_data = listOf(
                listOf("contractAddress", contractAddress, "address"),
                listOf("tokenBuy", buy_currency.address, "address"),
                listOf("amountBuy", amount_buy.toString(), "uint256"),
                listOf("tokenSell", sell_currency.address, "address"),
                listOf("amountSell", amount_sell.toString(), "uint256"),
                listOf("expires", nextInt.toString(), "uint256"),
                listOf("nonce", nonce, "uint256"),
                listOf("address", apiKey, "address")
        )

        val sig = generateSignature(idexExchange.exchangeSpecification.secretKey, hash_data)!!
        val v = sig.v
        val r = sig.r
        val s = sig.s
        val orderReq = OrderReq()
                .address(apiKey)
                .nonce(nonce)
                .tokenBuy(buy_currency.address)
                .amountBuy(amount_buy.toString())
                .tokenSell(sell_currency.address)
                .amountSell(amount_sell.toString())
                .expires(nextInt)
                .r("0x" + Hex.encodeHexString(r))
                .s("0x" + Hex.encodeHexString(s))
                .v((v).toInt())


        return orderReq
    }


    override fun createOpenOrdersParams() = OpenOrdersParams { it is IdexLimitOrder }


    override fun createTradeHistoryParams(): TradeHistoryParams = IdexTradeHistoryReq()

    override fun placeStopOrder(stopOrder: StopOrder?): String =
            throw org.knowm.xchange.exceptions.NotAvailableFromExchangeException()


    override fun cancelOrder(orderHash: String): Boolean {

        val idexServerNonce1 = idexServerNonce

        val hash_data = listOf(
                listOf("orderHash", orderHash, "address"),
                listOf("nonce", idexServerNonce1, "uint256")
        )

        val sig = generateSignature(idexExchange.exchangeSpecification.secretKey, hash_data)!!
        val cancelReq = CancelReq().orderHash(orderHash).nonce(idexServerNonce1).address(apiKey)
                .v(sig.v.toInt())
                .r("0x" + Hex.encodeHex(sig.r))
                .s("0x" + Hex.encodeHex(sig.s))

        val cancel = cancel(cancelReq)
        return (cancel.success == 1).also {
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
            var sig_str = ""
            data.forEach { d ->

                sig_str += when (d[2]) {
                    "address"
                        // remove 0x prefix and convert to bytes
                    -> d[1].toLowerCase().split("0x").last()
                    "uint256"
                        // encode, pad and convert to bytes
                    -> Hex.encodeHexString(d[1].toBigInteger().toByteArray()).toLowerCase()
                    else ->/*never*/ d[1]
                }
            }
            // hash the packed string
            val rawhash = sha3String(sig_str)

            // salt the hashed packed string
            val salted = sha3String("\u0019Ethereum Signed Message:\n32$rawhash")

            apiSecret.split("0x").last()

            val payloadBytesCrypted = salted.toByteArray(UTF_8)
//            val apiSecretBytes = apiSecret.toLowerCase().split("0x").last().toByteArray(UTF_8)
            val toBigInteger = apiSecret.split("0x").last().toBigInteger(16)
            val ecKeyPair = ECKeyPair.create(toBigInteger)
            return Sign.signMessage(payloadBytesCrypted, ecKeyPair)
        }
    }
}
