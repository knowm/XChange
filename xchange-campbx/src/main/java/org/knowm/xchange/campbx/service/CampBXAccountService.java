package org.knowm.xchange.campbx.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.campbx.dto.CampBXResponse;
import org.knowm.xchange.campbx.dto.account.MyFunds;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matija Mazi
 */
public class CampBXAccountService extends CampBXAccountServiceRaw implements AccountService {

  private final Logger logger = LoggerFactory.getLogger(CampBXAccountService.class);

  /**
   * Constructor
   *
   * @param exchange
   */
  public CampBXAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    MyFunds myFunds = getCampBXAccountInfo();
    logger.debug("myFunds = {}", myFunds);

    if (!myFunds.isError()) {
      // TODO move to adapter class
      // TODO: what does MyFunds.liquid* mean? means available amount of the wallet?
      return new AccountInfo(exchange.getExchangeSpecification().getUserName(),
          new Wallet(new Balance(Currency.BTC, myFunds.getTotalBTC()), new Balance(Currency.USD, myFunds.getTotalUSD())));
    } else {
      throw new ExchangeException("Error calling getAccountInfo(): " + myFunds.getError());
    }
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    CampBXResponse campBXResponse = withdrawCampBXFunds(amount, address);
    logger.debug("campBXResponse = {}", campBXResponse);

    if (!campBXResponse.isError()) {
      return campBXResponse.getSuccess();
    } else {
      throw new ExchangeException("Error calling withdrawFunds(): " + campBXResponse.getError());
    }
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    CampBXResponse campBXResponse = requestCampBXBitcoinDepositAddress();
    logger.debug("campBXResponse = {}", campBXResponse);

    if (!campBXResponse.isError()) {
      return campBXResponse.getSuccess();
    } else {
      throw new ExchangeException("Error calling requestBitcoinDepositAddress(): " + campBXResponse.getError());
    }
  }

}
