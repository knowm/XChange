package com.xeiam.xchange.btce.v3.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.btce.v3.BTCEAdapters;
import com.xeiam.xchange.btce.v3.dto.account.BTCEAccountInfo;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEExchangeInfo;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEPairInfo;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Matija Mazi
 */
public class BTCEAccountService extends BTCEAccountServiceRaw implements PollingAccountService {

  private BigDecimal tradingFee;

  /**
   * Constructor
   *
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCEAccountService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Integer> nonceFactory) {

    super(exchangeSpecification, nonceFactory);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    BTCEAccountInfo info = getBTCEAccountInfo(null, null, null, null, null, null, null);
    return BTCEAdapters.adaptAccountInfo(info, tradingFee);
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(String currency, String... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  public void setTradingFeeFromExchangeInfo(BTCEExchangeInfo info) {
    Map<String, BTCEPairInfo> pairs = info.getPairs();
    Iterator<BTCEPairInfo> iter = pairs.values().iterator();
    if (iter.hasNext())
      tradingFee = iter.next().getFee().movePointLeft(2);
  }
}
