package org.knowm.xchange.kraken.dto.trading;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Test;
import org.knowm.xchange.kraken.dto.trade.KrakenOrder;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderDescription;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderResponse;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderStatus;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderType;
import org.knowm.xchange.kraken.dto.trade.KrakenTrade;
import org.knowm.xchange.kraken.dto.trade.KrakenType;
import org.knowm.xchange.kraken.dto.trade.results.KrakenCancelOrderResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenCancelOrderResult.KrakenCancelOrderResponse;
import org.knowm.xchange.kraken.dto.trade.results.KrakenOpenOrdersResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenOrderResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenTradeHistoryResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenTradeHistoryResult.KrakenTradeHistory;

public class KrakenTradeJsonTest {

  @Test
  public void testOrderUnmarshall() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenTradeJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/kraken/dto/trading/example-openorders-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenOpenOrdersResult krakenResult = mapper.readValue(is, KrakenOpenOrdersResult.class);
    Entry<String, KrakenOrder> openOrderEntry =
        krakenResult.getResult().getOrders().entrySet().iterator().next();
    KrakenOrder order = openOrderEntry.getValue();

    // Verify that the example data was unmarshalled correctly
    assertThat(openOrderEntry.getKey()).isEqualTo("O767CW-TXHCL-FWZ5R2");
    assertThat(order.getOpenTimestamp()).isEqualTo(1499872460.2572);
    assertThat(order.getPrice()).isEqualTo("0.000000000");
    assertThat(order.getVolume()).isEqualTo("1000.00000000");
    assertThat(order.getVolumeExecuted()).isEqualTo("0.00000000");
    assertThat(order.getStatus()).isEqualTo(KrakenOrderStatus.OPEN);
    KrakenOrderDescription orderDescription = order.getOrderDescription();
    assertThat(orderDescription.getAssetPair()).isEqualTo("XRPXBT");
    assertThat(orderDescription.getLeverage()).isEqualTo("none");
    assertThat(orderDescription.getOrderDescription())
        .isEqualTo("buy 1000.00000000 XRPXBT @ limit 0.00001000");
    assertThat(orderDescription.getOrderType()).isEqualTo(KrakenOrderType.LIMIT);
    assertThat(orderDescription.getType()).isEqualTo(KrakenType.BUY);
    assertThat(orderDescription.getPrice()).isEqualTo("0.00001000");
    assertThat(orderDescription.getSecondaryPrice()).isEqualTo("0");
  }

  @Test
  public void testTradeHistoryUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenTradeJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/kraken/dto/trading/example-tradehistory-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenTradeHistoryResult krakenResult = mapper.readValue(is, KrakenTradeHistoryResult.class);
    KrakenTradeHistory krakenTradeHistory = krakenResult.getResult();
    Map<String, KrakenTrade> krakenTradeHistoryMap = krakenTradeHistory.getTrades();
    KrakenTrade trade = krakenTradeHistoryMap.get("TY5BYV-WJUQF-XPYEYD-1");

    assertThat(trade.getAssetPair()).isEqualTo("XLTCXXBT");
    assertThat(trade.getPrice()).isEqualTo("32.07562");
    assertThat(trade.getCost()).isEqualTo("16.03781");
    assertThat(trade.getFee()).isEqualTo("0.03208");
    assertThat(trade.getMargin()).isEqualTo("0.00000");
    assertThat(trade.getVolume()).isEqualTo("0.50000000");
    assertThat(trade.getOrderTxId()).isEqualTo("ONRNOX-DVI4W-76DL6Q-1");
    assertThat(trade.getUnixTimestamp()).isEqualTo(1389071942.2089);
    assertThat(trade.getType()).isEqualTo(KrakenType.SELL);
    assertThat(trade.getOrderType()).isEqualTo(KrakenOrderType.MARKET);
    assertThat(trade.getMiscellaneous()).isEqualTo("");
  }

  @Test
  public void testCancelOrderUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenTradeJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/kraken/dto/trading/example-cancelorder-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenCancelOrderResult krakenResult = mapper.readValue(is, KrakenCancelOrderResult.class);
    KrakenCancelOrderResponse cancelOrderResponse = krakenResult.getResult();

    assertThat(cancelOrderResponse.getCount()).isEqualTo(1);
    assertFalse(cancelOrderResponse.isPending());
  }

  @Test
  public void testAddOrderResponseUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenTradeJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/kraken/dto/trading/example-addorder-response-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenOrderResult krakenResult = mapper.readValue(is, KrakenOrderResult.class);
    KrakenOrderResponse orderResponse = krakenResult.getResult();

    assertThat(orderResponse.getDescription().getOrderDescription())
        .isEqualTo("sell 0.01000000 XBTLTC @ limit 45.25000");
    assertThat(orderResponse.getTransactionIds().get(0)).isEqualTo("OWQJ5O-ZWYC7-5R7POQ");
  }
}
