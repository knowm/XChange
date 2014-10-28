package com.xeiam.xchange.anx.v2.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.anx.v2.ANXAdapters;
import com.xeiam.xchange.anx.v2.ANXV2;
import com.xeiam.xchange.anx.v2.dto.ANXException;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXAccountInfo;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXAccountInfoWrapper;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXMarketMetadata;
import com.xeiam.xchange.anx.v2.service.ANXV2Digest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.MarketMetadataService;
import com.xeiam.xchange.utils.Assert;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

import static com.xeiam.xchange.utils.ConfigurationManager.CFG_MGR;

/**
 * @author Rafał Krupiński
 */
public class ANXMarketMetadataService extends ANXBasePollingService implements MarketMetadataService {
  private final ANXV2 anxV2;
  private final ANXV2Digest signatureCreator;

  public ANXMarketMetadataService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification, nonceFactory);

    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    this.anxV2 = RestProxyFactory.createProxy(ANXV2.class, exchangeSpecification.getSslUri());
    this.signatureCreator = ANXV2Digest.createInstance(exchangeSpecification.getSecretKey());
  }

  @Override
  public ANXMarketMetadata getMarketMetadata(CurrencyPair pair) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    Properties properties = CFG_MGR.getProperties();
    int amountScale = getIntProperty(KEY_ORDER_SIZE_SCALE_DEFAULT, properties);

    String amountMinimumStr = properties.getProperty(PREKEY_ORDER_SIZE_MIN + pair.baseSymbol);
    if (amountMinimumStr == null)
      amountMinimumStr = properties.getProperty(KEY_ORDER_SIZE_MIN_DEFAULT);
    BigDecimal amountMinimum = new BigDecimal(amountMinimumStr).setScale(amountScale);

    String amountMaximumStr = properties.getProperty(PREKEY_ORDER_SIZE_MAX + pair.baseSymbol);
    if (amountMaximumStr == null)
      amountMaximumStr = properties.getProperty(KEY_ORDER_SIZE_MAX_DEFAULT);
    BigDecimal amountMaximum = new BigDecimal(amountMaximumStr).setScale(amountScale);

    int priceScale = getIntProperty(KEY_ORDER_PRICE_SCALE_DEFAULT, properties);

    BigDecimal orderFee = ANXAdapters.percentToFactor(getANXAccountInfo().getTradeFee());

    if(!getBoolProperty(KEY_ORDER_FEE_POLICY_MAKER, properties)) {
      orderFee = orderFee.multiply(new BigDecimal(properties.getProperty(KEY_ORDER_FEE_DISCOUNT)));
    }

    return new ANXMarketMetadata(amountMinimum, amountMaximum, priceScale, orderFee);
  }

  protected int getIntProperty(String key, Properties properties) {

    return Integer.parseInt(properties.getProperty(key));
  }

  protected boolean getBoolProperty(String key, Properties properties){

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
