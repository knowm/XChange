package com.xeiam.xchange.examples.kraken.account;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.examples.kraken.KrakenExampleUtils;
import com.xeiam.xchange.kraken.dto.account.KrakenLedger;
import com.xeiam.xchange.kraken.dto.account.KrakenTradeBalanceInfo;
import com.xeiam.xchange.kraken.dto.account.KrakenTradeVolume;
import com.xeiam.xchange.kraken.service.polling.KrakenAccountServiceRaw;

public class KrakenAccountRawSpecificDemo {

  public static void main(String[] args) throws IOException {

    Exchange krakenExchange = KrakenExampleUtils.createTestExchange();

    KrakenAccountServiceRaw rawKrakenAcctService = (KrakenAccountServiceRaw) krakenExchange.getPollingAccountService();

    KrakenTradeBalanceInfo balanceInfo = rawKrakenAcctService.getKrakenTradeBalance();
    System.out.println(balanceInfo);

    Map<String, KrakenLedger> ledgerInfo = rawKrakenAcctService.getKrakenLedgerInfo();
    System.out.println(ledgerInfo);

    KrakenTradeVolume tradeVolume = rawKrakenAcctService.getTradeVolume();
    System.out.println(tradeVolume);

    tradeVolume = rawKrakenAcctService.getTradeVolume(CurrencyPair.BTC_USD);
    System.out.println(tradeVolume);
  }
}
