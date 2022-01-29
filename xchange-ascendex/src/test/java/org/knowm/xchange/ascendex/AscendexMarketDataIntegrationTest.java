package org.knowm.xchange.ascendex;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexBarHistDto;
import org.knowm.xchange.ascendex.service.AscendexMarketDataService;

public class AscendexMarketDataIntegrationTest {

  @Test
  public void testBarHist() throws IOException {
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(AscendexExchange.class.getCanonicalName());
    exchange.remoteInit();

    List<AscendexBarHistDto> barHistDtos =
        ((AscendexMarketDataService) exchange.getMarketDataService())
            .getBarHistoryData("BTC/USDT", "15", null, null, 100);
    Assert.assertTrue(Objects.nonNull(barHistDtos) && !barHistDtos.isEmpty());
  }
}
