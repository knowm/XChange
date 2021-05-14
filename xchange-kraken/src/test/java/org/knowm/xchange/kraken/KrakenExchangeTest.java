package org.knowm.xchange.kraken;

import java.io.IOException;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;

@Ignore
public class KrakenExchangeTest {
  protected static ExchangeSpecification exchangeSpecification;
  protected static Exchange exchange;

  @BeforeClass
  public static void setUpBaseClass() {
    exchangeSpecification = new ExchangeSpecification(KrakenExchange.class);
    exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
  }

  @Test
  public void minimunOrderSizeTest() throws IOException {
    exchange.remoteInit();
    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs =
        exchange.getExchangeMetaData().getCurrencyPairs();

    Map<Currency, CurrencyMetaData> currencies = exchange.getExchangeMetaData().getCurrencies();

    Assert.assertEquals(
        1, currencyPairs.get(CurrencyPair.ADA_BTC).getMinimumAmount().doubleValue(), 0.01);
    Assert.assertEquals(
        1, currencyPairs.get(CurrencyPair.ADA_ETH).getMinimumAmount().doubleValue(), 0.01);
    Assert.assertEquals(
        1, currencyPairs.get(CurrencyPair.ADA_USD).getMinimumAmount().doubleValue(), 0.01);
    Assert.assertEquals(
        0.000002,
        currencyPairs.get(CurrencyPair.BCH_USD).getMinimumAmount().doubleValue(),
        0.000001);
    Assert.assertEquals(
        1, currencyPairs.get(CurrencyPair.ATOM_ETH).getMinimumAmount().doubleValue(), 0.01);

    Assert.assertEquals(0.3, currencies.get(Currency.ADA).getWithdrawalFee().doubleValue(), 0.01);
  }
}
