package org.knowm.xchange.jubi.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.jubi.JubiAdapters;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Dzf on 2017/7/16.
 */
public class JubiOrderJsonTest {

  @Test
  public void testSuccessToFetchJubiOrderHistoryAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is = JubiOrderJsonTest.class.getResourceAsStream("/example-trade-list.json");
    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JubiOrderHistory jubiOrderHistory = mapper.readValue(is, JubiOrderHistory.class);
    assertThat(jubiOrderHistory.getResult().isSuccess()).isTrue();
    assertThat(jubiOrderHistory.getOrderList().length).isEqualTo(8);
    assertThat(jubiOrderHistory.getOrderList()[0].getId()).isEqualTo(new BigDecimal(6860502));
    assertThat(jubiOrderHistory.getOrderList()[0].getType()).isEqualTo(JubiOrderType.Buy);
    assertThat(jubiOrderHistory.getOrderList()[0].getAmountOriginal()).isEqualTo(new BigDecimal(2323));
    assertThat(jubiOrderHistory.getOrderList()[0].getAmountOutstanding()).isEqualTo(new BigDecimal(223));
    assertThat(jubiOrderHistory.getOrderList()[1].getType()).isEqualTo(JubiOrderType.Sell);
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    String dateString = f.format(jubiOrderHistory.getOrderList()[0].getDatetime());
    assertThat(dateString).isEqualTo("2017-07-16 15:48:10");
    UserTrades userTrades = JubiAdapters.adaptUserTrades(jubiOrderHistory, new CurrencyPair("doge", "cny"));
    System.out.println(userTrades);
    assertThat(userTrades.getlastID()).isEqualTo(6860502);
    assertThat(userTrades.getTrades().get(0).getId()).isEqualTo("1071957");
    assertThat(userTrades.getTrades().get(0).getType()).isEqualTo(Order.OrderType.BID);
    assertThat(userTrades.getTrades().get(0).getTradableAmount()).isEqualTo(new BigDecimal(100000));
    assertThat(userTrades.getTrades().get(1).getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(userTrades.getTrades().get(7).getId()).isEqualTo("6860502");
  }

  @Test
  public void testFailToFetchJubiOrderHistoryAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is = JubiOrderJsonTest.class.getResourceAsStream("/example-error-response.json");
    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JubiOrderHistory jubiOrderHistory = mapper.readValue(is, JubiOrderHistory.class);
    assertThat(jubiOrderHistory.getResult().isSuccess()).isFalse();
    assertThat(jubiOrderHistory.getResult().getErrorCode()).isEqualTo(105);
    assertThat(jubiOrderHistory.getOrderList()).isNull();
  }

  @Test
  public void testSuccessToFetchJubiOrderStatusAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is = JubiOrderJsonTest.class.getResourceAsStream("/example-order-status.json");
    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JubiOrderStatus jubiOrderStatus = mapper.readValue(is, JubiOrderStatus.class);
    //Check jubiOrderStatus
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    assertThat(jubiOrderStatus.getResult().isSuccess()).isTrue();
    assertThat(jubiOrderStatus.getId()).isEqualTo(new BigDecimal(6730721));
    assertThat(jubiOrderStatus.getStatus()).isEqualTo(JubiStatusType.Cancelled);
    assertThat(jubiOrderStatus.getType()).isEqualTo(JubiOrderType.Buy);
    assertThat(jubiOrderStatus.getAmountOriginal()).isEqualTo(new BigDecimal(10000));
    f.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    String dateString = f.format(jubiOrderStatus.getDatetime());
    assertThat(dateString).isEqualTo("2017-07-09 18:23:42");
    System.out.println(jubiOrderStatus);
  }

  @Test
  public void testFailToFetchJubiOrderStatusAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is = JubiOrderJsonTest.class.getResourceAsStream("/example-error-response.json");
    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JubiOrderStatus jubiOrderStatus = mapper.readValue(is, JubiOrderStatus.class);
    //Check jubiOrderStatus
    assertThat(jubiOrderStatus.getResult().isSuccess()).isFalse();
    assertThat(jubiOrderStatus.getResult().getErrorCode()).isEqualTo(105);
  }

  @Test
  public void testJubiTradeResultSuccessAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is = JubiOrderJsonTest.class.getResourceAsStream("/example-place-limit-order.json");
    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JubiTradeResult jubiTradeResult = mapper.readValue(is, JubiTradeResult.class);
    //Check jubiOrderStatus
    assertThat(jubiTradeResult.isSuccess()).isTrue();
    assertThat(jubiTradeResult.getId()).isEqualTo(new BigDecimal(11));
  }

  @Test
  public void testJubiTradeResultFailAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is = JubiOrderJsonTest.class.getResourceAsStream("/example-error-response.json");
    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JubiTradeResult jubiTradeResult = mapper.readValue(is, JubiTradeResult.class);
    //Check jubiOrderStatus
    assertThat(jubiTradeResult.isSuccess()).isFalse();
    assertThat(jubiTradeResult.getErrorCode()).isEqualTo(105);
  }
}
