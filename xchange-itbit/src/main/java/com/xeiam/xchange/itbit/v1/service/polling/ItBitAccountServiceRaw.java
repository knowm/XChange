package com.xeiam.xchange.itbit.v1.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.itbit.v1.dto.account.ItBitAccountInfoReturn;

public class ItBitAccountServiceRaw extends ItBitBasePollingService {

  private final String userId;

  /**
   * Constructor
   *
   * @param exchange
   */
  public ItBitAccountServiceRaw(Exchange exchange) {

    super(exchange);

    this.userId = (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("userId");
  }

  public ItBitAccountInfoReturn[] getItBitAccountInfo() throws IOException {

    ItBitAccountInfoReturn[] info = itBitAuthenticated.getInfo(signatureCreator, new Date().getTime(), exchange.getNonceFactory(), userId);
    return info;
  }

  public String withdrawItBitFunds(String currency, BigDecimal amount, String address) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  public String requestItBitDepositAddress(String currency, String... args) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  public ItBitAccountInfoReturn getItBitAccountInfo(String walletId) throws IOException {

    ItBitAccountInfoReturn itBitAccountInfoReturn = itBitAuthenticated.getWallet(signatureCreator, new Date().getTime(), exchange.getNonceFactory(),
        walletId);
    return itBitAccountInfoReturn;
  }
}
