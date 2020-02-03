package org.knowm.xchange.enigma;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProduct;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProductMarketData;
import org.knowm.xchange.enigma.dto.trade.EnigmaNewOrderRequest;
import org.knowm.xchange.enigma.dto.trade.EnigmaOrderSubmission;
import org.knowm.xchange.enigma.model.Infrastructure;
import org.knowm.xchange.enigma.model.Side;
import org.knowm.xchange.enigma.service.EnigmaAccountService;
import org.knowm.xchange.enigma.service.EnigmaMarketDataService;
import org.knowm.xchange.enigma.service.EnigmaTradeServiceRaw;

public class EnigmaOrderBookTest {

  private String username = "";
  private String password = "";
  private EnigmaExchange enigmaExchange;
  private EnigmaAccountService accountService;
  private EnigmaMarketDataService marketDataService;

  @Before
  public void init() throws IOException {
    enigmaExchange = new EnigmaExchange();
    ExchangeSpecification exchangeSpec = enigmaExchange.getDefaultExchangeSpecification();
    exchangeSpec.setUserName(username);
    exchangeSpec.setPassword(password);
    exchangeSpec.setExchangeSpecificParametersItem("infra", "prod");
    enigmaExchange.applySpecification(exchangeSpec);
    accountService = new EnigmaAccountService(enigmaExchange);
    marketDataService = new EnigmaMarketDataService(enigmaExchange);
    accountService.login();
  }

  @Test()
  public void getProducts() throws IOException {
    List<EnigmaProduct> enigmaProducts = marketDataService.getProducts();
    assertThat(enigmaProducts).isNotEmpty();
    assertThat(enigmaProducts.get(0)).isNotNull();
    assertThat(enigmaProducts.get(0).getProductName()).isEqualTo("BTC-EUR");
  }

  @Test()
  public void getOrderBook() throws IOException {
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_EUR);
    System.out.println("orderBook: " + orderBook);
    assertThat(orderBook.getAsks()).hasSizeGreaterThan(0);
  }

  // market data
  @Test()
  public void getMarketData() throws IOException {
    List<EnigmaProduct> enigmaProducts = marketDataService.getProducts();

    assertThat(enigmaProducts).isNotEmpty();
    assertThat(enigmaProducts.get(0)).isNotNull();
    assertThat(enigmaProducts.get(0).getProductName()).isEqualTo("BTC-EUR");

    int product_id = enigmaProducts.get(0).getProductId();
    EnigmaProductMarketData marketData = marketDataService.getProductMarketData(product_id);

    System.out.println("market data: " + marketData.getBid());
    assertThat(marketData.getAsk()).isGreaterThan(BigDecimal.ZERO);
  }

  // check risk limits
  @Test()
  public void getRiskLimit() throws IOException {
    System.out.println("RiskLimits");
    Map<String, BigDecimal> riskLimits = accountService.getRiskLimits();
    for (String key : riskLimits.keySet()) {
      BigDecimal value = riskLimits.get(key);
      System.out.println("(" + key + "," + value + ")");
    }
    assertThat(riskLimits.get("BTC-EUR_max_qty_per_trade")).isGreaterThan(BigDecimal.ZERO);
  }

  // test sell
  @Test()
  public void sell() throws IOException {
    List<EnigmaProduct> enigmaProducts = marketDataService.getProducts();

    assertThat(enigmaProducts).isNotEmpty();
    assertThat(enigmaProducts.get(0)).isNotNull();
    assertThat(enigmaProducts.get(0).getProductName()).isEqualTo("BTC-EUR");

    EnigmaNewOrderRequest enigmaNewOrderRequest = new EnigmaNewOrderRequest(enigmaProducts.get(0).getProductId(),
        Side.SELL.getValue(), new BigDecimal("0.002"), null, Infrastructure.PRODUCTION.getValue());

    EnigmaTradeServiceRaw tradeService = (EnigmaTradeServiceRaw) enigmaExchange.getTradeService();
    EnigmaOrderSubmission orderSubmission = tradeService.submitOrder(enigmaNewOrderRequest);
    assertThat(orderSubmission.getMessage()).isEqualTo("Order executed");
  }

  // test buy
  @Test()
  public void buy() throws IOException {
    List<EnigmaProduct> enigmaProducts = marketDataService.getProducts();

    assertThat(enigmaProducts).isNotEmpty();
    assertThat(enigmaProducts.get(0)).isNotNull();
    assertThat(enigmaProducts.get(0).getProductName()).isEqualTo("BTC-EUR");
    assertThat(enigmaProducts.get(0).getProductId()).isEqualTo(1);

    EnigmaNewOrderRequest enigmaNewOrderRequest = new EnigmaNewOrderRequest(enigmaProducts.get(0).getProductId(),
        Side.BUY.getValue(), null, new BigDecimal("15"), Infrastructure.PRODUCTION.getValue());

    EnigmaTradeServiceRaw tradeService = (EnigmaTradeServiceRaw) enigmaExchange.getTradeService();
    EnigmaOrderSubmission orderSubmission = tradeService.submitOrder(enigmaNewOrderRequest);
    assertThat(orderSubmission.getMessage()).isEqualTo("Order executed");
  }
}
