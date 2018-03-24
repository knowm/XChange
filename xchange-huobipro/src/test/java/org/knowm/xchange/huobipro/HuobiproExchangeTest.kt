package org.knowm.xchange.huobipro

import org.junit.Test

import org.junit.Assert.*
import org.knowm.xchange.ExchangeFactory

class HuobiproExchangeTest


//smoke test

fun main(args: Array<String>) {
    System.setProperty("XChangeDebug","true")
    val apiKey = args[0]
    val apiZekret = args[1]
    val hp = ExchangeFactory.INSTANCE.createExchange(HuobiproExchange::class.java, apiKey, apiZekret);
    val accountInfo = hp.accountService.accountInfo
    System.err.println(accountInfo.toString())
}