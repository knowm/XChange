package org.knowm.xchange.kuna.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/** @author Dat Bui */
public class KunaAccountService extends KunaAccountServiceRaw implements AccountService {

  /**
   * Constructor.
   *
   * @param exchange
   */
  public KunaAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
