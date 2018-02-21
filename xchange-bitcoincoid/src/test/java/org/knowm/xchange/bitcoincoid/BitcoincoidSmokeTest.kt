package org.knowm.xchange.bitcoincoid

import org.knowm.xchange.*

//smoke test

fun main(args: Array<String>) {
    val apiKey = args[0]
    val apiZekret = args[1]
    val bccoid = ExchangeFactory.INSTANCE.createExchange(BitcoincoidExchange::class.java, apiKey, apiZekret);
    val accountInfo = bccoid.accountService.accountInfo
    System.err.println(accountInfo.toString())
}