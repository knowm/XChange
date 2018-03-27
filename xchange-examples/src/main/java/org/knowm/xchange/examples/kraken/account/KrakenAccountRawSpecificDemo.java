package org.knowm.xchange.examples.kraken.account;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.examples.kraken.KrakenExampleUtils;
import org.knowm.xchange.kraken.dto.account.KrakenLedger;
import org.knowm.xchange.kraken.dto.account.KrakenTradeBalanceInfo;
import org.knowm.xchange.kraken.dto.account.KrakenTradeVolume;
import org.knowm.xchange.kraken.service.KrakenAccountServiceRaw;

public class KrakenAccountRawSpecificDemo {

  public static void main(String[] args) throws IOException {

    Exchange krakenExchange = KrakenExampleUtils.createTestExchange();

    KrakenAccountServiceRaw rawKrakenAcctService =
        (KrakenAccountServiceRaw) krakenExchange.getAccountService();

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
