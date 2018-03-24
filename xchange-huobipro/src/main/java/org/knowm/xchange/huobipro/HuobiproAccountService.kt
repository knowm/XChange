package org.knowm.xchange.huobipro

import org.knowm.xchange.currency.Currency
import org.knowm.xchange.dto.account.AccountInfo
import org.knowm.xchange.dto.account.Balance
import org.knowm.xchange.dto.account.FundingRecord
import org.knowm.xchange.dto.account.Wallet
import org.knowm.xchange.huobipro.dto.AccountsBalanceResponseDataList
import org.knowm.xchange.huobipro.service.AccountApi
import org.knowm.xchange.service.account.AccountService
import org.knowm.xchange.service.trade.params.TradeHistoryParams
import org.knowm.xchange.service.trade.params.WithdrawFundsParams
import java.math.BigDecimal

class HuobiproAccountService(private val huobiproExchange: HuobiproExchange) : AccountService, AccountApi() {
    init {
        apiClient = huobiproExchange.authClient
    }

    override fun getAccountInfo(): AccountInfo {
        val listOf = mutableListOf<Balance>();
        val associate = accounts.data.map {
            val accountId = it.id.toString()
            val balance = balance(accountId)
            val data = balance.data
            val raw = data.list .associate { (it.currency to it.type) to (it.balance.toBigDecimalOrNull() ?: 0.toBigDecimal()) }
            val map = raw.keys.map { it.first }.distinct().map {
                Balance(
                        Currency(it),
                        null,
                        raw[Pair(it, AccountsBalanceResponseDataList.TypeEnum.TRADE)] ?: 0.toBigDecimal(),
                        raw[Pair(it, AccountsBalanceResponseDataList.TypeEnum.FROZEN)] ?: 0.toBigDecimal()
                )

            } .filter { it.total>0.toBigDecimal() }
            Wallet((/*it.userId to*/ accountId to  it.type).toString(), map)
        }

        return AccountInfo(associate)
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

    override fun createFundingHistoryParams(): TradeHistoryParams {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFundingHistory(params: TradeHistoryParams?): MutableList<FundingRecord> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}