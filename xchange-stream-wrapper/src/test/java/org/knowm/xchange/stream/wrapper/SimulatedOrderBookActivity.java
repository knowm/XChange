package org.knowm.xchange.stream.wrapper;

import static java.math.RoundingMode.HALF_UP;
import static org.knowm.xchange.currency.Currency.BTC;
import static org.knowm.xchange.currency.Currency.USD;
import static org.knowm.xchange.currency.CurrencyPair.BTC_USD;
import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;
import static org.knowm.xchange.simulated.SimulatedExchange.ACCOUNT_FACTORY_PARAM;
import static org.knowm.xchange.simulated.SimulatedExchange.ENGINE_FACTORY_PARAM;

import com.google.common.util.concurrent.AbstractExecutionThreadService;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.simulated.AccountFactory;
import org.knowm.xchange.simulated.MatchingEngineFactory;
import org.knowm.xchange.simulated.SimulatedExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SimulatedOrderBookActivity extends AbstractExecutionThreadService {

  private static final BigDecimal MIN_PRICE = new BigDecimal("0.01");
  private static final Logger LOGGER = LoggerFactory.getLogger(SimulatedOrderBookActivity.class);
  private static final BigDecimal THOUSAND = new BigDecimal(1000);
  private static final BigDecimal HUNDRED = new BigDecimal(100);

  private final SimulatedExchange marketMakerExchange;
  private final Random random;

  SimulatedOrderBookActivity(AccountFactory accountFactory, MatchingEngineFactory matchingEngineFactory) {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(SimulatedExchange.class);
    exchangeSpecification.setApiKey("MarketMakers");
    exchangeSpecification.setExchangeSpecificParametersItem(
        ENGINE_FACTORY_PARAM, matchingEngineFactory);
    exchangeSpecification.setExchangeSpecificParametersItem(ACCOUNT_FACTORY_PARAM, accountFactory);
    marketMakerExchange =
        (SimulatedExchange) ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    random = new Random();
  }

  @Override
  protected void run() {
    LOGGER.info("Starting market data simulator...");

    try {
      mockMarket();
      LOGGER.info("Prepared mock order book");

      while (isRunning() && !Thread.interrupted()) {

        // Wait a random amount of time, up to 2 seconds
        Thread.sleep(500L + random.nextInt(1499));

        // Randomly add a bit to the book and then execute a market order on the opposite side
        OrderType orderType = random.nextBoolean() ? ASK : BID;
        placeLimitOrder(orderType, randomPrice(orderType), randomAmount(2));
        placeMarketOrder(orderType.equals(ASK) ? BID : ASK, randomAmount(2));
      }
    } catch (Exception e) {
      LOGGER.error("Simulator failed.", e);
    }
  }

  private BigDecimal randomAmount(int max) {
    // 0.001 - max BTC
    return new BigDecimal(1 + random.nextInt(max * 1000 - 1)).divide(THOUSAND, 4, HALF_UP);
  }

  private BigDecimal randomPrice(OrderType orderType) throws IOException {
    // 0 - 50 USD from the current price
    Ticker ticker = marketMakerExchange.getMarketDataService().getTicker(BTC_USD);
    BigDecimal diff = new BigDecimal(random.nextInt(5000)).divide(HUNDRED, 2, HALF_UP);
    if (orderType == BID) {
      BigDecimal result = ticker.getBid().subtract(diff);
      return result.compareTo(MIN_PRICE) <= 0 ? MIN_PRICE : result;
    } else {
      return ticker.getAsk().add(diff);
    }
  }

  private void mockMarket() {
    marketMakerExchange.getAccountService().deposit(USD, new BigDecimal("99999999999999999"));
    marketMakerExchange.getAccountService().deposit(BTC, new BigDecimal("99999999999999999"));
    BigDecimal startPrice = new BigDecimal(5000);
    BigDecimal startAmount = new BigDecimal("2");
    BigDecimal range = new BigDecimal(4000);
    BigDecimal seedIncrement = new BigDecimal("0.1");
    BigDecimal multiplicator = new BigDecimal("1.3");

    BigDecimal diff = seedIncrement;
    BigDecimal amount = startAmount;
    while (diff.compareTo(range) < 0) {
      LOGGER.debug("Building order book, price diff {}", diff);
      marketMakerOrder(
          ASK, startPrice.add(diff).setScale(2, HALF_UP), amount.add(randomAmount(10)));
      marketMakerOrder(
          BID, startPrice.subtract(diff).setScale(2, HALF_UP), amount.add(randomAmount(10)));
      diff = diff.multiply(multiplicator).setScale(2, HALF_UP);
      amount = amount.divide(multiplicator, 2, HALF_UP);
    }
  }

  private void placeLimitOrder(OrderType orderType, BigDecimal price, BigDecimal amount)
      throws IOException {
    LOGGER.debug("Simulated limit order: {} {} @ {}", orderType, amount, price);
    marketMakerExchange
        .getTradeService()
        .placeLimitOrder(
            new LimitOrder.Builder(orderType, BTC_USD)
                .limitPrice(price)
                .originalAmount(amount)
                .build());
  }

  private void placeMarketOrder(OrderType orderType, BigDecimal amount) throws IOException {
    LOGGER.debug("Simulated market order: {} {}", orderType, amount);
    marketMakerExchange
        .getTradeService()
        .placeMarketOrder(
            new MarketOrder.Builder(orderType, BTC_USD).originalAmount(amount).build());
  }

  private void marketMakerOrder(OrderType orderType, BigDecimal price, BigDecimal amount) {
    marketMakerExchange
        .getTradeService()
        .placeLimitOrderUnrestricted(
            new LimitOrder.Builder(orderType, BTC_USD)
                .limitPrice(price)
                .originalAmount(amount)
                .build());
  }
}
