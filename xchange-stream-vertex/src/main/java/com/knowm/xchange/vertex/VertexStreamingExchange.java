package com.knowm.xchange.vertex;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.base.MoreObjects;
import static com.knowm.xchange.vertex.VertexStreamingService.ALL_MESSAGES;
import com.knowm.xchange.vertex.api.VertexApi;
import com.knowm.xchange.vertex.dto.RewardsList;
import com.knowm.xchange.vertex.dto.RewardsRequest;
import static com.knowm.xchange.vertex.dto.VertexModelUtils.buildSender;
import static com.knowm.xchange.vertex.dto.VertexModelUtils.convertToDecimal;
import static com.knowm.xchange.vertex.dto.VertexModelUtils.readX18Decimal;
import static com.knowm.xchange.vertex.dto.VertexModelUtils.readX18DecimalArray;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;

public class VertexStreamingExchange extends BaseExchange implements StreamingExchange {

  public static final String USE_LEVERAGE = "useLeverage";
  public static final String MAX_SLIPPAGE_RATIO = "maxSlippageRatio";
  public static final String DEFAULT_SUB_ACCOUNT = "default";
  public static final String PLACE_ORDER_VALID_UNTIL_MS_PROP = "placeOrderValidUntilMs";

  private VertexStreamingService subscriptionStream;
  private VertexStreamingMarketDataService streamingMarketDataService;
  private VertexStreamingTradeService streamingTradeService;

  private boolean useTestnet;

  private long chainId;

  private String endpointContract;

  private List<String> bookContracts;

  private VertexStreamingService requestResponseStream;
  private VertexProductInfo productInfo;

  private final Map<Long, TopOfBookPrice> marketPrices = new ConcurrentHashMap<>();
  private final Map<Long, InstrumentDefinition> increments = new HashMap<>();

  private final Set<Long> spotProducts = new TreeSet<>();
  private final Set<Long> perpProducts = new TreeSet<>();

  private Observable<JsonNode> allMessages;
  private VertexApi restApiClient;


  private VertexStreamingService createStreamingService(String suffix) {
    VertexStreamingService streamingService = new VertexStreamingService(getApiUrl() + suffix);
    applyStreamingSpecification(getExchangeSpecification(), streamingService);

    return streamingService;
  }

  private String getApiUrl() {
    return "wss://" + getHost(useTestnet);

  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    String host = getHost(useTestnet);
    exchangeSpecification.setSslUri("https://" + host);
    exchangeSpecification.setHost(host);
    exchangeSpecification.setExchangeName("Vertex");
    exchangeSpecification.setExchangeDescription("Vertex - One DEX. Everything you need.");
    return exchangeSpecification;
  }

  private static String getHost(boolean useTestnet) {
    return useTestnet ? "test.vertexprotocol-backend.com" : "prod.vertexprotocol-backend.com";
  }


  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    this.useTestnet = !Boolean.FALSE.equals(exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX));

    exchangeSpecification.setSslUri("https://" + getHost(useTestnet));

    super.applySpecification(exchangeSpecification);
  }


  @Override
  public void remoteInit() throws ExchangeException {

    if (!requestResponseStream.isSocketOpen() && !requestResponseStream.connect().blockingAwait(10, TimeUnit.SECONDS)) {
      throw new RuntimeException("Timeout waiting for connection");
    }

    ArrayList<Query> queries = new ArrayList<>();
    ArrayList<Query> priceQueries = new ArrayList<>();
    logger.info("Loading contract data and current prices");
    queries.add(new Query("{\"type\":\"contracts\"}",
        data1 -> {
          chainId = Long.parseLong(data1.get("chain_id").asText());
          endpointContract = data1.get("endpoint_addr").asText();
          bookContracts = new ArrayList<>();
          data1.withArray("book_addrs").elements().forEachRemaining(node -> bookContracts.add(node.asText()));
        }, (code, error) -> logger.error("Error loading contract data: " + code + " " + error)));

    List<BigDecimal> takerFees = new ArrayList<>();
    List<BigDecimal> makerFees = new ArrayList<>();
    AtomicReference<BigDecimal> takerSequencerFee = new AtomicReference<>();
    String walletAddress = exchangeSpecification.getApiKey();
    if (StringUtils.isNotEmpty(walletAddress)) {
      queries.add(new Query("{\"type\":\"fee_rates\", \"sender\": \"" + buildSender(walletAddress, getSubAccountOrDefault()) + "\"}",
          feeData -> {
            readX18DecimalArray(feeData, "taker_fee_rates_x18", takerFees);
            readX18DecimalArray(feeData, "maker_fee_rates_x18", makerFees);
            takerSequencerFee.set(readX18Decimal(feeData, "taker_sequencer_fee"));
          }, (code, error) -> logger.error("Error loading fees data: " + code + " " + error)));

    }
    queries.add(new Query("{\"type\":\"all_products\"}", productData -> {
      processProductIncrements(productData.withArray("spot_products"), spotProducts);
      processProductIncrements(productData.withArray("perp_products"), perpProducts);

      productInfo = new VertexProductInfo(spotProducts, restApiClient.symbols(), takerFees, makerFees, takerSequencerFee.get());


      for (Long productId : productInfo.getProductsIds()) {
        if (productId != 0) {
          Query marketPricesQuery = new Query("{\"type\":\"market_price\", \"product_id\": " + productId + "}",
              priceData -> {
                JsonNode bidX18 = priceData.get("bid_x18");
                BigInteger bid = new BigInteger(bidX18.asText());
                JsonNode offerX18 = priceData.get("ask_x18");
                BigInteger offer = new BigInteger(offerX18.asText());
                marketPrices.computeIfAbsent(productId, k -> new TopOfBookPrice(convertToDecimal(bid), convertToDecimal(offer)));
              }, (code, error) -> logger.error("Error loading market prices: " + code + " " + error));
          priceQueries.add(marketPricesQuery);
        }
      }
    }, (code, error) -> logger.error("Error loading product info: " + code + " " + error)));

    submitQueries(queries.toArray(new Query[0]));

    submitQueries(priceQueries.toArray(new Query[0]));

  }

  private void processProductIncrements(ArrayNode spotProducts, Set<Long> productSet) {
    for (JsonNode spotProduct : spotProducts) {
      long productId = spotProduct.get("product_id").asLong();
      if (productId == 0) { // skip USDC product
        continue;
      }
      productSet.add(productId);
      JsonNode bookInfo = spotProduct.get("book_info");
      BigDecimal quantityIncrement = convertToDecimal(new BigInteger(bookInfo.get("size_increment").asText()));
      BigDecimal priceIncrement = convertToDecimal(new BigInteger(bookInfo.get("price_increment_x18").asText()));
      increments.put(productId, new InstrumentDefinition(priceIncrement, quantityIncrement));
    }
  }

  public RewardsList queryRewards(String walletAddress) {
    return restApiClient.rewards(new RewardsRequest(new RewardsRequest.RewardAddress(walletAddress)));
  }

  public synchronized void submitQueries(Query... queries) {

    Observable<JsonNode> stream = subscribeToAllMessages();

    for (Query query : queries) {
      CountDownLatch responseLatch = new CountDownLatch(1);
      Disposable subscription = stream.subscribe(resp -> {
        JsonNode requestType = resp.get("request_type");
        if (requestType != null && requestType.textValue().startsWith("query_")) {
          try {
            JsonNode data = resp.get("data");
            JsonNode error = resp.get("error");
            JsonNode errorCode = resp.get("error_code");
            JsonNode status = resp.get("status");
            boolean success = status != null && status.asText().equals("success");

            if (!success) {
              query.getErrorHandler().accept(errorCode.asInt(-1), error.asText("Unknown error"));
            } else {
              query.getRespHandler().accept(data);
              logger.info("Query response " + data.toPrettyString());
            }
          } catch (Throwable t) {
            logger.error("Query error running " + query.getQueryMsg(), t);
          } finally {
            responseLatch.countDown();
          }

        }
      }, (err) -> logger.error("Query error running " + query.getQueryMsg(), err));
      try {
        logger.info("Sending query " + query.getQueryMsg());
        requestResponseStream.sendMessage(query.getQueryMsg());
        if (!responseLatch.await(20, TimeUnit.SECONDS)) {
          query.getErrorHandler().accept(-1, "Timed out after 20 seconds waiting for response for " + query.getQueryMsg());
        }
      } catch (InterruptedException e) {
        logger.error("Failed to get contract data due to timeout");
      } finally {
        subscription.dispose();
      }
    }


  }

  public Observable<JsonNode> subscribeToAllMessages() {
    if (allMessages == null) {
      allMessages = requestResponseStream.subscribeChannel(ALL_MESSAGES);
    }
    return allMessages;
  }

  @Override
  protected void initServices() {

    String wallet = exchangeSpecification.getApiKey();

    this.subscriptionStream = createStreamingService("/subscribe");
    this.requestResponseStream = createStreamingService("/ws");

    this.restApiClient = ExchangeRestProxyBuilder.forInterface(VertexApi.class, exchangeSpecification).build();

  }


  @Override
  public StreamingMarketDataService getStreamingMarketDataService() {
    if (this.streamingMarketDataService == null) {
      this.streamingMarketDataService = new VertexStreamingMarketDataService(subscriptionStream, productInfo, this);
    }
    return streamingMarketDataService;
  }

  @Override
  public VertexStreamingTradeService getStreamingTradeService() {
    if (this.streamingTradeService == null) {
      this.streamingTradeService = new VertexStreamingTradeService(requestResponseStream, subscriptionStream, getExchangeSpecification(), productInfo, chainId, bookContracts, this, endpointContract, getStreamingMarketDataService());
    }
    return streamingTradeService;
  }

  @Override
  public TradeService getTradeService() {
    return streamingTradeService;
  }

  @Override
  public Observable<ConnectionStateModel.State> connectionStateObservable() {
    return subscriptionStream.subscribeConnectionState();
  }

  @Override
  public Observable<Throwable> reconnectFailure() {
    return subscriptionStream.subscribeReconnectFailure();
  }

  @Override
  public Observable<Object> connectionSuccess() {
    return subscriptionStream.subscribeConnectionSuccess();
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    if (requestResponseStream.isSocketOpen()) {
      return subscriptionStream.connect();
    }
    return Completable.concatArray(subscriptionStream.connect(), requestResponseStream.connect());
  }

  @Override
  public Completable disconnect() {
    return Completable.concatArray(subscriptionStream.disconnect(), requestResponseStream.disconnect());
  }

  @Override
  public boolean isAlive() {
    return subscriptionStream.isSocketOpen() && requestResponseStream.isSocketOpen();
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    subscriptionStream.useCompressedMessages(compressedMessages);
  }

  public TopOfBookPrice getMarketPrice(Long productId) {
    return marketPrices.get(productId);
  }

  public void setMarketPrice(Long productId, TopOfBookPrice price) {
    marketPrices.put(productId, price);
  }


  /**
   * size and price increments for a product
   */
  public InstrumentDefinition getIncrements(Long productId) {
    return increments.get(productId);
  }

  public VertexApi getRestClient() {
    return restApiClient;
  }

  /*
   Fee charged on taker trades in BPS (always positive)
   */
  public BigDecimal getTakerTradeFee(long productId) {
    return productInfo.takerTradeFee(productId);
  }

  /*
  Rebate paid on maker trades in BPS (always positive)
  */
  public BigDecimal getMakerTradeFee(long productId) {
    return productInfo.makerTradeFee(productId);
  }

  String getSubAccountOrDefault() {
    return MoreObjects.firstNonNull(exchangeSpecification.getUserName(), DEFAULT_SUB_ACCOUNT);
  }

  /*
   Fixed USDC fee charged on every taker trade
   */
  public BigDecimal getTakerFee() {
    return productInfo.takerSequencerFee();
  }
}
