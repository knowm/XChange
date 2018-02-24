package org.knowm.xchange.idex

import org.knowm.xchange.*
import org.knowm.xchange.dto.meta.*
import org.knowm.xchange.idex.util.*
import org.knowm.xchange.service.account.*
import org.knowm.xchange.service.marketdata.*
import org.knowm.xchange.service.trade.*
import org.knowm.xchange.utils.nonce.*

class IdexExchange : Exchange, BaseExchange() {
    val gson = JSON().gson
    override fun getExchangeSpecification(): ExchangeSpecification = this.exchangeSpecification
    override fun getNonceFactory() = AtomicLongIncrementalTime2014NonceFactory()
    override fun initServices() =Unit
    override fun getExchangeMetaData(): ExchangeMetaData = TODO("not implemented")
    override fun applySpecification(exchangeSpecification: ExchangeSpecification?) { this.exchangeSpecification = exchangeSpecification }
    override fun getDefaultExchangeSpecification() = ExchangeSpecification(IdexExchange::class.java).apply {
        exchangeDescription = "Idex.market exchange Kotlin based API client "
        exchangeName = "idex"
    }

    override fun remoteInit() {  }
    override fun getAccountService(): AccountService = IdexAccountService(this)
    override fun getTradeService(): TradeService = IdexTradeService(this)
    override fun getMarketDataService(): MarketDataService = IdexMarketDataService(this)
}

