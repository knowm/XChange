package com.xeiam.xchange.anx.v2.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.anx.v2.ANXV2;
import com.xeiam.xchange.anx.v2.dto.ANXException;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXAccountInfo;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXAccountInfoWrapper;
import com.xeiam.xchange.anx.v2.service.ANXV2Digest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.BaseMarketMetadata;
import com.xeiam.xchange.dto.marketdata.MarketMetadata;
import com.xeiam.xchange.service.polling.MarketMetadataService;
import com.xeiam.xchange.utils.Assert;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author Rafał Krupiński
 */
public class ANXMarketMetadataService extends ANXBasePollingService implements MarketMetadataService {
  private final ANXV2 anxV2;
  private final ANXV2Digest signatureCreator;
  private static final BigDecimal MAKER_DISCOUNT = new BigDecimal(".5");


  public ANXMarketMetadataService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification, nonceFactory);

    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    this.anxV2 = RestProxyFactory.createProxy(ANXV2.class, exchangeSpecification.getSslUri());
    this.signatureCreator = ANXV2Digest.createInstance(exchangeSpecification.getSecretKey());
  }

  @Override
  public MarketMetadata getMarketMetadata(CurrencyPair pair) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    int amountScale = getIntProperty(KEY_ORDER_SIZE_SCALE_DEFAULT);
    String amountMinimumStr = properties.getProperty(PREKEY_ORDER_SIZE_MIN + pair.baseSymbol);
    if (amountMinimumStr == null)
      properties.getProperty(KEY_ORDER_SIZE_MIN_DEFAULT);
    BigDecimal amountMinimum = new BigDecimal(amountMinimumStr).setScale(amountScale);

    int priceScale = getIntProperty(KEY_ORDER_PRICE_SCALE_DEFAULT);

    BigDecimal orderFee = getANXAccountInfo().getTradeFee().movePointLeft(2);
    if(!getBoolProperty(KEY_ORDER_FEE_POLICY_MAKER)) {
      orderFee = orderFee.multiply(MAKER_DISCOUNT);
    }

    return new BaseMarketMetadata(amountMinimum, priceScale, orderFee);
  }

  protected int getIntProperty(String key) {

    return Integer.parseInt(properties.getProperty(key));
  }

  protected boolean getBoolProperty(String key){

    String str = properties.getProperty(key);
    return str != null && Boolean.parseBoolean(str);
  }

  protected ANXAccountInfo getANXAccountInfo() throws IOException {

    try {
      ANXAccountInfoWrapper anxAccountInfoWrapper = anxV2.getAccountInfo(exchangeSpecification.getApiKey(), signatureCreator, getNonce());
      return anxAccountInfoWrapper.getANXAccountInfo();
    } catch (ANXException e) {
      throw new ExchangeException("Error calling getAccountInfo(): " + e.getError(), e);
    }
  }

}
