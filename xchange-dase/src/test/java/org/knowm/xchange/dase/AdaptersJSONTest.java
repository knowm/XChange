package org.knowm.xchange.dase;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dase.dto.account.ApiAccountTxn;
import org.knowm.xchange.dase.dto.marketdata.DaseOrderBookSnapshot;
import org.knowm.xchange.dase.dto.marketdata.DaseTicker;
import org.knowm.xchange.dase.dto.marketdata.DaseTrade;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;

public class AdaptersJSONTest {

  private static final CurrencyPair PAIR = CurrencyPair.ADA_EUR;

  @Test
  public void adaptTicker() throws Exception {
    InputStream is =
        getClass()
            .getResourceAsStream("/org/knowm/xchange/dase/dto/marketdata/example-ticker.json");
    DaseTicker raw = new ObjectMapper().readValue(is, DaseTicker.class);
    Ticker t = DaseAdapters.adaptTicker(raw, PAIR);

    assertThat(t.getInstrument()).isEqualTo(PAIR);
    assertThat(t.getAsk()).isNotNull();
    assertThat(t.getBid()).isNotNull();
    assertThat(t.getLast()).isNotNull();
  }

  @Test
  public void adaptOrderBook() throws Exception {
    InputStream is =
        getClass()
            .getResourceAsStream("/org/knowm/xchange/dase/dto/marketdata/example-snapshot.json");
    DaseOrderBookSnapshot raw = new ObjectMapper().readValue(is, DaseOrderBookSnapshot.class);
    OrderBook ob = DaseAdapters.adaptOrderBook(raw, PAIR);

    assertThat(ob.getAsks()).isNotEmpty();
    assertThat(ob.getBids()).isNotEmpty();
    assertThat(ob.getTimeStamp()).isNotNull();
  }

  @Test
  public void adaptTrades() throws Exception {
    InputStream is =
        getClass()
            .getResourceAsStream("/org/knowm/xchange/dase/dto/marketdata/example-trades.json");
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(is);
    List<DaseTrade> list =
        StreamSupport.stream(root.get("trades").spliterator(), false)
            .map(n -> mapper.convertValue(n, DaseTrade.class))
            .collect(Collectors.toList());
    Trades tr = DaseAdapters.adaptTrades(list, PAIR);

    assertThat(tr.getTrades()).hasSize(2);
    assertThat(tr.getTrades().get(0).getInstrument()).isEqualTo(PAIR);
    // maker_side: sell -> taker side buy -> BID
    assertThat(tr.getTrades().get(0).getType())
        .isEqualTo(org.knowm.xchange.dto.Order.OrderType.BID);
    // maker_side: buy -> taker side sell -> ASK
    assertThat(tr.getTrades().get(1).getType())
        .isEqualTo(org.knowm.xchange.dto.Order.OrderType.ASK);
  }

  @Test
  public void adaptFundingRecords_mapping() {
    ApiAccountTxn deposit =
        new ApiAccountTxn(
            "6a0b7c40-1e16-4e1c-a4c5-1d9fdf7e9d21",
            "EUR",
            "deposit",
            new BigDecimal("50.00"),
            1719354237834L,
            null,
            null);
    ApiAccountTxn fee =
        new ApiAccountTxn(
            "7e2f8c91-4d5e-4a6b-9c8d-2e3f4a5b6c7d",
            "BTC",
            "trade_fill_fee_base",
            new BigDecimal("0.0001"),
            1719354237835L,
            null,
            null);

    List<FundingRecord> out = DaseAdapters.adaptFundingRecords(Arrays.asList(deposit, fee));
    assertThat(out).hasSize(2);
    assertThat(out.get(0).getType()).isEqualTo(FundingRecord.Type.DEPOSIT);
    assertThat(out.get(0).getCurrency().getCurrencyCode()).isEqualTo("EUR");
    assertThat(out.get(1).getType()).isEqualTo(FundingRecord.Type.OTHER_OUTFLOW);
    assertThat(out.get(1).getCurrency().getCurrencyCode()).isEqualTo("BTC");
  }
}
