package org.knowm.xchange.gateio.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gateio.GateioExchangeWiremock;
import org.knowm.xchange.gateio.dto.marketdata.GateioMarketInfoWrapper;
import org.knowm.xchange.gateio.dto.marketdata.GateioMarketInfoWrapper.GateioMarketInfo;

public class GateioMarketDataServiceRawTest extends GateioExchangeWiremock {

  GateioMarketDataServiceRaw gateioMarketDataServiceRaw = (GateioMarketDataServiceRaw) exchange.getMarketDataService();

  @Test
  public void valid_marketinfo() throws IOException {

    Map<CurrencyPair, GateioMarketInfoWrapper.GateioMarketInfo> expected = new HashMap<>();
    expected.put(CurrencyPair.BTC_USDT,
        new GateioMarketInfo(CurrencyPair.BTC_USDT, 1, new BigDecimal("0.00010"), new BigDecimal("0.2")));
    expected.put(CurrencyPair.ETH_USDT,
        new GateioMarketInfo(CurrencyPair.ETH_USDT, 2, new BigDecimal("0.001"), new BigDecimal("0.2")));

    Map<CurrencyPair, GateioMarketInfoWrapper.GateioMarketInfo> actual = gateioMarketDataServiceRaw.getGateioMarketInfo();
    assertThat(actual).isEqualTo(expected);
  }

}