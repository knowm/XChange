package org.knowm.xchange.idex


import org.knowm.xchange.currency.*
import org.knowm.xchange.dto.account.*
import org.knowm.xchange.idex.dto.*
import org.knowm.xchange.idex.service.*
import org.knowm.xchange.idex.util.*
import org.knowm.xchange.service.account.*
import org.knowm.xchange.service.trade.params.*
import java.math.*

class IdexAccountService(private val idexExchange: IdexExchange) : AccountApi(), AccountService {
    override fun getAccountInfo(): AccountInfo {
        val apiKey = idexExchange.exchangeSpecification.apiKey
        val s = apiKey.slice(0.rangeTo(6)) + "…"
        val returnBalancesPost = completeBalances(CompleteBalancesReq().apply { address = apiKey })

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

    override fun withdrawFunds(params: WithdrawFundsParams) = when (params) {
        is IdexWithdraw -> JSON().gson.toJson(withdraw(params))
        else -> throw  ApiException(
                "Idex withdraw requires " + IdexWithdraw::class.java.canonicalName)
    }

    override fun requestDepositAddress(currency: Currency?, vararg args: String?): String =//the safe option
            idexExchange.exchangeSpecification.apiKey
    override fun getFundingHistory(params: TradeHistoryParams): MutableList<FundingRecord> {
        TODO("not supported by exchange")
    }

    override fun createFundingHistoryParams(): TradeHistoryParams {
        TODO("not supported by exchange")
    }
}

class IdexWithdraw : WithdrawReq, WithdrawFundsParams {
    constructor(address: String, amount: String, token: String, nonce: String, s: String, v: String,
                r: String) {
        address(address)
                .amount(amount)
                .token(token)
                .nonce(nonce)
                .s(s)
                .v(v)
                .r(r)
    }
}

