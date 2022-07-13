package org.knowm.xchange.kraken;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.kraken.dto.trade.KrakenOrder;
import org.knowm.xchange.kraken.dto.trade.KrakenTrade;
import org.knowm.xchange.kraken.dto.trade.KrakenUserTrade;
import org.knowm.xchange.kraken.dto.trade.results.KrakenOpenOrdersResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenTradeHistoryResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenTradeHistoryResult.KrakenTradeHistory;

public class KrakenUtilsTest {

  @Test
  public void testFilterOpenOrdersByCurrencyPair() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/kraken/dto/trading/example-openorders-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenOpenOrdersResult krakenResult = mapper.readValue(is, KrakenOpenOrdersResult.class);

    Map<String, KrakenOrder> krakenOrders =
        KrakenUtils.filterOpenOrdersByCurrencyPair(
            krakenResult.getResult().getOrders(), CurrencyPair.BTC_EUR);

    OpenOrders orders = KrakenAdapters.adaptOpenOrders(krakenOrders);

    // Verify that the example data was unmarshalled correctly
    assertThat(orders.getOpenOrders()).hasSize(1);
    assertThat(orders.getOpenOrders().get(0).getId()).isEqualTo("OU5JPQ-OIDTK-QIGIGI");
    assertThat(orders.getOpenOrders().get(0).getLimitPrice()).isEqualTo("1000.000");
    assertThat(orders.getOpenOrders().get(0).getOriginalAmount()).isEqualTo("0.01000000");
    assertThat(orders.getOpenOrders().get(0).getCurrencyPair().base).isEqualTo(Currency.XBT);
    assertThat(orders.getOpenOrders().get(0).getCurrencyPair().counter).isEqualTo(Currency.EUR);
    assertThat(orders.getOpenOrders().get(0).getType()).isEqualTo(Order.OrderType.BID);
  }

  @Test
  public void testAdaptTradeHistoryByCurrencyPair()
      throws JsonParseException, JsonMappingException, IOException {
    Map<String, KrakenTrade> krakenTradeMap =
        loadUserTrades("/org/knowm/xchange/kraken/dto/trading/example-tradehistory-data.json");

    Map<String, KrakenTrade> filteredKrakenTradeMap =
        KrakenUtils.filterTradeHistoryByCurrencyPair(krakenTradeMap, CurrencyPair.BTC_USD);

    assertThat(filteredKrakenTradeMap.size()).isEqualTo(2);

    UserTrades userTrades = KrakenAdapters.adaptTradesHistory(filteredKrakenTradeMap);

    UserTrade trade0 = userTrades.getUserTrades().get(0);
    assertThat(trade0).isInstanceOf(KrakenUserTrade.class);
    assertThat(trade0.getId()).isEqualTo("TY5BYV-WJUQF-XPYEYD-2");
    assertThat(trade0.getPrice()).isEqualTo("32.07562");
    assertThat(trade0.getOriginalAmount()).isEqualTo("0.50000000");
    assertThat(trade0.getCurrencyPair().base).isEqualTo(Currency.BTC);
    assertThat(trade0.getCurrencyPair().counter).isEqualTo(Currency.USD);
    assertThat(trade0.getType()).isEqualTo(OrderType.ASK);
    assertThat(trade0.getFeeAmount()).isEqualTo("0.03208");
    assertThat(trade0.getFeeCurrency()).isEqualTo(Currency.USD);
    assertThat(((KrakenUserTrade) trade0).getCost()).isEqualTo("16.03781");

    UserTrade trade1 = userTrades.getUserTrades().get(1);
    assertThat(trade1).isInstanceOf(KrakenUserTrade.class);
    assertThat(trade1.getId()).isEqualTo("TY5BYV-WJUQF-XPYEYD-3");
    assertThat(trade1.getPrice()).isEqualTo("32.07562");
    assertThat(trade1.getOriginalAmount()).isEqualTo("0.50000000");
    assertThat(trade1.getCurrencyPair().base).isEqualTo(Currency.BTC);
    assertThat(trade1.getCurrencyPair().counter).isEqualTo(Currency.USD);
    assertThat(trade1.getType()).isEqualTo(OrderType.ASK);
    assertThat(trade1.getFeeAmount()).isEqualTo("0.03208");
    assertThat(trade1.getFeeCurrency()).isEqualTo(Currency.USD);
    assertThat(((KrakenUserTrade) trade1).getCost()).isEqualTo("16.03781");
  }

  private static Map<String, KrakenTrade> loadUserTrades(String resourceName) throws IOException {
    // Read in the JSON from the example resources
    InputStream is = KrakenAdaptersTest.class.getResourceAsStream(resourceName);

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenTradeHistoryResult krakenResult = mapper.readValue(is, KrakenTradeHistoryResult.class);
    KrakenTradeHistory krakenTradeHistory = krakenResult.getResult();
    Map<String, KrakenTrade> krakenTradeHistoryMap = krakenTradeHistory.getTrades();

    return krakenTradeHistoryMap;
  }
}
