package org.knowm.xchange.bitso.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitso.BitsoAdapters;
import org.knowm.xchange.bitso.dto.BitsoException;
import org.knowm.xchange.bitso.dto.trade.BitsoConversionExecutionResponse;
import org.knowm.xchange.bitso.dto.trade.BitsoConversionQuoteRequest;
import org.knowm.xchange.bitso.dto.trade.BitsoConversionQuoteResponse;
import org.knowm.xchange.bitso.dto.trade.BitsoConversionStatusResponse;
import org.knowm.xchange.bitso.dto.trade.BitsoOrder;
import org.knowm.xchange.bitso.dto.trade.BitsoOrderRequest;
import org.knowm.xchange.bitso.dto.trade.BitsoOrderSide;
import org.knowm.xchange.bitso.dto.trade.BitsoOrderType;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * @author Piotr Ładyżyński
 */
public class BitsoTradeService extends BitsoTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitsoTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, BitsoException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    BitsoOrder[] openOrders = getBitsoOpenOrders();

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (BitsoOrder bitsoOrder : openOrders) {
      // Parse order type from side field
      OrderType orderType = "buy".equals(bitsoOrder.getSide()) ? OrderType.BID : OrderType.ASK;

      String id = bitsoOrder.getOid();
      BigDecimal price = bitsoOrder.getPrice();

      // Parse currency pair from book field
      CurrencyPair currencyPair = parseCurrencyPair(bitsoOrder.getBook());
      if (currencyPair == null) {
        // Skip orders with unparseable book field rather than using a fallback
        continue;
      }

      // Parse timestamp
      Date timestamp = parseTimestamp(bitsoOrder.getCreatedAt());

      limitOrders.add(
          new LimitOrder(
              orderType, bitsoOrder.getOriginalAmount(), currencyPair, id, timestamp, price));
    }
    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException, BitsoException {

    String book = formatCurrencyPair(marketOrder.getCurrencyPair());

    BitsoOrderRequest request =
        BitsoOrderRequest.builder()
            .book(book)
            .side(marketOrder.getType() == OrderType.BID ? BitsoOrderSide.BUY : BitsoOrderSide.SELL)
            .type(BitsoOrderType.MARKET)
            .major(marketOrder.getOriginalAmount())
            .build();

    return placeOrder(request);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException, BitsoException {

    String book = formatCurrencyPair(limitOrder.getCurrencyPair());

    BitsoOrderRequest request =
        BitsoOrderRequest.builder()
            .book(book)
            .side(limitOrder.getType() == OrderType.BID ? BitsoOrderSide.BUY : BitsoOrderSide.SELL)
            .type(BitsoOrderType.LIMIT)
            .major(limitOrder.getOriginalAmount())
            .price(limitOrder.getLimitPrice())
            .build();

    return placeOrder(request);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, BitsoException {

    String[] canceledOrders = cancelBitsoOrder(orderId);
    return canceledOrders != null && canceledOrders.length > 0;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    Integer limit = null;
    if (params instanceof TradeHistoryParamPaging) {
      limit = ((TradeHistoryParamPaging) params).getPageLength();
    }

    return BitsoAdapters.adaptTradeHistory(getBitsoUserTrades(limit, null, null));
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return DefaultTradeHistoryParamPaging.builder().pageLength(1000).build();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  /** Parse currency pair from book string (e.g., "btc_mxn" -> BTC/MXN) */
  private CurrencyPair parseCurrencyPair(String book) {
    if (book == null || !book.contains("_")) {
      return null;
    }

    String[] parts = book.split("_");
    if (parts.length != 2) {
      return null;
    }

    try {
      Currency base = Currency.getInstance(parts[0].toUpperCase());
      Currency counter = Currency.getInstance(parts[1].toUpperCase());
      return new CurrencyPair(base, counter);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  /** Format currency pair to book string (e.g., BTC/MXN -> "btc_mxn") */
  private String formatCurrencyPair(CurrencyPair currencyPair) {
    return currencyPair.getBase().getCurrencyCode().toLowerCase()
        + "_"
        + currencyPair.getCounter().getCurrencyCode().toLowerCase();
  }

  /** Parse ISO 8601 timestamp string to Date */
  private Date parseTimestamp(String timestamp) {
    if (timestamp == null) {
      return new Date();
    }

    try {
      return Date.from(Instant.parse(timestamp));
    } catch (Exception e) {
      return new Date();
    }
  }

  // ==========================================================================
  // Bitso API v4 Currency Conversion Methods
  // ==========================================================================

  /**
   * Request a conversion quote using Bitso API v4
   *
   * @param fromCurrency Source currency (e.g., "btc", "mxn", "usd")
   * @param toCurrency Target currency (e.g., "btc", "mxn", "usd")
   * @param amount Amount to convert in the source currency
   * @return Conversion quote with rate and expiration details
   * @throws IOException Network exception
   */
  public BitsoConversionQuoteResponse requestConversionQuote(
      String fromCurrency, String toCurrency, BigDecimal amount) throws IOException {
    return requestBitsoConversionQuote(fromCurrency, toCurrency, amount);
  }

  /**
   * Request a conversion quote using Bitso API v4 with Currency objects
   *
   * @param fromCurrency Source currency
   * @param toCurrency Target currency
   * @param amount Amount to convert in the source currency
   * @return Conversion quote with rate and expiration details
   * @throws IOException Network exception
   */
  public BitsoConversionQuoteResponse requestConversionQuote(
      Currency fromCurrency, Currency toCurrency, BigDecimal amount) throws IOException {
    return requestBitsoConversionQuote(
        fromCurrency.getCurrencyCode().toLowerCase(),
        toCurrency.getCurrencyCode().toLowerCase(),
        amount);
  }

  /**
   * Request a conversion quote with advanced parameters
   *
   * @param quoteRequest Complete quote request with all parameters
   * @return Conversion quote with rate and expiration details
   * @throws IOException Network exception
   */
  public BitsoConversionQuoteResponse requestConversionQuote(
      BitsoConversionQuoteRequest quoteRequest) throws IOException {
    return requestBitsoConversionQuote(quoteRequest);
  }

  /**
   * Execute a conversion quote using Bitso API v4
   *
   * @param quoteId Quote ID from the request quote call
   * @return Conversion execution response with conversion ID for tracking
   * @throws IOException Network exception
   */
  public BitsoConversionExecutionResponse executeConversionQuote(String quoteId)
      throws IOException {
    return executeBitsoConversionQuote(quoteId);
  }

  /**
   * Get the status of a conversion using Bitso API v4 (with enhanced "queued" state)
   *
   * @param conversionId Conversion ID from the execute quote call
   * @return Conversion status with detailed information
   * @throws IOException Network exception
   */
  public BitsoConversionStatusResponse getConversionStatus(String conversionId) throws IOException {
    return getBitsoConversionStatus(conversionId);
  }

  /**
   * Complete currency conversion flow: request quote, execute, and return conversion ID
   *
   * @param fromCurrency Source currency (e.g., "btc", "mxn", "usd")
   * @param toCurrency Target currency (e.g., "btc", "mxn", "usd")
   * @param amount Amount to convert in the source currency
   * @return Conversion ID for tracking the conversion status
   * @throws IOException Network exception
   */
  public String convertCurrency(String fromCurrency, String toCurrency, BigDecimal amount)
      throws IOException {
    // Step 1: Request quote
    BitsoConversionQuoteResponse quote = requestConversionQuote(fromCurrency, toCurrency, amount);

    // Step 2: Execute quote immediately
    BitsoConversionExecutionResponse execution = executeConversionQuote(quote.getId());

    return execution.getConversionId();
  }

  /**
   * Complete currency conversion flow using Currency objects
   *
   * @param fromCurrency Source currency
   * @param toCurrency Target currency
   * @param amount Amount to convert in the source currency
   * @return Conversion ID for tracking the conversion status
   * @throws IOException Network exception
   */
  public String convertCurrency(Currency fromCurrency, Currency toCurrency, BigDecimal amount)
      throws IOException {
    return convertCurrency(
        fromCurrency.getCurrencyCode().toLowerCase(),
        toCurrency.getCurrencyCode().toLowerCase(),
        amount);
  }
}
