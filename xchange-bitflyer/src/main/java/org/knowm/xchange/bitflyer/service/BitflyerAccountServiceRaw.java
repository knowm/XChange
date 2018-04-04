package org.knowm.xchange.bitflyer.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitflyer.dto.BitflyerException;
import org.knowm.xchange.bitflyer.dto.account.BitflyerAddress;
import org.knowm.xchange.bitflyer.dto.account.BitflyerBalance;
import org.knowm.xchange.bitflyer.dto.account.BitflyerBankAccount;
import org.knowm.xchange.bitflyer.dto.account.BitflyerCoinHistory;
import org.knowm.xchange.bitflyer.dto.account.BitflyerDepositOrWithdrawal;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarginAccount;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarginStatus;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarginTransaction;
import org.knowm.xchange.bitflyer.dto.account.BitflyerWithdrawRequest;
import org.knowm.xchange.bitflyer.dto.account.BitflyerWithdrawResponse;

public class BitflyerAccountServiceRaw extends BitflyerBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitflyerAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public List<String> getPermissions() throws IOException {
    try {
      return bitflyer.getPermissions(apiKey, exchange.getNonceFactory(), signatureCreator);
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public List<BitflyerAddress> getAddresses() throws IOException {
    try {
      return bitflyer.getAddresses(apiKey, exchange.getNonceFactory(), signatureCreator);
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public List<BitflyerCoinHistory> getCoinIns() throws IOException {
    try {
      return bitflyer.getCoinIns(apiKey, exchange.getNonceFactory(), signatureCreator);
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public List<BitflyerCoinHistory> getCoinOuts() throws IOException {
    try {
      return bitflyer.getCoinOuts(apiKey, exchange.getNonceFactory(), signatureCreator);
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public List<BitflyerBankAccount> getBankAccounts() throws IOException {
    try {
      return bitflyer.getBankAccounts(apiKey, exchange.getNonceFactory(), signatureCreator);
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public List<BitflyerDepositOrWithdrawal> getCashDeposits() throws IOException {
    try {
      return bitflyer.getCashDeposits(apiKey, exchange.getNonceFactory(), signatureCreator);
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public BitflyerWithdrawResponse withdrawFunds(
      String currencyCode, String bankAccountID, BigDecimal amount) throws IOException {
    try {
      return bitflyer.withdrawFunds(
          apiKey,
          exchange.getNonceFactory(),
          signatureCreator,
          new BitflyerWithdrawRequest(currencyCode, bankAccountID, amount));
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public List<BitflyerDepositOrWithdrawal> getWithdrawals() throws IOException {
    try {
      return bitflyer.getWithdrawals(apiKey, exchange.getNonceFactory(), signatureCreator);
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public BitflyerMarginStatus getBitflyerMarginStatus() throws IOException {

    try {
      return bitflyer.getMarginStatus(apiKey, exchange.getNonceFactory(), signatureCreator);
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

  public List<BitflyerBalance> getBitflyerBalances() throws IOException {
    try {
      return bitflyer.getBalances(apiKey, exchange.getNonceFactory(), signatureCreator);
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
