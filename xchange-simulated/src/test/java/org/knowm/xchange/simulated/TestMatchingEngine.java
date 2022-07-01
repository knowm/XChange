package org.knowm.xchange.simulated;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.currency.Currency.BTC;
import static org.knowm.xchange.currency.Currency.USD;
import static org.knowm.xchange.currency.CurrencyPair.BTC_USD;
import static org.knowm.xchange.dto.Order.OrderStatus.FILLED;
import static org.knowm.xchange.dto.Order.OrderStatus.NEW;
import static org.knowm.xchange.dto.Order.OrderStatus.PARTIALLY_FILLED;
import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;
import static org.knowm.xchange.simulated.Assertions.assertThat;
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
import java.util.List;
import java.util.function.Consumer;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.dto.marketdata.OrderBook;
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
    matchingEngine =
        new MatchingEngine(accountFactory, BTC_USD, 2, new BigDecimal("0.001"), onFill);
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
        new LimitOrder.Builder(ASK, BTC_USD).originalAmount(new BigDecimal("0.000999999")).build());
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
    assertThat(result.getId()).isNotNull();
    assertThat(result.getStatus()).isEqualTo(NEW);
    verifyZeroInteractions(onFill);
    verify(account, never()).fill(any(UserTrade.class), any(Boolean.class));
    verify(account, times(1)).reserve(any(LimitOrder.class));
    verify(account, never()).release(any(LimitOrder.class));
    assertThat(matchingEngine.book().getAsks()).contains(result);
    assertThat(matchingEngine.book().getBids()).isEmpty();
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
    assertThat(result.getId()).isNotNull();
    assertThat(result.getStatus()).isEqualTo(NEW);
    verifyZeroInteractions(onFill);
    verify(account, never()).fill(any(UserTrade.class), any(Boolean.class));
    verify(account, times(1)).reserve(any(LimitOrder.class));
    verify(account, never()).release(any(LimitOrder.class));

    Level3OrderBook book = matchingEngine.book();
    assertThat(book.getBids()).contains(result);
    assertThat(book.getAsks()).isEmpty();
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
    assertThat(taker.getStatus()).isEqualTo(FILLED);

    verify(onFill)
        .accept(
            argThat(
                new AssertionMatcher<Fill>() {
                  @Override
                  public void assertion(Fill actual) throws AssertionError {
                    assertThat(actual).hasApiKey(TAKER).isTaker();
                    assertThat(actual.getTrade())
                        .hasOrderId(taker.getId())
                        .hasId()
                        .hasFeeAmount(new BigDecimal("0.500"))
                        .hasFeeCurrency(USD)
                        .hasOriginalAmount(new BigDecimal(5))
                        .hasPrice(new BigDecimal(100))
                        .hasType(ASK);
                  }
                }));

    verify(onFill)
        .accept(
            argThat(
                new AssertionMatcher<Fill>() {
                  @Override
                  public void assertion(Fill actual) throws AssertionError {
                    assertThat(actual).hasApiKey(MAKER).isNotTaker();
                    assertThat(actual.getTrade())
                        .hasOrderId(maker.getId())
                        .hasId()
                        .hasFeeAmount(new BigDecimal("0.005"))
                        .hasFeeCurrency(BTC)
                        .hasOriginalAmount(new BigDecimal(5))
                        .hasPrice(new BigDecimal(100))
                        .hasType(BID);
                  }
                }));

    verifyNoMoreInteractions(onFill);

    Level3OrderBook book = matchingEngine.book();
    assertThat(book.getBids()).isEmpty();
    assertThat(book.getAsks()).isEmpty();
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
    assertThat(taker.getStatus()).isEqualTo(FILLED);

    verify(onFill)
        .accept(
            argThat(
                new AssertionMatcher<Fill>() {
                  @Override
                  public void assertion(Fill actual) throws AssertionError {
                    assertThat(actual).hasApiKey(TAKER).isTaker();
                    assertThat(actual.getTrade())
                        .hasOrderId(taker.getId())
                        .hasId()
                        .hasFeeAmount(new BigDecimal("0.005"))
                        .hasFeeCurrency(BTC)
                        .hasOriginalAmount(new BigDecimal(5))
                        .hasPrice(new BigDecimal(100))
                        .hasType(BID);
                  }
                }));

    verify(onFill)
        .accept(
            argThat(
                new AssertionMatcher<Fill>() {
                  @Override
                  public void assertion(Fill actual) throws AssertionError {
                    assertThat(actual).hasApiKey(MAKER).isNotTaker();
                    assertThat(actual.getTrade())
                        .hasOrderId(maker.getId())
                        .hasId()
                        .hasFeeAmount(new BigDecimal("0.500"))
                        .hasFeeCurrency(USD)
                        .hasOriginalAmount(new BigDecimal(5))
                        .hasPrice(new BigDecimal(100))
                        .hasType(ASK);
                  }
                }));

    verifyNoMoreInteractions(onFill);

    Level3OrderBook book = matchingEngine.book();
    assertThat(book.getBids()).isEmpty();
    assertThat(book.getAsks()).isEmpty();
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
    assertThat(taker.getStatus()).isEqualTo(PARTIALLY_FILLED);

    verify(onFill).accept(argThat(useAmount(TAKER, taker, new BigDecimal(5))));
    verify(onFill).accept(argThat(useAmount(MAKER, maker, new BigDecimal(5))));
    verifyNoMoreInteractions(onFill);

    Level3OrderBook book = matchingEngine.book();
    assertThat(book.getBids()).isEmpty();
    assertThat(book.getAsks()).hasSize(1);
    assertThat(book.getAsks().get(0).getCumulativeAmount()).isEqualTo(new BigDecimal(5));
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
    assertThat(taker.getStatus()).isEqualTo(PARTIALLY_FILLED);

    verify(onFill).accept(argThat(useAmount(TAKER, taker, new BigDecimal(5))));
    verify(onFill).accept(argThat(useAmount(MAKER, maker, new BigDecimal(5))));
    verifyNoMoreInteractions(onFill);

    Level3OrderBook book = matchingEngine.book();
    assertThat(book.getBids()).hasSize(1);
    assertThat(book.getAsks()).isEmpty();
    assertThat(book.getBids().get(0).getCumulativeAmount()).isEqualTo(new BigDecimal(5));
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
    assertThat(taker1.getStatus()).isEqualTo(FILLED);

    verify(onFill, atLeastOnce()).accept(fillCaptor1.capture());
    assertThat(fillCaptor1.getAllValues()).hasSize(6);
    assertFill(
        fillCaptor1.getAllValues().get(0),
        TAKER,
        taker1,
        maker1.getOriginalAmount(),
        maker1.getLimitPrice());
    assertFill(
        fillCaptor1.getAllValues().get(1),
        MAKER,
        maker1,
        maker1.getOriginalAmount(),
        maker1.getLimitPrice());
    assertFill(
        fillCaptor1.getAllValues().get(2),
        TAKER,
        taker1,
        maker4.getOriginalAmount(),
        maker4.getLimitPrice());
    assertFill(
        fillCaptor1.getAllValues().get(3),
        MAKER,
        maker4,
        maker4.getOriginalAmount(),
        maker4.getLimitPrice());
    assertFill(
        fillCaptor1.getAllValues().get(4),
        TAKER,
        taker1,
        new BigDecimal(1),
        maker2.getLimitPrice());
    assertFill(
        fillCaptor1.getAllValues().get(5),
        MAKER,
        maker2,
        new BigDecimal(1),
        maker2.getLimitPrice());

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
    assertThat(taker2.getStatus()).isEqualTo(FILLED);

    verify(onFill, atLeastOnce()).accept(fillCaptor2.capture());
    assertThat(fillCaptor2.getAllValues()).hasSize(4);
    assertFill(
        fillCaptor2.getAllValues().get(0),
        TAKER,
        taker2,
        new BigDecimal(1),
        maker2.getLimitPrice());
    assertFill(
        fillCaptor2.getAllValues().get(1),
        MAKER,
        maker2,
        new BigDecimal(1),
        maker2.getLimitPrice());
    assertFill(
        fillCaptor2.getAllValues().get(2),
        TAKER,
        taker2,
        new BigDecimal(4),
        maker3.getLimitPrice());
    assertFill(
        fillCaptor2.getAllValues().get(3),
        MAKER,
        maker3,
        new BigDecimal(4),
        maker3.getLimitPrice());

    Level3OrderBook book = matchingEngine.book();
    assertThat(book.getBids()).isEmpty();
    assertThat(book.getAsks()).isEmpty();
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testGetLevel2OrderBook() {
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

    LimitOrder taker1 =
        matchingEngine.postOrder(
            TAKER,
            new LimitOrder.Builder(BID, BTC_USD)
                .limitPrice(new BigDecimal(99))
                .originalAmount(new BigDecimal(1))
                .build());
    LimitOrder taker2 =
        matchingEngine.postOrder(
            TAKER,
            new LimitOrder.Builder(BID, BTC_USD)
                .limitPrice(new BigDecimal(98))
                .originalAmount(new BigDecimal(2))
                .build());
    // When
    OrderBook level2 = matchingEngine.getLevel2OrderBook();
    // Then
    assertThat(maker1.getStatus()).isEqualTo(NEW);
    assertThat(maker2.getStatus()).isEqualTo(NEW);
    assertThat(taker1.getStatus()).isEqualTo(NEW);
    assertThat(taker2.getStatus()).isEqualTo(NEW);
    List<LimitOrder> asks = level2.getAsks();
    assertThat(asks).size().isEqualTo(2);
    assertThat(asks.get(0).getLimitPrice()).isEqualTo("100");
    assertThat(asks.get(0).getOriginalAmount()).isEqualTo("1");
    assertThat(asks.get(0).getType()).isEqualTo(ASK);
    assertThat(asks.get(1).getLimitPrice()).isEqualTo("102");
    assertThat(asks.get(1).getOriginalAmount()).isEqualTo("2");
    assertThat(asks.get(1).getType()).isEqualTo(ASK);
    List<LimitOrder> bids = level2.getBids();
    assertThat(bids).size().isEqualTo(2);
    assertThat(bids.get(0).getLimitPrice()).isEqualTo("99");
    assertThat(bids.get(0).getOriginalAmount()).isEqualTo("1");
    assertThat(bids.get(0).getType()).isEqualTo(BID);
    assertThat(bids.get(1).getLimitPrice()).isEqualTo("98");
    assertThat(bids.get(1).getOriginalAmount()).isEqualTo("2");
    assertThat(bids.get(1).getType()).isEqualTo(BID);
    System.out.println("Asks:" + asks);
    System.out.println("Bids:" + bids);
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
    assertThat(taker1.getStatus()).isEqualTo(FILLED);

    verify(onFill, atLeastOnce()).accept(fillCaptor1.capture());
    assertThat(fillCaptor1.getAllValues()).hasSize(6);
    assertFill(
        fillCaptor1.getAllValues().get(0),
        TAKER,
        taker1,
        maker1.getOriginalAmount(),
        maker1.getLimitPrice());
    assertFill(
        fillCaptor1.getAllValues().get(1),
        MAKER,
        maker1,
        maker1.getOriginalAmount(),
        maker1.getLimitPrice());
    assertFill(
        fillCaptor1.getAllValues().get(2),
        TAKER,
        taker1,
        maker4.getOriginalAmount(),
        maker4.getLimitPrice());
    assertFill(
        fillCaptor1.getAllValues().get(3),
        MAKER,
        maker4,
        maker4.getOriginalAmount(),
        maker4.getLimitPrice());
    assertFill(
        fillCaptor1.getAllValues().get(4),
        TAKER,
        taker1,
        new BigDecimal(1),
        maker2.getLimitPrice());
    assertFill(
        fillCaptor1.getAllValues().get(5),
        MAKER,
        maker2,
        new BigDecimal(1),
        maker2.getLimitPrice());

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
    assertThat(taker2.getStatus()).isEqualTo(FILLED);

    verify(onFill, atLeastOnce()).accept(fillCaptor2.capture());
    assertThat(fillCaptor2.getAllValues()).hasSize(4);
    assertFill(
        fillCaptor2.getAllValues().get(0),
        TAKER,
        taker2,
        new BigDecimal(1),
        maker2.getLimitPrice());
    assertFill(
        fillCaptor2.getAllValues().get(1),
        MAKER,
        maker2,
        new BigDecimal(1),
        maker2.getLimitPrice());
    assertFill(
        fillCaptor2.getAllValues().get(2),
        TAKER,
        taker2,
        new BigDecimal(4),
        maker3.getLimitPrice());
    assertFill(
        fillCaptor2.getAllValues().get(3),
        MAKER,
        maker3,
        new BigDecimal(4),
        maker3.getLimitPrice());

    Level3OrderBook book = matchingEngine.book();
    assertThat(book.getBids()).isEmpty();
    assertThat(book.getAsks()).isEmpty();
  }

  private AssertionMatcher<Fill> useAmount(String apiKey, LimitOrder order, BigDecimal amount) {
    return new AssertionMatcher<Fill>() {
      @Override
      public void assertion(Fill actual) throws AssertionError {
        assertFill(actual, apiKey, order, amount, order.getLimitPrice());
      }
    };
  }

  private void assertFill(
      Fill fill, String apiKey, LimitOrder order, BigDecimal amount, BigDecimal price) {
    assertThat(fill).hasApiKey(apiKey);
    assertThat(fill.getTrade())
        .hasOrderId(order.getId())
        .hasId()
        .hasOriginalAmount(amount)
        .hasPrice(price)
        .hasType(order.getType());
  }
}
