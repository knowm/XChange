package org.knowm.xchange.coinbase.v2.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbase.v2.CoinbaseExchange;
import org.knowm.xchange.coinbase.v2.dto.marketdata.CoinbaseTimeData.CoinbaseTime;
import org.knowm.xchange.coinbase.v2.service.CoinbaseBaseService;

public class BaseServiceIntegration {

  static CoinbaseExchange exchange;
  static CoinbaseBaseService baseService;

  @BeforeClass
  public static void beforeClass() {
    exchange =
        (CoinbaseExchange)
            ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class.getName());
    baseService = (CoinbaseBaseService) exchange.getMarketDataService();
  }

  @Test
  public void currencyFetchTest() throws Exception {

    CoinbaseTime coinbaseTime = baseService.getCoinbaseTime();
    String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    assertThat(coinbaseTime.getIso()).startsWith(today);
    assertThat(coinbaseTime.getEpoch()).isNotNull();
  }
}
