package org.knowm.xchange.enigma.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.enigma.EnigmaExchange;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProduct;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProductMarketData;
import org.knowm.xchange.enigma.dto.trade.EnigmaNewOrder;
import org.knowm.xchange.enigma.dto.trade.EnigmaNewOrderRequest;
import org.knowm.xchange.enigma.dto.trade.EnigmaWithdrawlResponse;
import org.knowm.xchange.enigma.model.Infrastructure;
import org.knowm.xchange.enigma.model.Side;

public class ServiceTests {


  private final static String username = null /* set value to test */ ;
  private final static String password = null /* set value to test */ ;
  private EnigmaExchange enigmaExchange;
  private EnigmaAccountService accountService;
  private EnigmaMarketDataService marketDataService;
  private EnigmaTradeService tradeService;

  @Before
  public void init() throws IOException {
    EnigmaExchange enigmaExchange = new EnigmaExchange();
    ExchangeSpecification exchangeSpec = enigmaExchange.getDefaultExchangeSpecification();
    exchangeSpec.setUserName(username);
    exchangeSpec.setPassword(password);
    enigmaExchange.applySpecification(exchangeSpec);
    accountService = new EnigmaAccountService(enigmaExchange);
    marketDataService = new EnigmaMarketDataService(enigmaExchange);
    tradeService = new EnigmaTradeService(enigmaExchange);
    accountService.login();
  }

  @Test()
  public void login() throws IOException {
    System.out.println(accountService.login().getKey());
  }

  @Ignore()
  @Test()
  public void getProducts() throws IOException {
    List<EnigmaProduct> enigmaProducts = accountService.getProducts();
    for (EnigmaProduct enigmaProduct : enigmaProducts) {
      System.out.println(enigmaProduct.getProductId() + ' ' + enigmaProduct.getProductName());
    }
  }

  @Ignore()
  @Test()
  public void getBalance() throws IOException {
    Map<String, BigDecimal> balances =
        accountService.getBalance(Infrastructure.DEVELOPMENT.getValue());
    for (Map.Entry<String, BigDecimal> balance : balances.entrySet()) {
      System.out.println("key: " + balance.getKey() + ", value: " + balance.getValue().toString());
    }
  }

  @Ignore()
  @Test
  public void getAccountLimits() throws IOException {
    Map<String, BigDecimal> limits = accountService.getRiskLimits();
    for (Map.Entry<String, BigDecimal> limit : limits.entrySet()) {
      System.out.println("key: " + limit.getKey() + ", value: " + limit.getValue().toString());
    }
  }

  @Ignore()
  @Test
  public void getAllWithdrawls() throws IOException {
    List<EnigmaWithdrawlResponse> withdrawlResponses = accountService.getWithdrawls();
    for (EnigmaWithdrawlResponse withdrawlResponse : withdrawlResponses) {
      System.out.println("key: " + withdrawlResponse.getCode());
    }
  }

  @Test
  public void getMarketData() throws IOException {
    EnigmaProductMarketData product = marketDataService.getProductMarketData(1);
    System.out.println(
        product.getProductName() + ": bid: " + product.getBid() + ", ask: " + product.getAsk());
  }

  @Test
  public void submitOrder() throws IOException {
    EnigmaNewOrder newOrder = tradeService.submitOrder(
        new EnigmaNewOrderRequest(5, Side.BUY.getValue(), null, BigDecimal.valueOf(100.00), Infrastructure.DEVELOPMENT.getValue()));
  }

}
