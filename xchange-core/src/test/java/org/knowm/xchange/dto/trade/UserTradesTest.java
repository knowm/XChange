package org.knowm.xchange.dto.trade;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;

public class UserTradesTest {

  @Test
  public void userTradesJsonMarchallTest() throws JsonProcessingException {

    List<UserTrade> userTradeList = new ArrayList<>();
    userTradeList.add(
        UserTrade.builder()
            .timestamp(Date.from(Instant.now()))
            .currencyPair(CurrencyPair.BTC_USD)
            .price(BigDecimal.ONE)
            .originalAmount(BigDecimal.ONE)
            .build());
    userTradeList.add(
        UserTrade.builder()
            .timestamp(Date.from(Instant.now()))
            .currencyPair(CurrencyPair.BTC_USD)
            .id("id")
            .price(BigDecimal.ONE)
            .originalAmount(BigDecimal.ONE)
            .build());
    userTradeList.add(
        UserTrade.builder()
            .timestamp(Date.from(Instant.now()))
            .currencyPair(CurrencyPair.BTC_USD)
            .id("id")
            .instrument(CurrencyPair.BTC_USD)
            .price(BigDecimal.ONE)
            .originalAmount(BigDecimal.ONE)
            .build());

    UserTrades userTrades = new UserTrades(userTradeList, Trades.TradeSortType.SortByTimestamp);

    String json = new ObjectMapper().writeValueAsString(userTrades);

    UserTrades result = new ObjectMapper().readValue(json, UserTrades.class);

    assertThat(result).isInstanceOf(UserTrades.class);
    assertThat(result.getUserTrades().size()).isEqualTo(userTradeList.size());
    assertThat(result.getUserTrades().get(0)).isInstanceOf(UserTrade.class);
    System.out.println(result);
  }
}
