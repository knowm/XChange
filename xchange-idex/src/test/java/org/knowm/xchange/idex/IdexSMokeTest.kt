package org.knowm.xchange.idex

import org.knowm.xchange.*
import org.knowm.xchange.currency.*

fun main(args: Array<String>) {
    val apiKey = args[0]
    System.err.println("level 1.... grabbing your wallet")
    val apiZekret = if (args.size > 1) args[1] else ""

    val idex = ExchangeFactory.INSTANCE.createExchange(IdexExchange::class.java, apiKey, apiZekret);
    val accountInfo = idex.accountService.accountInfo

    System.err.println(accountInfo.toString())

    System.err.println("level 2... looking for gold in your teeth (ticker for ETH_DCN)")
    val ticker = idex.marketDataService.getTicker(CurrencyPair("ETH", "DCN"));
    val tickers = ticker.toString()
    System.err.println(
            tickers
    )

}