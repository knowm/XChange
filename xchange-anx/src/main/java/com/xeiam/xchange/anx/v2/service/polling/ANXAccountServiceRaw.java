package com.xeiam.xchange.anx.v2.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.anx.ANXUtils;
import com.xeiam.xchange.anx.v2.ANXV2;
import com.xeiam.xchange.anx.v2.dto.ANXException;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXAccountInfo;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXAccountInfoWrapper;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXBitcoinDepositAddress;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXBitcoinDepositAddressWrapper;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXWithdrawalResponse;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXWithdrawalResponseWrapper;
import com.xeiam.xchange.anx.v2.service.ANXV2Digest;
import com.xeiam.xchange.utils.Assert;

public class ANXAccountServiceRaw extends ANXBasePollingService {

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
      ANXWithdrawalResponseWrapper anxWithdrawalResponseWrapper = anxV2.withdrawBtc(exchange.getExchangeSpecification().getApiKey(),
          signatureCreator, exchange.getNonceFactory(), currency, address,
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
