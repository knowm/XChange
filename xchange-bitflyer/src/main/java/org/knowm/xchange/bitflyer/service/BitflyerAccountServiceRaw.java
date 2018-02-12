package org.knowm.xchange.bitflyer.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitflyer.dto.BitflyerException;
import org.knowm.xchange.bitflyer.dto.account.BitflyerBalance;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarginAccount;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarginStatus;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarginTransaction;

public class BitflyerAccountServiceRaw extends BitflyerBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitflyerAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BitflyerMarginStatus getBitflyerMarginStatus() throws IOException {

    try {
      return bitflyer.getMarginStatus(apiKey, exchange.getNonceFactory(), signatureCreator);
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public List<BitflyerBalance> getBitflyerBalances() throws IOException {
    try {
      return bitflyer.getBalances(apiKey, exchange.getNonceFactory(), signatureCreator);
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public List<BitflyerMarginAccount> getBitflyerMarginAccounts() throws IOException {

    try {
      return bitflyer.getMarginAccounts(apiKey, exchange.getNonceFactory(), signatureCreator);
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public List<BitflyerMarginTransaction> getBitflyerMarginHistory() throws IOException {

    try {
      return bitflyer.getMarginHistory(apiKey, exchange.getNonceFactory(), signatureCreator);
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }
}
