package org.knowm.xchange.lakebtc.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.lakebtc.LakeBTCAdapters;
import org.knowm.xchange.lakebtc.dto.account.LakeBTCAccountInfoResponse;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

/** @author cristian.lucaci */
public class LakeBTCAccountService extends LakeBTCAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public LakeBTCAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    LakeBTCAccountInfoResponse response = super.getLakeBTCAccountInfo();
    return LakeBTCAdapters.adaptAccountInfo(response.getResult());
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }
}
