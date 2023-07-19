package org.knowm.xchange.examples.vertex;

import com.knowm.xchange.vertex.VertexOrderFlags;
import com.knowm.xchange.vertex.VertexStreamingExchange;
import com.knowm.xchange.vertex.VertexStreamingTradeService;
import com.knowm.xchange.vertex.dto.RewardsList;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.trade.params.CancelAllOrders;
import org.knowm.xchange.service.trade.params.DefaultCancelAllOrdersByInstrument;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderByInstrumentAndIdParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamInstrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

public class VertexOrderExample {

  private static final Logger log = LoggerFactory.getLogger(VertexOrderExample.class);


  public static void main(String[] args) throws IOException, InterruptedException {

    ExchangeSpecification exchangeSpecification = StreamingExchangeFactory.INSTANCE
        .createExchangeWithoutSpecification(VertexStreamingExchange.class)
        .getDefaultExchangeSpecification();


    ECKeyPair ecKeyPair = Credentials.create(System.getProperty("WALLET_PRIVATE_KEY")).getEcKeyPair();
    String address = "0x" + Keys.getAddress(ecKeyPair.getPublicKey());
    String subAccount = "default";

    exchangeSpecification.setApiKey(address);
    exchangeSpecification.setSecretKey(Numeric.toHexStringNoPrefix(ecKeyPair.getPrivateKey()));
    exchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.USE_SANDBOX, true);
    exchangeSpecification.setExchangeSpecificParametersItem(VertexStreamingExchange.USE_LEVERAGE, true);

    exchangeSpecification.setUserName(subAccount); //subaccount name

    VertexStreamingExchange exchange = (VertexStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    exchange.connect().blockingAwait();


    RewardsList rewardsList = exchange.queryRewards(address);
    System.out.println(rewardsList);

    VertexStreamingTradeService tradeService = exchange.getStreamingTradeService();


    CurrencyPair btc = new CurrencyPair("BTC-PERP", "USDC");

    Disposable trades = tradeService.getUserTrades(btc, subAccount).subscribe(userTrade -> {
      log.info("User trade: {}", userTrade);
    });

    Disposable changes = tradeService.getOrderChanges(btc, subAccount).subscribe(order -> {
      log.info("User order event: {}", order);
    });

    MarketOrder buy = new MarketOrder(Order.OrderType.BID, BigDecimal.valueOf(0.01), btc);
    buy.addOrderFlag(VertexOrderFlags.TIME_IN_FORCE_IOC);
    tradeService.placeMarketOrder(buy);

    Thread.sleep(2000);

    log.info("Open positions before sell: {}", tradeService.getOpenPositions());

    MarketOrder sell = new MarketOrder(Order.OrderType.ASK, BigDecimal.valueOf(0.01), btc);
    sell.addOrderFlag(VertexOrderFlags.TIME_IN_FORCE_FOK);
    tradeService.placeMarketOrder(sell);

    LimitOrder resting = new LimitOrder(Order.OrderType.BID, BigDecimal.valueOf(0.01), btc, null, null, BigDecimal.valueOf(20000));
    String orderId = tradeService.placeLimitOrder(resting);

    Thread.sleep(5000);

    log.info("Open orders before cancel: {}", tradeService.getOpenOrders(new DefaultOpenOrdersParamInstrument(btc)));
    log.info("Open positions before cancel: {}", tradeService.getOpenPositions());

    tradeService.cancelOrder(new DefaultCancelOrderByInstrumentAndIdParams(btc, orderId));

    log.info("Open orders after cancel: {}", tradeService.getOpenOrders(new DefaultOpenOrdersParamInstrument(btc)));

    // Check leveraged shorting works
    sell = new MarketOrder(Order.OrderType.ASK, BigDecimal.valueOf(0.01), btc);
    sell.addOrderFlag(VertexOrderFlags.TIME_IN_FORCE_FOK);
    tradeService.placeMarketOrder(sell);

    buy = new MarketOrder(Order.OrderType.BID, BigDecimal.valueOf(0.01), btc);
    buy.addOrderFlag(VertexOrderFlags.TIME_IN_FORCE_IOC);
    tradeService.placeMarketOrder(buy);

    Thread.sleep(2000);

    LimitOrder resting2 = new LimitOrder(Order.OrderType.BID, BigDecimal.valueOf(0.01), btc, null, null, BigDecimal.valueOf(20000));
    String orderId2 = tradeService.placeLimitOrder(resting);


    log.info("Open orders before cancel all instrument: {}", tradeService.getOpenOrders());

    tradeService.cancelOrder(new DefaultCancelAllOrdersByInstrument(btc));

    log.info("Open orders after cancel: {}", tradeService.getOpenOrders(new DefaultOpenOrdersParamInstrument(btc)));


    LimitOrder resting3 = new LimitOrder(Order.OrderType.ASK, BigDecimal.valueOf(0.01), btc, null, null, BigDecimal.valueOf(40000));
    String orderId3 = tradeService.placeLimitOrder(resting);


    log.info("Open orders before cancel all instrument: {}", tradeService.getOpenOrders(new DefaultOpenOrdersParamInstrument(btc)));

    tradeService.cancelOrder(new CancelAllOrders() {
    });


    log.info("Open orders after cancel: {}", tradeService.getOpenOrders(new DefaultOpenOrdersParamInstrument(btc)));

    exchange.disconnect().blockingAwait();

  }
}
