package org.knowm.xchange.idex

import org.knowm.xchange.*
import org.knowm.xchange.currency.*
import org.knowm.xchange.dto.*


 fun main(args: Array<String>) {
//    System.setProperty("XChangeDebug", "true")
    val apiKey = args[0]
    System.err.println("AccountInfo.... using arg[0] as account")
    val apiZekret = if (args.size > 1) args[1] else ""

    val idex: IdexExchange = ExchangeFactory.INSTANCE.createExchange(IdexExchange::class.java, apiKey,
                                                                     apiZekret) as IdexExchange



    val ticker = idex.marketDataService.getTicker(CurrencyPair(
            "OMG",
            "ETH"
    ));
    val last = ticker.last
    System.err.println("normalized order for 2 ETH_OMG at " + last + "ETH price using 2.0 originalAmoun")
    val currencyPair = CurrencyPair("OMG/ETH")

    val normalizedOrderReq = idex.tradeService.createNormalizedOrderReq(baseCurrency = currencyPair.base,
                                                                        counterCurrency = currencyPair.counter,
                                                                        type = Order.OrderType.BID,
                                                                        limitPrice = last,
                                                                        originalAmount = 2.toBigDecimal())
    System.err.println(normalizedOrderReq)
}