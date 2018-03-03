package org.knowm.xchange.bitmex

import org.knowm.xchange.*

//smoke test

fun main(args: Array<String>) {
    val apiKey = args[0]
    val apiZekret = args[1]
    val bitmex = ExchangeFactory.INSTANCE.createExchange(BitmexExchange::class.java, apiKey, apiZekret);
    val accountInfo = bitmex.accountService.accountInfo
    System.err.println(accountInfo.toString())
}