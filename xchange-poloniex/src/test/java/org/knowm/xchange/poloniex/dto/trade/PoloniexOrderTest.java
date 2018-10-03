package org.knowm.xchange.poloniex.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.poloniex.PoloniexAuthenticated;
import org.knowm.xchange.poloniex.dto.PoloniexException;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;
import si.mazi.rescu.InvocationResult;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestMethodMetadata;
import si.mazi.rescu.SynchronizedValueFactory;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonResponseReader;

public class PoloniexOrderTest {

  @Test
  public void orderEntryResponseTest() throws IOException {
    final InputStream is =
        getClass().getResourceAsStream("/org/knowm/xchange/poloniex/dto/trade/order-entry.json");
    final ObjectMapper mapper = new ObjectMapper();
    final PoloniexTradeResponse response = mapper.readValue(is, PoloniexTradeResponse.class);

    assertThat(response.getOrderNumber()).isEqualTo(21343142352L);
    assertThat(response.getPoloniexPublicTrades().size()).isEqualTo(1);

    PoloniexPublicTrade trade = response.getPoloniexPublicTrades().get(0);
    assertThat(trade.getAmount()).isEqualTo("0.10000000");
    assertThat(trade.getDate()).isEqualTo("2016-08-19 12:06:35");
    assertThat(trade.getRate()).isEqualTo("0.00623991");
    assertThat(trade.getTotal()).isEqualTo("0.00062399");
    assertThat(trade.getTradeID()).isEqualTo("1623424");
    assertThat(trade.getType()).isEqualTo("buy");
  }

  @Test
  public void responseImmediateOrCancelTest() throws IOException {
    final InputStream is =
        getClass()
            .getResourceAsStream("/org/knowm/xchange/poloniex/dto/trade/order-entry-ioc.json");
    final ObjectMapper mapper = new ObjectMapper();
    final PoloniexTradeResponse response = mapper.readValue(is, PoloniexTradeResponse.class);

    assertThat(response.getOrderNumber()).isEqualTo(213743244249L);
    assertThat(response.getPoloniexPublicTrades().size()).isEqualTo(0);
    assertThat(response.getAmountUnfilled()).isEqualTo("1.00000000");
  }

  @Test(expected = PoloniexException.class)
  public void buyRejectTest() throws Exception {

    InvocationResult invocationResult =
        new InvocationResult("{\"error\":\"Not enough LTC.\"}", 200);

    Method apiMethod =
        PoloniexAuthenticated.class.getDeclaredMethod(
            "buy",
            String.class,
            ParamsDigest.class,
            SynchronizedValueFactory.class,
            String.class,
            String.class,
            String.class,
            Integer.class,
            Integer.class,
            Integer.class);
    RestMethodMetadata data = RestMethodMetadata.create(apiMethod, "", "");

    try {
      new JacksonResponseReader(new DefaultJacksonObjectMapperFactory().createObjectMapper(), false)
          .read(invocationResult, data);
    } catch (PoloniexException e) {
      Assert.assertTrue(e.getMessage().startsWith("Not enough LTC."));
      throw e;
    }
  }

  @Test
  public void moveOrderTest() throws IOException {
    final InputStream is =
        getClass().getResourceAsStream("/org/knowm/xchange/poloniex/dto/trade/order-move.json");
    final ObjectMapper mapper = new ObjectMapper();
    final PoloniexMoveResponse response = mapper.readValue(is, PoloniexMoveResponse.class);

    assertThat(response.getOrderNumber()).isEqualTo(214232442242L);
    assertThat(response.getPoloniexPublicTrades().size()).isEqualTo(1);

    PoloniexPublicTrade trade = response.getPoloniexPublicTrades().get("BTC_LTC").get(0);
    assertThat(trade.getAmount()).isEqualTo("0.10000000");
    assertThat(trade.getDate()).isEqualTo("2016-08-21 13:08:40");
    assertThat(trade.getRate()).isEqualTo("0.00623069");
    assertThat(trade.getTotal()).isEqualTo("0.00062306");
    assertThat(trade.getTradeID()).isEqualTo("1623434");
    assertThat(trade.getType()).isEqualTo("buy");
  }

  @Test(expected = PoloniexException.class)
  public void sellRejectTest() throws Exception {

    InvocationResult invocationResult =
        new InvocationResult("{\"error\":\"Not enough LTC.\"}", 200);

    Method apiMethod =
        PoloniexAuthenticated.class.getDeclaredMethod(
            "sell",
            String.class,
            ParamsDigest.class,
            SynchronizedValueFactory.class,
            String.class,
            String.class,
            String.class,
            Integer.class,
            Integer.class,
            Integer.class);
    RestMethodMetadata data = RestMethodMetadata.create(apiMethod, "", "");

    try {
      new JacksonResponseReader(new DefaultJacksonObjectMapperFactory().createObjectMapper(), false)
          .read(invocationResult, data);
    } catch (PoloniexException e) {
      Assert.assertTrue(e.getMessage().startsWith("Not enough LTC."));
      throw e;
    }
  }

  @Test(expected = PoloniexException.class)
  public void moveOrderRejectTest() throws Exception {

    InvocationResult invocationResult =
        new InvocationResult("{\"success\":0,\"error\":\"Not enough LTC.\"}", 200);

    Method apiMethod =
        PoloniexAuthenticated.class.getDeclaredMethod(
            "moveOrder",
            String.class,
            ParamsDigest.class,
            SynchronizedValueFactory.class,
            String.class,
            String.class,
            String.class,
            Integer.class,
            Integer.class);
    RestMethodMetadata data = RestMethodMetadata.create(apiMethod, "", "");

    try {
      new JacksonResponseReader(new DefaultJacksonObjectMapperFactory().createObjectMapper(), false)
          .read(invocationResult, data);
    } catch (PoloniexException e) {
      Assert.assertTrue(e.getMessage().startsWith("Not enough LTC."));
      throw e;
    }
  }
}
