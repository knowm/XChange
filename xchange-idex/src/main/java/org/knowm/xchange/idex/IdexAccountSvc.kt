package org.knowm.xchange.idex

import org.knowm.xchange.currency.*
import org.knowm.xchange.dto.account.*
import org.knowm.xchange.idex.dto.*
import org.knowm.xchange.idex.service.*
import org.knowm.xchange.idex.util.*
import org.knowm.xchange.service.account.*
import org.knowm.xchange.service.trade.params.*
import java.math.*

class IdexAccountService(private val idexExchange: IdexExchange) :
        AccountService {
    override fun getAccountInfo(): AccountInfo {

        val apiKey = idexExchange.exchangeSpecification.apiKey
        val s = apiKey.slice(0.rangeTo(6)) + "…"
        val returnBalancesPost =  IdexRawService .returnCompleteBalances(apiKey)
        val entries = returnBalancesPost.entries
        val toList = entries.toList()
        val list = toList.map {
            val key = it.key!!
            val value = it.value!!
            val currency = Currency(key)
            Balance(currency, null, value.available, value.onOrders)
        }
        val accountInfo = AccountInfo(
                Wallet(s, list))
        return accountInfo
    }

    override fun withdrawFunds(currency: Currency?, amount: BigDecimal?, address: String?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun withdrawFunds(params: WithdrawFundsParams?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun requestDepositAddress(currency: Currency?, vararg args: String?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFundingHistory(params: TradeHistoryParams?): MutableList<FundingRecord> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createFundingHistoryParams(): TradeHistoryParams {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun returnTickerRequestedWithNull() = let {
        val returnTickerPost = DefaultApi().returnTickerPost(null)
        val gson = JSON().gson
        val toJson = gson.toJson(returnTickerPost)
        gson.fromJson(toJson, ReturnTickerRequestedWithNull::class.java)
    }
}