package org.knowm.xchange.idex

import org.knowm.xchange.*
import org.knowm.xchange.currency.*
import org.knowm.xchange.dto.*
import org.knowm.xchange.dto.trade.*
import org.knowm.xchange.idex.IdexExchange.Companion.gson

class IdexSmokeTestBuy {}

fun main(args: Array<String>) {
    System.setProperty("XChangeDebug", "true")
    val apiKey = args[0]
    System.err.println("AccountInfo.... using arg[0] as account")
    val apiZekret = if (args.size > 1) args[1] else ""

    val idex: Exchange = ExchangeFactory.INSTANCE.createExchange(IdexExchange::class.java, apiKey,
                                                                 apiZekret) as IdexExchange;
    val accountInfo = idex.accountService.accountInfo

    System.err.println(accountInfo.toString())
    System.err.println("Ticker... looking for KIN (ticker for ETH_KIN)")
    val kin = CurrencyPair("KIN", "ETH")
    val ticker = idex.marketDataService.getTicker(kin);
    val tickers = ticker.toString()
    System.err.println(
            tickers
    )
    val placeLimitOrder = idex.tradeService.placeLimitOrder(
            LimitOrder(Order.OrderType.ASK, 1 .toBigDecimal(), kin, null, null,
                       42 .toBigDecimal()))
    System.err.println(gson.toJson(placeLimitOrder))
}