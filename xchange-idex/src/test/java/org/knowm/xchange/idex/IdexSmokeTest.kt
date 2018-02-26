package org.knowm.xchange.idex

import org.knowm.xchange.*
import org.knowm.xchange.currency.*

fun main(args: Array<String>) {
    System.setProperty("XChangeDebug", "true")
    val apiKey = args[0]
    System.err.println("AccountInfo.... using arg[0] as account")
    val apiZekret = if (args.size > 1) args[1] else ""

    val idex: IdexExchange = ExchangeFactory.INSTANCE.createExchange(IdexExchange::class.java, apiKey,
                                                                     apiZekret) as IdexExchange;
    val accountInfo = idex.accountService.accountInfo

    System.err.println(accountInfo.toString())

    System.err.println("Ticker... looking for OMG (ticker for ETH_OMG)")
    val ticker = idex.marketDataService.getTicker(CurrencyPair("ETH", "OMG"));
    val tickers = ticker.toString()
    System.err.println(
            tickers
    )
    System.err.println("Trade History... using arg[2]")

    val split = args[2].split("_")
    System.err.println(idex.marketDataService.getTrades(CurrencyPair(split[0], split[1]))).toString()

    System.err.println("Funding History... using arg[0]")
    val c: IdexTradeHistoryParams = idex.accountService.createFundingHistoryParams() as IdexTradeHistoryParams
    c.address(apiKey)
    System.err.println(idex.accountService.getFundingHistory(c))
    System.err.println("All currencies (lazy)")
    var t = System.currentTimeMillis()
    val allCurrencies = IdexMarketDataService.allCurrencies
    System.err.println("fetched/sorted in " + (System.currentTimeMillis() - t) + "(ms)")
    System.err.println(allCurrencies)
    System.err.println("All intruments (lazy)")
    t = System.currentTimeMillis()
    val allInstrument = IdexMarketDataService.allInstrument
    System.err.println("sorted in ${System.currentTimeMillis() - t}(ms)")
    System.err.println(allInstrument)
}