package org.knowm.xchange.idex

import org.apache.commons.codec.binary.*
import org.knowm.xchange.currency.*
import org.knowm.xchange.currency.Currency
import org.knowm.xchange.dto.*
import org.knowm.xchange.dto.Order.OrderType.*
import org.knowm.xchange.dto.marketdata.*
import org.knowm.xchange.dto.trade.*
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

    val apiKey inline get() = idexExchange.exchangeSpecification.apiKey

    val idexServerNonce get() = nextNonce(NextNonceReq().address(apiKey)).nonce


    override fun placeLimitOrder(placeOrder: LimitOrder): String {
        when (placeOrder) {
            is IdexLimitOrder -> {
                val limitOrder: IdexLimitOrder = placeOrder
                val apiKey = idexExchange.exchangeSpecification.apiKey
                order(OrderReq()
                              .address(apiKey)
                              .nonce(idexServerNonce)
                              .amountBuy(limitOrder.amountBuy)
                              .amountSell(limitOrder.amountSell)
                              .tokenBuy(limitOrder.tokenBuy)
                              .tokenSell(limitOrder.tokenSell)
                              .r(limitOrder.r)
                              .s(limitOrder.s)
                              .v(limitOrder.v)
                ).orderNumber
            }
            else -> {

                val type = placeOrder.type
                val baseCurrency = placeOrder.currencyPair.base
                val counterCurrency = placeOrder.currencyPair.counter
                val originalAmount = placeOrder.originalAmount
                val limitPrice = placeOrder.limitPrice


                val orderReq = createNormalizedOrderReq(baseCurrency, counterCurrency, type, limitPrice, originalAmount,
                                                        contractAddress.address.toString())
                order(orderReq).orderNumber
            }
        }
        TODO("This method requires " + IdexLimitOrder::class.java.canonicalName);
    }

    val IdexContractAddress by lazy { contractAddress().address }

    public fun createNormalizedOrderReq(baseCurrency: Currency,
                                        counterCurrency: Currency,
                                        type: Order.OrderType, limitPrice: BigDecimal,
                                        originalAmount: BigDecimal,
                                        contractAddress: String = contractAddress().address): OrderReq {
        idexExchange.exchangeMetaData.currencies[baseCurrency]
        idexExchange.exchangeMetaData.currencies[counterCurrency]


        var c = listOf(baseCurrency,//OMG
                       counterCurrency//ETH
        )
        if (type == ASK) c = c.reversed();

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
                listOf("nonce", idexServerNonce, "uint256"),
                listOf("address", apiKey, "address")
        )

        val sig = generateSignature(idexExchange.exchangeSpecification.secretKey, hash_data)!!
        val orderReq = OrderReq()
                .address(apiKey)
                .nonce(idexServerNonce)
                .tokenBuy(buy_currency.address)
                .amountBuy(amount_buy.toString())
                .tokenSell(sell_currency.address)
                .amountSell(amount_sell.toString())
                .expires(nextInt)
                .s(sig.s.toString())


        return orderReq
    }


    override fun createOpenOrdersParams() = OpenOrdersParams { it is IdexLimitOrder }


    override fun createTradeHistoryParams(): TradeHistoryParams = IdexTradeHistoryReq()

    override fun placeStopOrder(stopOrder: StopOrder?): String =
            throw org.knowm.xchange.exceptions.NotAvailableFromExchangeException()


    override fun cancelOrder(orderId: String) =
            cancel(CancelReq().run { orderHash(orderId).address(apiKey).nonce(idexServerNonce) }).success == 1


    override fun placeMarketOrder(marketOrder: MarketOrder): String =
            when (marketOrder) {
                is IdexMarketOrder -> {
                    val trade = trade(TradeReq().apply {
                        val m: IdexMarketOrder = marketOrder;
                        orderHash(m.id)
                                .amount(m.originalAmount.toPlainString())
                                .address(apiKey)
                                .nonce(idexServerNonce)
                                .r(m.r)
                                .s(m.s)
                                .v(m.v)
                    }
                    )
                    if (trade.isEmpty()) throw ApiException(
                            "Idex orders are done as all-or-nothing bulk arrays.  this trade " +
                                    "did not return any confirmations, all failed.")
                    trade.first().orderHash

                }
                else -> throw ApiException(
                        "Idex Market Orders require " + IdexMarketOrder::class.java.canonicalName)
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
            getOpenOrders().openOrders.filter(params::accept))

    override fun getOrder(vararg orderIds: String?): MutableCollection<Order> =
            getOpenOrders { it.id in orderIds }.openOrders.toMutableList()

    val contractAddress by lazy { contractAddress() }

    companion object {


        class IdexMarketOrder(val r: String, val s: String, val v: String, type: OrderType?,
                              originalAmount: BigDecimal?,
                              currencyPair: CurrencyPair?, id: String?, timestamp: Date?, averagePrice: BigDecimal?,
                              cumulativeAmount: BigDecimal?, fee: BigDecimal?, status: OrderStatus?) :
                MarketOrder(type, originalAmount, currencyPair, id, timestamp, averagePrice, cumulativeAmount, fee,
                            status)

        /**
        v - ...
        r - ...
        s - (v r and s are the values produced by your private key signature, see above for details)
         */
        public class IdexLimitOrder(type: OrderType?, originalAmount: BigDecimal?,
                                    currencyPair: CurrencyPair?, id: String?,
                                    timestamp: Date?, limitPrice: BigDecimal?,
                                    val r: String,
                                    val s: String,
                                    val v: String,
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
                    -> d[1].toLowerCase().split("0x").last() as String
                    "uint256"
                        // encode, pad and convert to bytes
                    -> Hex.encodeHexString(d[1].toBigInteger().toByteArray()).toLowerCase() as String
                    else ->/*never*/ d[1]
                }
            }
            // hash the packed string
            val rawhash = sha3String(sig_str)

            // salt the hashed packed string
            val salted = sha3String("\u0019Ethereum Signed Message:\n32$rawhash")

            apiSecret.split("0x").last()

            val payloadBytesCrypted = salted.toByteArray(UTF_8)
            val apiSecretBytes = apiSecret.toLowerCase().split("0x").last().toByteArray(UTF_8)
            return Sign.signMessage(payloadBytesCrypted, ECKeyPair.create(apiSecretBytes))
        }
    }
}
