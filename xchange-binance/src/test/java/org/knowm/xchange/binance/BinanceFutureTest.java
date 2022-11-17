package org.knowm.xchange.binance;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.trade.BinanceCancelOrderParams;
import org.knowm.xchange.binance.dto.trade.BinanceQueryOrderParams;
import org.knowm.xchange.binance.dto.trade.BinanceTradeHistoryParams;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamInstrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

@Ignore
public class BinanceFutureTest {

    private static final Instrument futureContractPair = new FuturesContract("BTC/USDT/PERP");

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private static Exchange binanceExchange;

    @Before
    public void setUp() throws IOException {
        Properties prop = new Properties();
        prop.load(this.getClass().getResourceAsStream("/keys.properties"));

        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class);

        ExchangeSpecification spec = exchange.getExchangeSpecification();

        spec.setApiKey(prop.getProperty("apikey"));
        spec.setSecretKey(prop.getProperty("secret"));

        exchange.applySpecification(spec);

        binanceExchange = exchange;
    }

    @Test
    public void binanceFutureMarketDataService() throws IOException {
        // Get OrderBook
        logger.info("OrderBook: "+binanceExchange.getMarketDataService().getOrderBook(futureContractPair));

        //Get Ticker
        logger.info("Ticker: "+binanceExchange.getMarketDataService().getTicker(futureContractPair));

        //Get Trades
        logger.info("Trades: "+binanceExchange.getMarketDataService().getTrades(futureContractPair));
    }

    @Test
    public void binanceFutureAccountService() throws IOException {

        AccountInfo accountInfo = binanceExchange.getAccountService().getAccountInfo();
        logger.info("AccountInfo: "+accountInfo.getWallet(Wallet.WalletFeature.FUTURES_TRADING));
        logger.info("Positions: "+accountInfo.getOpenPositions().toString());

    }

    @Test
    public void binanceFutureTradeService() throws IOException {
        Set<Order.IOrderFlags> orderFlags = new HashSet<>();
//        orderFlags.add(BinanceOrderFlags.REDUCE_ONLY);

        //Open Positions
        logger.info("Positions: "+ binanceExchange.getTradeService().getOpenPositions().getOpenPositions());

        // Get UserTrades
        logger.info("UserTrades: "+binanceExchange.getTradeService().getTradeHistory(new BinanceTradeHistoryParams(futureContractPair)).getUserTrades());

        // Place LimitOrder
        String orderId = binanceExchange.getTradeService().placeLimitOrder(new LimitOrder.Builder(Order.OrderType.BID, futureContractPair)
                        .limitPrice(BigDecimal.valueOf(1000))
                        .flags(orderFlags)
                        .originalAmount(BigDecimal.ONE)
                .build());
        // Get OpenOrders
        logger.info("OpenOrders: "+binanceExchange.getTradeService().getOpenOrders(new DefaultOpenOrdersParamInstrument(futureContractPair)).getOpenOrders());

        // Get order
        logger.info("GetOrder: "+binanceExchange.getTradeService().getOrder(new BinanceQueryOrderParams(futureContractPair, orderId)));

        //Cancel LimitOrder
        logger.info("CancelOrder: "+binanceExchange.getTradeService().cancelOrder(new BinanceCancelOrderParams(futureContractPair, orderId)));
    }
}
