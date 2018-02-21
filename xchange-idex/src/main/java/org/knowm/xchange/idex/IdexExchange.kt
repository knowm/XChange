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
    private val idexTradeService = IdexTradeService()
    private val idexMarketDataService = IdexMarketDataService(this)
    private val idexAccountService = IdexAccountService(this)
    override fun getExchangeSpecification(): ExchangeSpecification = this.exchangeSpecification
    override fun getNonceFactory() = AtomicLongIncrementalTime2014NonceFactory()
    override fun initServices() =Unit
    override fun getAccountService(): AccountService = idexAccountService
    override fun getExchangeMetaData(): ExchangeMetaData = TODO("not implemented")
    override fun getDefaultExchangeSpecification() = ExchangeSpecification(IdexExchange::class.java).apply {
        exchangeDescription = "Idex.market exchange Kotlin based API client "
        exchangeName = "idex"
    }

    override fun remoteInit() { TODO("not implemented") /*To change body of created functions use File | Settings | File Templates.*/ }
    override fun getTradeService(): TradeService = idexTradeService
    override fun getMarketDataService(): MarketDataService = idexMarketDataService
    override fun applySpecification(exchangeSpecification: ExchangeSpecification?) { this.exchangeSpecification = exchangeSpecification }
}

