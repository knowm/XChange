package org.knowm.xchange.abucoins.service.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.abucoins.AbucoinsExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamNextPageCursor;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

/**
 * Not structured as a unit test because it requires API Key information to run properly.
 *
 * @author bryant_harris
 */
public class OrdersFetchIntegration {
  public static final String ABUCOINS_PASSPHRASE = " -- replace with your passphrase --";
  public static final String ABUCOINS_KEY = " -- replace with your key --";
  public static final String ABUCOINS_SECRET = " -- replace with your secret --";

  public static void main(String[] args) throws Exception {
    OrdersFetchIntegration test = new OrdersFetchIntegration();
    // test.activeFetchTest();
    test.historyFetchTest();
  }

  public void activeFetchTest() throws Exception {
    Exchange exchange = createExchange();
    TradeService tradeService = exchange.getTradeService();

    OpenOrders openOrders = tradeService.getOpenOrders();
    assertThat(openOrders).isNotNull();
    System.out.println(openOrders);

    String orderID =
        tradeService.placeLimitOrder(
            new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
                .limitPrice(new BigDecimal("500"))
                .originalAmount(new BigDecimal("0.75"))
                .build());

    Collection<Order> orders = tradeService.getOrder(orderID);
    for (Order order : orders) tradeService.cancelOrder(order.getId());
  }

  public void historyFetchTest() throws Exception {
    Exchange exchange = createExchange();
    TradeService tradeService = exchange.getTradeService();
    TradeHistoryParams params = tradeService.createTradeHistoryParams();
    ((TradeHistoryParamLimit) params).setLimit(1000);
    UserTrades trades;
    do {
      trades = tradeService.getTradeHistory(params);
      ((TradeHistoryParamNextPageCursor) params).setNextPageCursor(trades.getNextPageCursor());
      System.out.println(trades);
    } while (!trades.getUserTrades().isEmpty() && trades.getNextPageCursor() != null);
  }

  private Exchange createExchange() {
    ExchangeSpecification exSpec = new AbucoinsExchange().getDefaultExchangeSpecification();
    exSpec.setPassword(ABUCOINS_PASSPHRASE);
    exSpec.setApiKey(ABUCOINS_KEY);
    exSpec.setSecretKey(ABUCOINS_SECRET);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    return exchange;
  }
}
