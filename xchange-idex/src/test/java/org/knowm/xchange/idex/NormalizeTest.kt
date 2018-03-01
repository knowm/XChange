package org.knowm.xchange.idex

import org.knowm.xchange.*
import org.knowm.xchange.currency.*
import org.knowm.xchange.dto.*


 fun main(args: Array<String>) {
    System.setProperty("XChangeDebug", "true")
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
    val currencyPair = CurrencyPair("OMG/ETH")
    System.err.println("normalized order for 1 "+currencyPair+" at " + last + "ETH  ")

    val ethCur = currencyPair.counter
    val omgCur = currencyPair.base
    val normalizedLimitOrderReq = idex.tradeService.createNormalizedLimitOrderReq(baseCurrency = omgCur,
                                                                                  counterCurrency = ethCur,
                                                                                  type = Order.OrderType.BID,
                                                                                  limitPrice = last,
                                                                                  originalAmount = last,
                                                                                  contractAddress = "0x2a0c0dbecc7e4d658f48e01e3fa353f44050c208",
                                                                                  nonce = "1000")
    System.err.println(normalizedLimitOrderReq)
    System.err.println("recv:"+normalizedLimitOrderReq.amountBuy.toBigDecimal()*"1e-${ (idex.exchangeMetaData.currencies[omgCur] as IdexCurrencyMeta).decimals}".toBigDecimal())
    System.err.println("spend"+normalizedLimitOrderReq.amountSell.toBigDecimal()*"1e-${ (idex.exchangeMetaData.currencies[ethCur ] as IdexCurrencyMeta).decimals}".toBigDecimal())
}