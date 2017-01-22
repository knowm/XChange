package org.knowm.xchange.anx.v2.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.anx.ANXUtils;
import org.knowm.xchange.anx.v2.ANXV2;
import org.knowm.xchange.anx.v2.dto.ANXException;
import org.knowm.xchange.anx.v2.dto.account.ANXAccountInfo;
import org.knowm.xchange.anx.v2.dto.account.ANXAccountInfoWrapper;
import org.knowm.xchange.anx.v2.dto.account.ANXBitcoinDepositAddress;
import org.knowm.xchange.anx.v2.dto.account.ANXBitcoinDepositAddressWrapper;
import org.knowm.xchange.anx.v2.dto.account.ANXWithdrawalResponse;
import org.knowm.xchange.anx.v2.dto.account.ANXWithdrawalResponseWrapper;
import org.knowm.xchange.utils.Assert;

import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.RestProxyFactory;

public class ANXAccountServiceRaw extends ANXBaseService {

  private final ANXV2 anxV2;
  private final ANXV2Digest signatureCreator;

  /**
   * Constructor
   */
  protected ANXAccountServiceRaw(Exchange exchange) {

    super(exchange);

    Assert.notNull(exchange.getExchangeSpecification().getSslUri(), "Exchange specification URI cannot be null");
    this.anxV2 = RestProxyFactory.createProxy(ANXV2.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = ANXV2Digest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public ANXAccountInfo getANXAccountInfo() throws IOException {

    try {
      ANXAccountInfoWrapper anxAccountInfoWrapper = anxV2.getAccountInfo(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
          exchange.getNonceFactory());
      return anxAccountInfoWrapper.getANXAccountInfo();
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXWithdrawalResponse anxWithdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    try {
      ANXWithdrawalResponseWrapper anxWithdrawalResponseWrapper = anxV2.withdrawBtc(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
          exchange.getNonceFactory(), currency, address,
          amount.multiply(new BigDecimal(ANXUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR_2)).intValue(), 1, false, false);
      return anxWithdrawalResponseWrapper.getAnxWithdrawalResponse();
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXBitcoinDepositAddress anxRequestDepositAddress(String currency) throws IOException {

    try {
      ANXBitcoinDepositAddressWrapper anxBitcoinDepositAddressWrapper = anxV2.requestDepositAddress(exchange.getExchangeSpecification().getApiKey(),
          signatureCreator, exchange.getNonceFactory(), currency);
      return anxBitcoinDepositAddressWrapper.getAnxBitcoinDepositAddress();
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }
}
