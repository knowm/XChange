package org.knowm.xchange.ftx;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.ftx.dto.FtxResponse;
import org.knowm.xchange.ftx.dto.trade.FtxOrderDto;

public class FtxAdapterTest {

  @Test
  public void adaptCurrencyPairToFtxPair() {
    assertPair("BTC-USD", "BTC/USD", "BTC/USD");
    assertPair("BTC-PERP", "BTC/PERP", "BTC-PERP");
    assertPair("BTC-0625", "BTC/0625", "BTC-0625");
  }

  private void assertPair(String market, String expString, String expAdapted) {
    CurrencyPair currencyPair = new CurrencyPair(market);
    assertThat(currencyPair.toString()).isEqualTo(expString);
    assertThat(FtxAdapters.adaptCurrencyPairToFtxMarket(currencyPair)).isEqualTo(expAdapted);
  }

  @Test
  public void adaptOpenOrdersTest() throws IOException {
    InputStream is =
        FtxAdapterTest.class.getResourceAsStream("/responses/example-ftxOpenOrders.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    FtxResponse<List<FtxOrderDto>> ftxResponse =
        mapper.readValue(is, new TypeReference<FtxResponse<List<FtxOrderDto>>>() {});

    assertThat(FtxAdapters.adaptOpenOrders(ftxResponse).getOpenOrders().size()).isEqualTo(1);
  }

  @Test
  public void adaptUserTradesTest() throws IOException {
    InputStream is =
        FtxAdapterTest.class.getResourceAsStream("/responses/example-ftxOrderHistory.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    FtxResponse<List<FtxOrderDto>> ftxResponse =
        mapper.readValue(is, new TypeReference<FtxResponse<List<FtxOrderDto>>>() {});

    assertThat(FtxAdapters.adaptOpenOrders(ftxResponse).getOpenOrders().size()).isEqualTo(1);
  }
}
