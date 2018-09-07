package org.knowm.xchange.bity;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.bity.dto.BityOrders;
import org.knowm.xchange.bity.dto.BityTickers;
import org.knowm.xchange.bity.dto.account.BityOrder;
import org.knowm.xchange.bity.dto.marketdata.BityPairs;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

public class BityAdaptersTest {

  @Test
  public void testTickersAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        BityAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bity/dto/marketdata/example-tickers-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    BityTickers tickers = mapper.readValue(is, BityTickers.class);
    assertThat(tickers).isNotNull();
    is.close();

    List<Ticker> tickersList = BityAdapters.adaptTickers(tickers.getObjects());
    assertThat(tickersList).isNotEmpty();

    BityTickersHolder tickersHolder = new BityTickersHolder(tickersList);
    Ticker ticker = tickersHolder.getTicker(new CurrencyPair("ETH", "CHF"));

    assertThat(ticker.getLast().toString()).isEqualTo("467.29460000");
    assertThat(ticker.getBid().toString()).isEqualTo("460.21920000");
    assertThat(ticker.getAsk().toString()).isEqualTo("475.53340000");
  }

  @Test
  public void testExchangeMetaDataAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        BityAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bity/dto/marketdata/example-currencies.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    BityPairs pairs = mapper.readValue(is, BityPairs.class);
    is.close();

    assertThat(pairs).isNotNull();
    assertThat(pairs.getPairs()).isNotEmpty();
  }

  @Test
  public void testUsersTradesAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        BityAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bity/dto/marketdata/example-trades.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    BityOrders trades = mapper.readValue(is, BityOrders.class);
    is.close();
    assertThat(trades).isNotNull();

    BityOrder order = trades.getObjects().get(1);
    assertThat(order.getBityInputTransactions().get(0).getAmount().toString())
        .isEqualTo("30.00000000");
    assertThat(order.getBityOutputTransactions().get(0).getAmount().toString())
        .isEqualTo("0.07161155");
  }
}
