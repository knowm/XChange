package org.knowm.xchange.acx;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.acx.dto.marketdata.AcxOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;

public class AcxMapperTest {

  private AcxMapper mapper;

  @Before
  public void setup() {
    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
    currencyPairs.put(
        CurrencyPair.ETH_USDT,
        new CurrencyPairMetaData(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN, 4, null));
    currencyPairs.put(
        new CurrencyPair(Currency.USDT, Currency.AUD),
        new CurrencyPairMetaData(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN, 4, null));
    ExchangeMetaData exchangeMetaData =
        new ExchangeMetaData(currencyPairs, new HashMap<>(), null, null, false);
    mapper = new AcxMapper(exchangeMetaData);
  }

  @Test
  public void shouldReturnPartiallyCancelled() {
    AcxOrder acxOrder =
        new AcxOrder(
            "1",
            "buy",
            "limit",
            BigDecimal.ONE,
            BigDecimal.TEN,
            "cancel",
            "btcaud",
            new Date(),
            BigDecimal.ONE,
            new BigDecimal("0.5"),
            new BigDecimal("0.5"),
            1);
    Order.OrderStatus status = mapper.mapOrderStatus(acxOrder);
    assertThat(status).isEqualTo(Order.OrderStatus.PARTIALLY_CANCELED);
  }

  @Test
  public void shouldReturnCancelled() {
    AcxOrder acxOrder =
        new AcxOrder(
            "1",
            "buy",
            "limit",
            BigDecimal.ONE,
            BigDecimal.TEN,
            "cancel",
            "btcaud",
            new Date(),
            BigDecimal.ONE,
            BigDecimal.ONE,
            BigDecimal.ZERO,
            0);
    Order.OrderStatus status = mapper.mapOrderStatus(acxOrder);
    assertThat(status).isEqualTo(Order.OrderStatus.CANCELED);
  }

  @Test
  public void testMapCurrencyPairWithThreeCharCurrency() {
    assertThat(mapper.mapCurrencyPair("btcaud")).isEqualTo(CurrencyPair.BTC_AUD);
  }

  @Test
  public void testGetCurrencyPairContainingUsdtCounter() {
    assertThat(mapper.mapCurrencyPair("ethusdt")).isEqualTo(CurrencyPair.ETH_USDT);
  }

  @Test
  public void testGetCurrencyPairContainingUsdtBase() {
    assertThat(mapper.mapCurrencyPair("usdtaud"))
        .isEqualTo(new CurrencyPair(Currency.USDT, Currency.AUD));
  }
}
