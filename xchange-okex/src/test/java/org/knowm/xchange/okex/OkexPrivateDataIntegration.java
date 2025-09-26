package org.knowm.xchange.okex;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.knowm.xchange.okex.dto.trade.OkexOrderFlags.POST_ONLY;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.dto.trade.OkexTradeParams.OkexCancelOrderParams;
import org.knowm.xchange.okex.service.OkexAccountService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamInstrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class OkexPrivateDataIntegration {

  private final Logger LOG = LoggerFactory.getLogger(OkexPrivateDataIntegration.class);
  Instrument instrument = new FuturesContract("BTC/USDT/SWAP");
  Exchange exchange;

  @Before
  public void setUp() {
    Properties properties = new Properties();

    try {
      properties.load(this.getClass().getResourceAsStream("/secret.keys"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ExchangeSpecification spec = new OkexExchange().getDefaultExchangeSpecification();

    spec.setApiKey(properties.getProperty("apikey"));
    spec.setSecretKey(properties.getProperty("secret"));
    spec.setExchangeSpecificParametersItem(
        OkexExchange.PARAM_PASSPHRASE, properties.getProperty("passphrase"));
    spec.setExchangeSpecificParametersItem(OkexExchange.PARAM_SIMULATED, "1");

    exchange = ExchangeFactory.INSTANCE.createExchange(spec);
  }

  @Test
  public void placeLimitOrderGetOpenOrderAndCancelOrder() throws IOException {
    Ticker ticker = exchange.getMarketDataService().getTicker(instrument);
    InstrumentMetaData instrumentMetaData =
        exchange.getExchangeMetaData().getInstruments().get(instrument);
    BigDecimal size = instrumentMetaData.getMinimumAmount();
    BigDecimal price = ticker.getLow();
    String userReference = RandomStringUtils.randomAlphanumeric(10);
    String orderId =
        exchange
            .getTradeService()
            .placeLimitOrder(
                new LimitOrder.Builder(OrderType.BID, instrument)
                    .originalAmount(size)
                    .limitPrice(price)
                    .flag(POST_ONLY)
                    .userReference(userReference)
                    .build());
    List<LimitOrder> openOrders = exchange.getTradeService().getOpenOrders().getOpenOrders();
    LOG.info(openOrders.toString());
    assertThat(openOrders.get(0).getId()).isEqualTo(orderId);
    assertThat(openOrders.get(0).getInstrument()).isEqualTo(instrument);
    assertThat(openOrders.get(0).getOriginalAmount()).isEqualTo(size);
    assertThat(openOrders.get(0).getLimitPrice()).isEqualTo(price);
    List<LimitOrder> openOrdersWithParams =
        exchange
            .getTradeService()
            .getOpenOrders(new DefaultOpenOrdersParamInstrument(instrument))
            .getOpenOrders();
    LOG.info(openOrdersWithParams.toString());
    assertThat(openOrdersWithParams.get(0).getId()).isEqualTo(orderId);
    assertThat(openOrdersWithParams.get(0).getInstrument()).isEqualTo(instrument);
    assertThat(openOrdersWithParams.get(0).getOriginalAmount()).isEqualTo(size);
    assertThat(openOrdersWithParams.get(0).getLimitPrice()).isEqualTo(price);
    String changedOrderId =
        exchange
            .getTradeService()
            .changeOrder(
                new LimitOrder.Builder(Order.OrderType.BID, instrument)
                    .limitPrice(price.add(BigDecimal.ONE))
                    .originalAmount(size)
                    .id(orderId)
                    .build());
    openOrdersWithParams =
        exchange
            .getTradeService()
            .getOpenOrders(new DefaultOpenOrdersParamInstrument(instrument))
            .getOpenOrders();
    LOG.info(openOrdersWithParams.toString());
    assertThat(openOrdersWithParams.get(0).getId()).isEqualTo(changedOrderId);
    assertThat(openOrdersWithParams.get(0).getInstrument()).isEqualTo(instrument);
    assertThat(openOrdersWithParams.get(0).getOriginalAmount()).isEqualTo(size);
    assertThat(openOrdersWithParams.get(0).getLimitPrice()).isEqualTo(price.add(BigDecimal.ONE));
    exchange
        .getTradeService()
        .cancelOrder(new OkexCancelOrderParams(instrument, orderId, userReference));
  }

  @Test
  public void placeOrderAndGetTradeHistory() throws IOException, InterruptedException {
    BigDecimal size = BigDecimal.valueOf(0.01);
    String bidOrderId =
        exchange
            .getTradeService()
            .placeMarketOrder(
                new MarketOrder.Builder(Order.OrderType.BID, instrument)
                    .originalAmount(size)
                    .build());
    String askOrderId =
        exchange
            .getTradeService()
            .placeMarketOrder(
                new MarketOrder.Builder(Order.OrderType.ASK, instrument)
                    .originalAmount(size)
                    .build());
    TimeUnit.SECONDS.sleep(2);
    List<UserTrade> userTrades =
        exchange
            .getTradeService()
            .getTradeHistory(new DefaultTradeHistoryParamInstrument(instrument))
            .getUserTrades();
    UserTrade bid = null;
    UserTrade ask = null;

    for (UserTrade userTrade : userTrades) {
      if (userTrade.getId().equals(bidOrderId)) {
        bid = userTrade;
      } else if (userTrade.getId().equals(askOrderId)) {
        ask = userTrade;
      }
    }

    assert ask != null;
    assert bid != null;
    assertThat(ask.getOriginalAmount()).isEqualTo(size);
    assertThat(bid.getOriginalAmount()).isEqualTo(size);
  }

  @Test
  public void checkOpenPositions() throws IOException {
    List<OpenPosition> openPositions =
        exchange.getTradeService().getOpenPositions().getOpenPositions();
    LOG.info(openPositions.toString());
    openPositions.forEach(
        openPosition -> assertThat(openPosition.getSize()).isGreaterThan(BigDecimal.ZERO));
  }

  @Test
  public void checkWallet() throws IOException {
    AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();
    LOG.info(accountInfo.toString());
    assertThat(accountInfo.getWallet(Wallet.WalletFeature.TRADING)).isNotNull();
    assertThat(accountInfo.getWallet(Wallet.WalletFeature.FUNDING)).isNotNull();
    assertThat(accountInfo.getWallet(Wallet.WalletFeature.FUTURES_TRADING)).isNotNull();
  }

  @Test
  public void feeRates() throws IOException {
    OkexAccountService okexAccountService = ((OkexAccountService) exchange.getAccountService());
    Map<Instrument, Fee> feeMap = okexAccountService.getDynamicTradingFeesByInstrument("SWAP");
    feeMap.forEach(
        (key, value) -> {
          System.out.println("Key : " + key + " Value : " + value);
        });
  }

  @Test
  public void setLeverage() throws IOException {
    OkexAccountService okexAccountService = ((OkexAccountService) exchange.getAccountService());
    System.out.println(
        "Set leverage 1, for "
            + instrument
            + ", result: "
            + okexAccountService.setLeverage(instrument, 1));
  }
}
