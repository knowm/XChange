package info.bitrich.xchangestream.bitstamp;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.observers.TestObserver;
import java.util.List;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BitstampStreamingMarketDataServiceBaseTest {

  protected ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  protected void validateTrades(Trade expected, TestObserver<Trade> test) {
    test.assertValue(
        trade1 -> {
          assertThat(trade1.getId()).as("Id").isEqualTo(expected.getId());
          assertThat(trade1.getCurrencyPair())
              .as("Currency pair")
              .isEqualTo(expected.getCurrencyPair());
          assertThat(trade1.getPrice()).as("Price").isEqualTo(expected.getPrice());
          // assertThat(trade1.getTimestamp()).as("Timestamp").isEqualTo(expected.getTimestamp());
          assertThat(trade1.getOriginalAmount())
              .as("Amount")
              .isEqualTo(expected.getOriginalAmount());
          assertThat(trade1.getType()).as("Type").isEqualTo(expected.getType());
          return true;
        });
  }

  protected void validateOrderBook(
      List<LimitOrder> bids, List<LimitOrder> asks, TestObserver<OrderBook> test) {
    test.assertValue(
        orderBook1 -> {
          assertThat(orderBook1.getAsks()).as("Asks").isEqualTo(asks);
          assertThat(orderBook1.getBids()).as("Bids").isEqualTo(bids);
          return true;
        });
  }
}
