package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bittrex.BittrexAdapters;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.*;

public class BittrexAccountService extends BittrexAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo(BittrexAdapters.adaptWallet(getBittrexBalances()));
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return TradeHistoryParamsZero.PARAMS_ZERO;
  }
}
