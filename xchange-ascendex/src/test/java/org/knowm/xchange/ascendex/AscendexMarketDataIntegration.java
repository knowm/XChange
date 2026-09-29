package org.knowm.xchange.ascendex;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexBarHistDto;
import org.knowm.xchange.ascendex.service.AscendexMarketDataService;

class AscendexMarketDataIntegration {

  @Test
  void barHist() throws Exception {
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(AscendexExchange.class.getCanonicalName());
    exchange.remoteInit();

    List<AscendexBarHistDto> barHistDtos =
        ((AscendexMarketDataService) exchange.getMarketDataService())
            .getBarHistoryData("BTC/USDT", "15", null, null, 100);
    assertThat(Objects.nonNull(barHistDtos) && !barHistDtos.isEmpty()).isTrue();
  }
}
