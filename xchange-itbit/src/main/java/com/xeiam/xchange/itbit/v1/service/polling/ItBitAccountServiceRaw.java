package com.xeiam.xchange.itbit.v1.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.itbit.v1.dto.account.ItBitAccountInfoReturn;

public class ItBitAccountServiceRaw extends ItBitBasePollingService {

  private final String userId;

  /**
   * Constructor
   *
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public ItBitAccountServiceRaw(Exchange exchange, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchange, nonceFactory);

    this.userId = (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("userId");
  }

  public ItBitAccountInfoReturn[] getItBitAccountInfo() throws IOException {

    ItBitAccountInfoReturn[] info = itBit.getInfo(signatureCreator, new Date().getTime(), nonceFactory, userId);
    return info;
  }

  public String withdrawItBitFunds(String currency, BigDecimal amount, String address) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  public String requestItBitDepositAddress(String currency, String... args) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  public ItBitAccountInfoReturn getItBitAccountInfo(String walletId) throws IOException {

    ItBitAccountInfoReturn itBitAccountInfoReturn = itBit.getWallet(signatureCreator, new Date().getTime(), nonceFactory, walletId);
    return itBitAccountInfoReturn;
  }
}
