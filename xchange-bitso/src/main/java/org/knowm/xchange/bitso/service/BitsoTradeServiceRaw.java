package org.knowm.xchange.bitso.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitso.BitsoAuthenticated;
import org.knowm.xchange.bitso.BitsoErrorAdapter;
import org.knowm.xchange.bitso.BitsoJacksonObjectMapperFactory;
import org.knowm.xchange.bitso.dto.BitsoBaseResponse;
import org.knowm.xchange.bitso.dto.BitsoException;
import org.knowm.xchange.bitso.dto.trade.*;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * @author Piotr Ładyżyński
 */
public class BitsoTradeServiceRaw extends BitsoBaseService {

  private final BitsoAuthenticated bitsoAuthenticated;
  private final BitsoDigest signatureCreator;

  /**
   * @param exchange
   */
  public BitsoTradeServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitsoAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                BitsoAuthenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new BitsoJacksonObjectMapperFactory()))
            .build();
    this.signatureCreator =
        BitsoDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getApiKey());
  }

  public BitsoOrder[] getBitsoOpenOrders() throws IOException {
    return getBitsoOpenOrders(null, null);
  }

  public BitsoOrder[] getBitsoOpenOrders(String book, Integer limit) throws IOException {
    try {
      BitsoBaseResponse<BitsoOrder[]> response =
          bitsoAuthenticated.getOpenOrders(
              signatureCreator, exchange.getNonceFactory(), book, limit);

      if (!response.getSuccess() || response.getError() != null) {
        String errorMessage =
            response.getError() != null
                ? response.getError().getMessage()
                : "Unknown error getting open orders";
        throw new ExchangeException("Error getting open orders. " + errorMessage);
      }

      return response.getPayload();
    } catch (BitsoException e) {
      throw BitsoErrorAdapter.adapt(e);
    }
  }

  /** Place a limit sell order */
  public String sellBitsoOrder(String book, BigDecimal originalAmount, BigDecimal price)
      throws IOException {
    BitsoOrderRequest request =
        BitsoOrderRequest.builder()
            .book(book)
            .side(BitsoOrderSide.SELL)
            .type(BitsoOrderType.LIMIT)
            .major(originalAmount)
            .price(price)
            .build();

    return placeOrder(request);
  }

  /** Place a limit buy order */
  public String buyBitsoOrder(String book, BigDecimal originalAmount, BigDecimal price)
      throws IOException {
    BitsoOrderRequest request =
        BitsoOrderRequest.builder()
            .book(book)
            .side(BitsoOrderSide.BUY)
            .type(BitsoOrderType.LIMIT)
            .major(originalAmount)
            .price(price)
            .build();

    return placeOrder(request);
  }

  /** Place any type of order using the v3 API */
  public String placeOrder(BitsoOrderRequest orderRequest) throws IOException {

    BitsoBaseResponse<BitsoOrderResponse> response =
        bitsoAuthenticated.placeOrder(signatureCreator, exchange.getNonceFactory(), orderRequest);

    if (!response.getSuccess() || response.getError() != null) {
      String errorMessage =
          response.getError() != null
              ? response.getError().getMessage()
              : "Unknown error placing order";
      throw new ExchangeException("Error placing order. " + errorMessage);
    }

    return response.getPayload().getOid();
  }

  public String[] cancelBitsoOrder(String orderId) throws IOException {

    BitsoBaseResponse<String[]> response =
        bitsoAuthenticated.cancelOrder(signatureCreator, exchange.getNonceFactory(), orderId);

    if (!response.getSuccess() || response.getError() != null) {
      String errorMessage =
          response.getError() != null
              ? response.getError().getMessage()
              : "Unknown error canceling order";
      throw new ExchangeException("Error canceling order. " + errorMessage);
    }

    return response.getPayload();
  }

  public String[] cancelBitsoOrders(String orderIds) throws IOException {

    BitsoBaseResponse<String[]> response =
        bitsoAuthenticated.cancelOrders(signatureCreator, exchange.getNonceFactory(), orderIds);

    if (!response.getSuccess() || response.getError() != null) {
      String errorMessage =
          response.getError() != null
              ? response.getError().getMessage()
              : "Unknown error canceling orders";
      throw new ExchangeException("Error canceling orders. " + errorMessage);
    }

    return response.getPayload();
  }

  public String[] cancelAllBitsoOrders() throws IOException {

    BitsoBaseResponse<String[]> response =
        bitsoAuthenticated.cancelAllOrders(signatureCreator, exchange.getNonceFactory());

    if (!response.getSuccess() || response.getError() != null) {
      String errorMessage =
          response.getError() != null
              ? response.getError().getMessage()
              : "Unknown error canceling all orders";
      throw new ExchangeException("Error canceling all orders. " + errorMessage);
    }

    return response.getPayload();
  }

  public BitsoUserTransaction[] getBitsoOrderTrades(String orderId) throws IOException {

    BitsoBaseResponse<BitsoUserTransaction[]> response =
        bitsoAuthenticated.getOrderTrades(signatureCreator, exchange.getNonceFactory(), orderId);

    if (!response.getSuccess() || response.getError() != null) {
      String errorMessage =
          response.getError() != null
              ? response.getError().getMessage()
              : "Unknown error getting order trades";
      throw new ExchangeException("Error getting order trades. " + errorMessage);
    }

    return response.getPayload();
  }

  public BitsoUserTransaction[] getBitsoUserTrades() throws IOException {
    return getBitsoUserTrades(null, null, null);
  }

  public BitsoUserTransaction[] getBitsoUserTrades(Integer limit, String sort, String book)
      throws IOException {

    BitsoBaseResponse<BitsoUserTransaction[]> response =
        bitsoAuthenticated.getUserTrades(
            signatureCreator, exchange.getNonceFactory(), limit, sort, book);

    if (!response.getSuccess() || response.getError() != null) {
      String errorMessage =
          response.getError() != null
              ? response.getError().getMessage()
              : "Unknown error getting user trades";
      throw new ExchangeException("Error getting user trades. " + errorMessage);
    }

    return response.getPayload();
  }

  public BitsoOrder[] getBitsoOrder(String orderId) throws IOException {

    BitsoBaseResponse<BitsoOrder[]> response =
        bitsoAuthenticated.getOrder(signatureCreator, exchange.getNonceFactory(), orderId);

    if (!response.getSuccess() || response.getError() != null) {
      String errorMessage =
          response.getError() != null
              ? response.getError().getMessage()
              : "Unknown error getting order";
      throw new ExchangeException("Error getting order. " + errorMessage);
    }

    return response.getPayload();
  }

  public BitsoOrder[] getBitsoOrders(String orderIds) throws IOException {

    BitsoBaseResponse<BitsoOrder[]> response =
        bitsoAuthenticated.getOrders(signatureCreator, exchange.getNonceFactory(), orderIds);

    if (!response.getSuccess() || response.getError() != null) {
      String errorMessage =
          response.getError() != null
              ? response.getError().getMessage()
              : "Unknown error getting orders";
      throw new ExchangeException("Error getting orders. " + errorMessage);
    }

    return response.getPayload();
  }

  // ==========================================================================
  // Bitso API v4 Methods
  // ==========================================================================

  /**
   * Modify an existing order using Bitso API v4 - by order ID
   *
   * @param orderId Bitso-supplied order ID
   * @param modifyRequest Modification parameters
   * @return Modified order ID
   * @throws IOException Network exception
   */
  public String modifyBitsoOrderById(String orderId, BitsoModifyOrderRequest modifyRequest)
      throws IOException {

    BitsoBaseResponse<BitsoOrderResponse> response =
        bitsoAuthenticated.modifyOrderById(
            signatureCreator, exchange.getNonceFactory(), orderId, modifyRequest);

    if (!response.getSuccess() || response.getError() != null) {
      String errorMessage =
          response.getError() != null
              ? response.getError().getMessage()
              : "Unknown error modifying order";
      throw new ExchangeException("Error modifying order. " + errorMessage);
    }

    return response.getPayload().getOid();
  }

  /**
   * Modify an existing order using Bitso API v4 - by origin ID
   *
   * @param originId Client-supplied origin ID
   * @param modifyRequest Modification parameters
   * @return Modified order ID
   * @throws IOException Network exception
   */
  public String modifyBitsoOrderByOriginId(String originId, BitsoModifyOrderRequest modifyRequest)
      throws IOException {

    BitsoBaseResponse<BitsoOrderResponse> response =
        bitsoAuthenticated.modifyOrderByOriginId(
            signatureCreator, exchange.getNonceFactory(), originId, modifyRequest);

    if (!response.getSuccess() || response.getError() != null) {
      String errorMessage =
          response.getError() != null
              ? response.getError().getMessage()
              : "Unknown error modifying order";
      throw new ExchangeException("Error modifying order. " + errorMessage);
    }

    return response.getPayload().getOid();
  }

  /**
   * Modify an existing order using Bitso API v4 - convenience method using general modify request
   *
   * @param modifyRequest Modification parameters including order identification
   * @return Modified order ID
   * @throws IOException Network exception
   */
  public String modifyBitsoOrder(BitsoModifyOrderRequest modifyRequest) throws IOException {
    // For general modification, we'll use the query parameter variant
    BitsoBaseResponse<BitsoOrderResponse> response =
        bitsoAuthenticated.modifyOrderByIdQuery(
            signatureCreator, exchange.getNonceFactory(), null, modifyRequest);

    if (!response.getSuccess() || response.getError() != null) {
      String errorMessage =
          response.getError() != null
              ? response.getError().getMessage()
              : "Unknown error modifying order";
      throw new ExchangeException("Error modifying order. " + errorMessage);
    }

    return response.getPayload().getOid();
  }

  // ==========================================================================
  // Bitso API v4 Currency Conversion Methods
  // ==========================================================================

  /**
   * Request a conversion quote using Bitso API v4
   *
   * @param fromCurrency Source currency ticker
   * @param toCurrency Target currency ticker
   * @param amount Amount to convert
   * @return Conversion quote with rate and expiration
   * @throws IOException Network exception
   */
  public BitsoConversionQuoteResponse requestBitsoConversionQuote(
      String fromCurrency, String toCurrency, BigDecimal amount) throws IOException {

    BitsoConversionQuoteRequest request =
        BitsoConversionQuoteRequest.builder()
            .from(fromCurrency)
            .to(toCurrency)
            .amount(amount)
            .build();

    return requestBitsoConversionQuote(request);
  }

  /**
   * Request a conversion quote using Bitso API v4 with full request parameters
   *
   * @param quoteRequest Complete quote request parameters
   * @return Conversion quote with rate and expiration
   * @throws IOException Network exception
   */
  public BitsoConversionQuoteResponse requestBitsoConversionQuote(
      BitsoConversionQuoteRequest quoteRequest) throws IOException {

    BitsoBaseResponse<BitsoConversionQuoteResponse> response =
        bitsoAuthenticated.requestConversionQuote(
            signatureCreator, exchange.getNonceFactory(), quoteRequest);

    if (!response.getSuccess() || response.getError() != null) {
      String errorMessage =
          response.getError() != null
              ? response.getError().getMessage()
              : "Unknown error requesting conversion quote";
      throw new ExchangeException("Error requesting conversion quote. " + errorMessage);
    }

    return response.getPayload();
  }

  /**
   * Execute a conversion quote using Bitso API v4
   *
   * @param quoteId Quote ID from the request quote call
   * @return Conversion execution response with conversion ID
   * @throws IOException Network exception
   */
  public BitsoConversionExecutionResponse executeBitsoConversionQuote(String quoteId)
      throws IOException {

    BitsoBaseResponse<BitsoConversionExecutionResponse> response =
        bitsoAuthenticated.executeConversionQuote(
            signatureCreator, exchange.getNonceFactory(), quoteId);

    if (!response.getSuccess() || response.getError() != null) {
      String errorMessage =
          response.getError() != null
              ? response.getError().getMessage()
              : "Unknown error executing conversion quote";
      throw new ExchangeException("Error executing conversion quote. " + errorMessage);
    }

    return response.getPayload();
  }

  /**
   * Get the status of a conversion using Bitso API v4 (with enhanced "queued" state)
   *
   * @param conversionId Conversion ID from the execute quote call
   * @return Conversion status with detailed information
   * @throws IOException Network exception
   */
  public BitsoConversionStatusResponse getBitsoConversionStatus(String conversionId)
      throws IOException {

    BitsoBaseResponse<BitsoConversionStatusResponse> response =
        bitsoAuthenticated.getConversionStatus(
            signatureCreator, exchange.getNonceFactory(), conversionId);

    if (!response.getSuccess() || response.getError() != null) {
      String errorMessage =
          response.getError() != null
              ? response.getError().getMessage()
              : "Unknown error getting conversion status";
      throw new ExchangeException("Error getting conversion status. " + errorMessage);
    }

    return response.getPayload();
  }
}
