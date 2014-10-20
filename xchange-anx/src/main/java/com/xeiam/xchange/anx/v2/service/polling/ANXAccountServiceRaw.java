package com.xeiam.xchange.anx.v2.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
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
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected ANXAccountServiceRaw(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification, nonceFactory);

    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    this.anxV2 = RestProxyFactory.createProxy(ANXV2.class, exchangeSpecification.getSslUri());
    this.signatureCreator = ANXV2Digest.createInstance(exchangeSpecification.getSecretKey());
  }

  public ANXAccountInfo getANXAccountInfo() throws IOException {

    try {
      ANXAccountInfoWrapper anxAccountInfoWrapper = anxV2.getAccountInfo(exchangeSpecification.getApiKey(), signatureCreator, getNonce());
      return anxAccountInfoWrapper.getANXAccountInfo();
    } catch (ANXException e) {
      throw new ExchangeException("Error calling getAccountInfo(): " + e.getError(), e);
    }
  }

  public ANXWithdrawalResponse anxWithdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    try {
      ANXWithdrawalResponseWrapper anxWithdrawalResponseWrapper =
          anxV2.withdrawBtc(exchangeSpecification.getApiKey(), signatureCreator, getNonce(), currency, address, amount.multiply(new BigDecimal(ANXUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR_2))
              .intValue(), 1, false, false);
      return anxWithdrawalResponseWrapper.getAnxWithdrawalResponse();
    } catch (ANXException e) {
      throw new ExchangeException("Error calling withdrawFunds(): " + e.getError(), e);
    }
  }

  public ANXBitcoinDepositAddress anxRequestDepositAddress(String currency) throws IOException {

    try {
      ANXBitcoinDepositAddressWrapper anxBitcoinDepositAddressWrapper = anxV2.requestDepositAddress(exchangeSpecification.getApiKey(), signatureCreator, getNonce(), currency);
      return anxBitcoinDepositAddressWrapper.getAnxBitcoinDepositAddress();
    } catch (ANXException e) {
      throw new ExchangeException("Error calling requestBitcoinDepositAddress(): " + e.getError(), e);
    }
  }
}
