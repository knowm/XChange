package org.knowm.xchange.simulated;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.knowm.xchange.currency.Currency.BTC;
import static org.knowm.xchange.currency.Currency.USD;
import static org.knowm.xchange.currency.CurrencyPair.BTC_USD;
import static org.knowm.xchange.dto.Order.OrderStatus.FILLED;
import static org.knowm.xchange.dto.Order.OrderStatus.NEW;
import static org.knowm.xchange.dto.Order.OrderStatus.PARTIALLY_FILLED;
import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;
import static org.knowm.xchange.simulated.FillMatcher.fillMatcher;
import static org.knowm.xchange.simulated.UserTradeMatcher.userTradeMatcher;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

import java.math.BigDecimal;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TestMatchingEngine {

  private static final String MAKER = "MAKER";
  private static final String TAKER = "TAKER";
  @Mock private Consumer<Fill> onFill;
  @Mock private AccountFactory accountFactory;
  @Mock private Account account;
  private MatchingEngine matchingEngine;

  @Captor private ArgumentCaptor<Fill> fillCaptor1;
  @Captor private ArgumentCaptor<Fill> fillCaptor2;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    Mockito.when(accountFactory.get(Mockito.anyString())).thenReturn(account);
    matchingEngine = new MatchingEngine(accountFactory, BTC_USD, 2, new BigDecimal("0.001"), onFill);
  }

  @Test
  public void testValidationOK() {
    matchingEngine.postOrder(
        TAKER,
        new LimitOrder.Builder(ASK, BTC_USD)
            .limitPrice(new BigDecimal("100.01"))
            .originalAmount(new BigDecimal("0.001"))
            .build());
  }

  @Test(expected = ExchangeException.class)
  public void testValidationNoPriceViolation() {
    matchingEngine.postOrder(
        TAKER,
        new LimitOrder.Builder(ASK, BTC_USD)
            .originalAmount(new BigDecimal("0.000999999"))
            .build());
  }

  @Test(expected = ExchangeException.class)
  public void testValidationMinimumAmountViolation() {
    matchingEngine.postOrder(
        TAKER,
        new LimitOrder.Builder(ASK, BTC_USD)
            .limitPrice(new BigDecimal("100.01"))
            .originalAmount(new BigDecimal("0.000999999"))
            .build());
  }

  @Test(expected = ExchangeException.class)
  public void testValidationPriceScaleViolation() {
    matchingEngine.postOrder(
        TAKER,
        new LimitOrder.Builder(ASK, BTC_USD)
            .limitPrice(new BigDecimal("100.011"))
            .originalAmount(new BigDecimal("0.001"))
            .build());
  }

  @Test(expected = ExchangeException.class)
  public void testValidationZeroPriceViolation() {
    matchingEngine.postOrder(
        TAKER,
        new LimitOrder.Builder(ASK, BTC_USD)
            .limitPrice(new BigDecimal(0))
            .originalAmount(new BigDecimal("0.001"))
            .build());
  }

  @Test(expected = ExchangeException.class)
  public void testValidationNegativePriceViolation() {
    matchingEngine.postOrder(
        TAKER,
        new LimitOrder.Builder(ASK, BTC_USD)
            .limitPrice(new BigDecimal("-0.0001"))
            .originalAmount(new BigDecimal("0.001"))
            .build());
  }

  @Test
  public void testAskNoMatch() {

    // Given an empty order book

    // When
    LimitOrder result =
        matchingEngine.postOrder(
            TAKER,
            new LimitOrder.Builder(ASK, BTC_USD)
                .limitPrice(new BigDecimal(100))
                .originalAmount(new BigDecimal(5))
                .build());

    // Then
    assertThat(result.getId(), notNullValue());
    assertThat(result.getStatus(), equalTo(NEW));
    verifyZeroInteractions(onFill);
    verify(account, never()).fill(any(UserTrade.class), any(Boolean.class));
    verify(account, times(1)).reserve(any(LimitOrder.class));
    verify(account, never()).release(any(LimitOrder.class));
    assertThat(matchingEngine.book().getAsks(), contains(result));
    assertThat(matchingEngine.book().getBids(), empty());
  }

  @Test
  public void testBidNoMatch() {
    // Given an empty order book

    // When
    LimitOrder result =
        matchingEngine.postOrder(
            TAKER,
            new LimitOrder.Builder(BID, BTC_USD)
                .limitPrice(new BigDecimal(100))
                .originalAmount(new BigDecimal(5))
                .build());

    // Then
    assertThat(result.getId(), notNullValue());
    assertThat(result.getStatus(), equalTo(NEW));
    verifyZeroInteractions(onFill);
    verify(account, never()).fill(any(UserTrade.class), any(Boolean.class));
    verify(account, times(1)).reserve(any(LimitOrder.class));
    verify(account, never()).release(any(LimitOrder.class));

    Level3OrderBook book = matchingEngine.book();
    assertThat(book.getBids(), contains(result));
    assertThat(book.getAsks(), empty());
  }

  @Test(expected = ExchangeException.class)
  public void testMarketAskEmptyBook() {
    matchingEngine.postOrder(
        TAKER, new MarketOrder.Builder(ASK, BTC_USD).originalAmount(new BigDecimal(5)).build());
  }

  @Test(expected = ExchangeException.class)
  public void testMarketBidEmptyBook() {
    matchingEngine.postOrder(
        TAKER, new MarketOrder.Builder(BID, BTC_USD).originalAmount(new BigDecimal(5)).build());
  }

  @Test
  public void testSimpleAskMatch() {

    // Given
    LimitOrder maker =
        matchingEngine.postOrder(
            MAKER,
            new LimitOrder.Builder(BID, BTC_USD)
                .limitPrice(new BigDecimal(100))
                .originalAmount(new BigDecimal(5))
                .build());

    verify(account, never()).fill(any(UserTrade.class), any(Boolean.class));
    verify(account, times(1)).reserve(any(LimitOrder.class));
    verify(account, never()).release(any(LimitOrder.class));
    reset(account);

    // When
    LimitOrder taker =
        matchingEngine.postOrder(
            TAKER,
            new LimitOrder.Builder(ASK, BTC_USD)
                .limitPrice(new BigDecimal(100))
                .originalAmount(new BigDecimal(5))
                .build());

    verify(account, times(2)).fill(any(UserTrade.class), any(Boolean.class));
    verify(account, never()).reserve(any(LimitOrder.class));
    verify(account, never()).release(any(LimitOrder.class));

    // Then
    assertThat(taker.getStatus(), equalTo(FILLED));

    verify(onFill)
        .accept(
            argThat(
                fillMatcher()
                    .whereApiKey(equalTo(TAKER))
                    .whereTaker(equalTo(true))
                    .whereTrade(
                        userTradeMatcher()
                            .whereOrderId(equalTo(taker.getId()))
                            .whereId(notNullValue(String.class))
                            .whereFeeAmount(equalTo(new BigDecimal("0.500")))
                            .whereFeeCurrency(equalTo(USD))
                            .whereOriginalAmount(equalTo(new BigDecimal(5)))
                            .wherePrice(equalTo(new BigDecimal(100)))
                            .whereType(equalTo(ASK)))));

    verify(onFill)
        .accept(
            argThat(
                fillMatcher()
                    .whereApiKey(equalTo(MAKER))
                    .whereTaker(equalTo(false))
                    .whereTrade(
                        userTradeMatcher()
                            .whereOrderId(equalTo(maker.getId()))
                            .whereId(notNullValue(String.class))
                            .whereFeeAmount(equalTo(new BigDecimal("0.005")))
                            .whereFeeCurrency(equalTo(BTC))
                            .whereOriginalAmount(equalTo(new BigDecimal(5)))
                            .wherePrice(equalTo(new BigDecimal(100)))
                            .whereType(equalTo(BID)))));

    verifyNoMoreInteractions(onFill);

    Level3OrderBook book = matchingEngine.book();
    assertThat(book.getBids(), empty());
    assertThat(book.getAsks(), empty());
  }

  @Test
  public void testSimpleBidMatch() {

    // Given
    LimitOrder maker =
        matchingEngine.postOrder(
            MAKER,
            new LimitOrder.Builder(ASK, BTC_USD)
                .limitPrice(new BigDecimal(100))
                .originalAmount(new BigDecimal(5))
                .build());

    verify(account, never()).fill(any(UserTrade.class), any(Boolean.class));
    verify(account, times(1)).reserve(any(LimitOrder.class));
    verify(account, never()).release(any(LimitOrder.class));
    reset(account);

    // When
    LimitOrder taker =
        matchingEngine.postOrder(
            TAKER,
            new LimitOrder.Builder(BID, BTC_USD)
                .limitPrice(new BigDecimal(100))
                .originalAmount(new BigDecimal(5))
                .build());

    verify(account, times(2)).fill(any(UserTrade.class), any(Boolean.class));
    verify(account, never()).reserve(any(LimitOrder.class));
    verify(account, never()).release(any(LimitOrder.class));

    // Then
    assertThat(taker.getStatus(), equalTo(FILLED));

    verify(onFill)
        .accept(
            argThat(
                fillMatcher()
                    .whereApiKey(equalTo(TAKER))
                    .whereTaker(equalTo(true))
                    .whereTrade(
                        userTradeMatcher()
                            .whereOrderId(equalTo(taker.getId()))
                            .whereId(notNullValue(String.class))
                            .whereFeeAmount(equalTo(new BigDecimal("0.005")))
                            .whereFeeCurrency(equalTo(BTC))
                            .whereOriginalAmount(equalTo(new BigDecimal(5)))
                            .wherePrice(equalTo(new BigDecimal(100)))
                            .whereType(equalTo(BID)))));

    verify(onFill)
        .accept(
            argThat(
                fillMatcher()
                    .whereApiKey(equalTo(MAKER))
                    .whereTaker(equalTo(false))
                    .whereTrade(
                        userTradeMatcher()
                            .whereOrderId(equalTo(maker.getId()))
                            .whereId(notNullValue(String.class))
                            .whereFeeAmount(equalTo(new BigDecimal("0.500")))
                            .whereFeeCurrency(equalTo(USD))
                            .whereOriginalAmount(equalTo(new BigDecimal(5)))
                            .wherePrice(equalTo(new BigDecimal(100)))
                            .whereType(equalTo(ASK)))));

    verifyNoMoreInteractions(onFill);

    Level3OrderBook book = matchingEngine.book();
    assertThat(book.getBids(), empty());
    assertThat(book.getAsks(), empty());
  }

  @Test
  public void testSimpleAskPartial() {

    // Given
    LimitOrder maker =
        matchingEngine.postOrder(
            MAKER,
            new LimitOrder.Builder(BID, BTC_USD)
                .limitPrice(new BigDecimal(100))
                .originalAmount(new BigDecimal(5))
                .build());

    // When
    LimitOrder taker =
        matchingEngine.postOrder(
            TAKER,
            new LimitOrder.Builder(ASK, BTC_USD)
                .limitPrice(new BigDecimal(100))
                .originalAmount(new BigDecimal(7))
                .build());

    // Then
    assertThat(taker.getStatus(), equalTo(PARTIALLY_FILLED));

    verify(onFill).accept(argThat(useAmount(TAKER, taker, new BigDecimal(5))));
    verify(onFill).accept(argThat(useAmount(MAKER, maker, new BigDecimal(5))));
    verifyNoMoreInteractions(onFill);

    Level3OrderBook book = matchingEngine.book();
    assertThat(book.getBids(), empty());
    assertThat(book.getAsks(), hasSize(1));
    assertThat(book.getAsks().get(0).getCumulativeAmount(), equalTo(new BigDecimal(5)));
  }

  @Test
  public void testSimpleBidPartial() {

    // Given
    LimitOrder maker =
        matchingEngine.postOrder(
            MAKER,
            new LimitOrder.Builder(ASK, BTC_USD)
                .limitPrice(new BigDecimal(100))
                .originalAmount(new BigDecimal(5))
                .build());

    // When
    LimitOrder taker =
        matchingEngine.postOrder(
            TAKER,
            new LimitOrder.Builder(BID, BTC_USD)
                .limitPrice(new BigDecimal(100))
                .originalAmount(new BigDecimal(7))
                .build());

    // Then
    assertThat(taker.getStatus(), equalTo(PARTIALLY_FILLED));

    verify(onFill).accept(argThat(useAmount(TAKER, taker, new BigDecimal(5))));
    verify(onFill).accept(argThat(useAmount(MAKER, maker, new BigDecimal(5))));
    verifyNoMoreInteractions(onFill);

    Level3OrderBook book = matchingEngine.book();
    assertThat(book.getBids(), hasSize(1));
    assertThat(book.getAsks(), empty());
    assertThat(book.getBids().get(0).getCumulativeAmount(), equalTo(new BigDecimal(5)));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testAskMultiple() {

    // Given
    LimitOrder maker1 =
        matchingEngine.postOrder(
            MAKER,
            new LimitOrder.Builder(BID, BTC_USD)
                .limitPrice(new BigDecimal(102))
                .originalAmount(new BigDecimal(1))
                .build());
    LimitOrder maker2 =
        matchingEngine.postOrder(
            MAKER,
            new LimitOrder.Builder(BID, BTC_USD)
                .limitPrice(new BigDecimal(100))
                .originalAmount(new BigDecimal(2))
                .build());
    LimitOrder maker3 =
        matchingEngine.postOrder(
            MAKER,
            new LimitOrder.Builder(BID, BTC_USD)
                .limitPrice(new BigDecimal(100))
                .originalAmount(new BigDecimal(4))
                .build());
    LimitOrder maker4 =
        matchingEngine.postOrder(
            MAKER,
            new LimitOrder.Builder(BID, BTC_USD)
                .limitPrice(new BigDecimal(101))
                .originalAmount(new BigDecimal(8))
                .build());

    // When
    LimitOrder taker1 =
        matchingEngine.postOrder(
            TAKER,
            new LimitOrder.Builder(ASK, BTC_USD)
                .limitPrice(new BigDecimal(100))
                .originalAmount(new BigDecimal(10))
                .build());

    // Then
    assertThat(taker1.getStatus(), equalTo(FILLED));

    verify(onFill, atLeastOnce()).accept(fillCaptor1.capture());
    assertThat(
        fillCaptor1.getAllValues(),
        contains(
            useAmount(TAKER, taker1, maker1.getOriginalAmount(), maker1.getLimitPrice()),
            useAmount(MAKER, maker1, maker1.getOriginalAmount()),
            useAmount(TAKER, taker1, maker4.getOriginalAmount(), maker4.getLimitPrice()),
            useAmount(MAKER, maker4, maker4.getOriginalAmount()),
            useAmount(TAKER, taker1, new BigDecimal(1), maker2.getLimitPrice()),
            useAmount(MAKER, maker2, new BigDecimal(1))));

    verifyNoMoreInteractions(onFill);
    reset(onFill);

    // When
    LimitOrder taker2 =
        matchingEngine.postOrder(
            TAKER,
            new LimitOrder.Builder(ASK, BTC_USD)
                .limitPrice(new BigDecimal(100))
                .originalAmount(new BigDecimal(5))
                .build());

    // Then
    assertThat(taker2.getStatus(), equalTo(FILLED));

    verify(onFill, atLeastOnce()).accept(fillCaptor2.capture());
    assertThat(
        fillCaptor2.getAllValues(),
        contains(
            useAmount(TAKER, taker2, new BigDecimal(1), maker2.getLimitPrice()),
            useAmount(MAKER, maker2, new BigDecimal(1)),
            useAmount(TAKER, taker2, new BigDecimal(4), maker3.getLimitPrice()),
            useAmount(MAKER, maker3, new BigDecimal(4))));

    Level3OrderBook book = matchingEngine.book();
    assertThat(book.getBids(), empty());
    assertThat(book.getAsks(), empty());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testBidMultiple() {

    // Given
    LimitOrder maker1 =
        matchingEngine.postOrder(
            MAKER,
            new LimitOrder.Builder(ASK, BTC_USD)
                .limitPrice(new BigDecimal(100))
                .originalAmount(new BigDecimal(1))
                .build());
    LimitOrder maker2 =
        matchingEngine.postOrder(
            MAKER,
            new LimitOrder.Builder(ASK, BTC_USD)
                .limitPrice(new BigDecimal(102))
                .originalAmount(new BigDecimal(2))
                .build());
    LimitOrder maker3 =
        matchingEngine.postOrder(
            MAKER,
            new LimitOrder.Builder(ASK, BTC_USD)
                .limitPrice(new BigDecimal(102))
                .originalAmount(new BigDecimal(4))
                .build());
    LimitOrder maker4 =
        matchingEngine.postOrder(
            MAKER,
            new LimitOrder.Builder(ASK, BTC_USD)
                .limitPrice(new BigDecimal(101))
                .originalAmount(new BigDecimal(8))
                .build());

    // When
    LimitOrder taker1 =
        matchingEngine.postOrder(
            TAKER,
            new LimitOrder.Builder(BID, BTC_USD)
                .limitPrice(new BigDecimal(102))
                .originalAmount(new BigDecimal(10))
                .build());

    // Then
    assertThat(taker1.getStatus(), equalTo(FILLED));

    verify(onFill, atLeastOnce()).accept(fillCaptor1.capture());
    assertThat(
        fillCaptor1.getAllValues(),
        contains(
            useAmount(TAKER, taker1, maker1.getOriginalAmount(), maker1.getLimitPrice()),
            useAmount(MAKER, maker1, maker1.getOriginalAmount()),
            useAmount(TAKER, taker1, maker4.getOriginalAmount(), maker4.getLimitPrice()),
            useAmount(MAKER, maker4, maker4.getOriginalAmount()),
            useAmount(TAKER, taker1, new BigDecimal(1), maker2.getLimitPrice()),
            useAmount(MAKER, maker2, new BigDecimal(1))));

    verifyNoMoreInteractions(onFill);
    reset(onFill);

    // When
    LimitOrder taker2 =
        matchingEngine.postOrder(
            TAKER,
            new LimitOrder.Builder(BID, BTC_USD)
                .limitPrice(new BigDecimal(102))
                .originalAmount(new BigDecimal(5))
                .build());

    // Then
    assertThat(taker2.getStatus(), equalTo(FILLED));

    verify(onFill, atLeastOnce()).accept(fillCaptor2.capture());
    assertThat(
        fillCaptor2.getAllValues(),
        contains(
            useAmount(TAKER, taker2, new BigDecimal(1), maker2.getLimitPrice()),
            useAmount(MAKER, maker2, new BigDecimal(1)),
            useAmount(TAKER, taker2, new BigDecimal(4), maker3.getLimitPrice()),
            useAmount(MAKER, maker3, new BigDecimal(4))));

    Level3OrderBook book = matchingEngine.book();
    assertThat(book.getBids(), empty());
    assertThat(book.getAsks(), empty());
  }

  private FillMatcher useAmount(String apiKey, LimitOrder order, BigDecimal amount) {
    return useAmount(apiKey, order, amount, order.getLimitPrice());
  }

  private FillMatcher useAmount(
      String apiKey, LimitOrder order, BigDecimal amount, BigDecimal price) {
    return fillMatcher()
        .whereApiKey(equalTo(apiKey))
        .whereTrade(
            userTradeMatcher()
                .whereOrderId(equalTo(order.getId()))
                .whereId(notNullValue(String.class))
                .whereOriginalAmount(equalTo(amount))
                .wherePrice(equalTo(price))
                .whereType(equalTo(order.getType())));
  }
}
