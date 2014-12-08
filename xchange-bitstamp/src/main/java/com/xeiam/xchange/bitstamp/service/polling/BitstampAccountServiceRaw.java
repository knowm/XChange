package com.xeiam.xchange.bitstamp.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.BitstampAuthenticated;
import com.xeiam.xchange.bitstamp.BitstampUtils;
import com.xeiam.xchange.bitstamp.dto.account.BitstampBalance;
import com.xeiam.xchange.bitstamp.dto.account.BitstampDepositAddress;
import com.xeiam.xchange.bitstamp.dto.account.BitstampWithdrawal;
import com.xeiam.xchange.bitstamp.dto.account.DepositTransaction;
import com.xeiam.xchange.bitstamp.dto.account.WithdrawalRequest;
import com.xeiam.xchange.bitstamp.service.BitstampDigest;

/**
 * @author gnandiga
 */
public class BitstampAccountServiceRaw extends BitstampBasePollingService {

  private final BitstampDigest signatureCreator;
  private final BitstampAuthenticated bitstampAuthenticated;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected BitstampAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    this.bitstampAuthenticated = RestProxyFactory.createProxy(BitstampAuthenticated.class, exchangeSpecification.getSslUri());
    this.signatureCreator = BitstampDigest.createInstance(exchangeSpecification.getSecretKey(), exchangeSpecification.getUserName(), exchangeSpecification.getApiKey());
  }

  public BitstampBalance getBitstampBalance() throws IOException {

    BitstampBalance bitstampBalance = bitstampAuthenticated.getBalance(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce());
    if (bitstampBalance.getError() != null) {
      throw new ExchangeException("Error getting balance. " + bitstampBalance.getError());
    }
    return bitstampBalance;
  }

  public BitstampWithdrawal withdrawBitstampFunds(final BigDecimal amount, final String address) throws IOException {

    final BitstampWithdrawal response = bitstampAuthenticated.withdrawBitcoin(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce(), amount, address);
    if (response.getError() != null) {
      throw new ExchangeException("Withdrawing funds from Bitstamp failed: " + response.getError());
    }
    return response;

  }

  public BitstampDepositAddress getBitstampBitcoinDepositAddress() throws IOException {

    final BitstampDepositAddress response = bitstampAuthenticated.getBitcoinDepositAddress(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce());
    if (response.getError() != null) {
      throw new ExchangeException("Requesting Bitcoin deposit address failed: " + response.getError());
    }
    return response;
  }

  public List<DepositTransaction> getUnconfirmedDeposits() throws IOException {

    final List<DepositTransaction> response = Arrays.asList(
        bitstampAuthenticated.getUnconfirmedDeposits(
            exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce())
    );
    return response;
  }

  public List<WithdrawalRequest> getWithdrawalRequests() throws IOException {

    final List<WithdrawalRequest> response = Arrays.asList(
        bitstampAuthenticated.getWithdrawalRequests(
            exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce())
    );
    return response;
  }

}
