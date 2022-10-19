package org.knowm.xchange.okex;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.okex.dto.OkexResponse;
import org.knowm.xchange.okex.dto.marketdata.OkexCandleStick;
import org.knowm.xchange.okex.service.OkexMarketDataService;

public class OkexMarketDataIntegrationTest {

  @Test
  public void testCandleHist() throws IOException {
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(OkexExchange.class.getCanonicalName());
    exchange.remoteInit();
    ((OkexMarketDataService) exchange.getMarketDataService()).getOkexOrderbook("BTC-USDT");
    OkexResponse<List<OkexCandleStick>> barHistDtos =
        ((OkexMarketDataService) exchange.getMarketDataService())
            .getHistoryCandle("BTC-USDT", null, null, null, null);
    Assert.assertTrue(Objects.nonNull(barHistDtos) && !barHistDtos.getData().isEmpty());
  }
}
