package org.knowm.xchange.idex

import org.knowm.xchange.*

fun main(args: Array<String>) {
    val apiKey = args[0]
    val apiZekret = if(args.size > 1) args[1] else ""

    val idex = ExchangeFactory.INSTANCE.createExchange(IdexExchange::class.java, apiKey, apiZekret);
    val accountInfo = idex.accountService.accountInfo

    System.err.println(accountInfo.toString())
}