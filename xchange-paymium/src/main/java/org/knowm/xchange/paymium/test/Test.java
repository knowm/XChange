package org.knowm.xchange.paymium.test;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.paymium.PaymiumExchange;
import org.knowm.xchange.paymium.service.PaymiumTradeService;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.TradeService;

public class Test {

  public static void main(String[] args) throws IOException {

    ExchangeSpecification exSpec = new PaymiumExchange().getDefaultExchangeSpecification();
    exSpec.setApiKey("9d553adcc4e0a99b990a061d8ebc755764926bb2cb13a8e98d2cfdb26070660e");
    exSpec.setSecretKey("e86c5e0607f09bea37ba96be9fb3b3ad77c7701255823c113ccfd406391f9be9");
    exSpec.setProxyHost("127.0.0.1");
    exSpec.setProxyPort(8888);

    PaymiumTradeService.PaymiumHistoryParams history = new PaymiumTradeService.PaymiumHistoryParams(0L,20);

    Exchange paymium = ExchangeFactory.INSTANCE.createExchange(exSpec);
    AccountService accountService = paymium.getAccountService();
    //AccountInfo accountInfo = accountService.getAccountInfo();
    //Wallet wallet = accountInfo.getWallet();

    TradeService tradeService = paymium.getTradeService();
    tradeService.getTradeHistory(history);

    //System.out.println(wallet);
  }
}
