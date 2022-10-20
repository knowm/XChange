package org.knowm.xchange.okex;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.okex.dto.OkexResponse;
import org.knowm.xchange.okex.dto.marketdata.OkexCandleStick;
import org.knowm.xchange.okex.service.OkexMarketDataService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

  @Test
  public void testInstrumentOkexConvertions(){
    assertThat(OkexAdapters.adaptOkexInstrumentIdToInstrument("BTC-USDT-SWAP")).isEqualTo(new FuturesContract("BTC/USDT/SWAP"));
    assertThat(OkexAdapters.adaptInstrumentToOkexInstrumentId(new CurrencyPair("BTC/USDT/SWAP"))).isEqualTo("BTC-USDT-SWAP");
    assertThat(OkexAdapters.adaptOkexInstrumentIdToInstrument("BTC-USDT")).isEqualTo(new CurrencyPair("BTC/USDT"));
    assertThat(OkexAdapters.adaptInstrumentToOkexInstrumentId(new CurrencyPair("BTC/USDT"))).isEqualTo("BTC-USDT");
  }
}
