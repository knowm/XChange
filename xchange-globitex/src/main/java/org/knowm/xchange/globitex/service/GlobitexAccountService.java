package org.knowm.xchange.globitex.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.globitex.GlobitexAdapters;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;

public class GlobitexAccountService extends GlobitexAccountServiceRaw implements AccountService {

  public GlobitexAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return GlobitexAdapters.adaptToAccountInfo(getGlobitexAccounts());
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new TradeHistoryParamsAll();
  }
}
