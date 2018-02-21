package org.knowm.xchange.stocksexchange

import org.knowm.xchange.*
import org.knowm.xchange.stocksexchange.*

//smoke test

fun main(args: Array<String>) {
    System.setProperty("XChangeDebug","true")
    val apiKey = args[0]
    val apiZekret = args[1]
    val exch = ExchangeFactory.INSTANCE.createExchange(StocksexchangeExchange::class.java, apiKey, apiZekret);
    val accountInfo = exch.accountService.accountInfo
    System.err.println(accountInfo.toString())
}