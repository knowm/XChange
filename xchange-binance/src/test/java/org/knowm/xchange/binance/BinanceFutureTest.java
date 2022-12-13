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
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.FundingRates;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamInstrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Ignore
public class BinanceFutureTest {

    private static final Instrument instrument = new FuturesContract("BTC/USDT/PERP");

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
        spec.setExchangeSpecificParametersItem(BinanceExchange.SPECIFIC_PARAM_USE_FUTURES_SANDBOX, true);
        exchange.applySpecification(spec);

        binanceExchange = exchange;
    }

    @Test
    public void binanceFutureMarketDataService() throws IOException {
        // Get OrderBook
        OrderBook orderBook = binanceExchange.getMarketDataService().getOrderBook(instrument);
        logger.info("OrderBook: "+orderBook);
        assertThat(orderBook.getBids().get(0).getInstrument()).isEqualTo(instrument);
        //Get Ticker
        Ticker ticker = binanceExchange.getMarketDataService().getTicker(instrument);
        logger.info("Ticker: "+ticker);
        assertThat(ticker.getInstrument()).isEqualTo(instrument);

        //Get Trades
        Trades trades = binanceExchange.getMarketDataService().getTrades(instrument);
        logger.info("Trades: "+trades);
        assertThat(trades.getTrades().get(0).getInstrument()).isEqualTo(instrument);
        //Get Funding rates
        FundingRates fundingRates = binanceExchange.getMarketDataService().getFundingRates();
        fundingRates.getFundingRates().forEach(fundingRate -> System.out.println(fundingRate.toString()));
    }

    @Test
    public void binanceFutureAccountService() throws IOException {

        AccountInfo accountInfo = binanceExchange.getAccountService().getAccountInfo();
        logger.info("AccountInfo: "+accountInfo.getWallet(Wallet.WalletFeature.FUTURES_TRADING));
        assertThat(accountInfo.getOpenPositions().stream().anyMatch(openPosition -> openPosition.getInstrument().equals(instrument))).isTrue();
        logger.info("Positions: "+ accountInfo.getOpenPositions());

    }

    @Test
    public void binanceFutureTradeService() throws IOException {
        Set<Order.IOrderFlags> orderFlags = new HashSet<>();
//        orderFlags.add(BinanceOrderFlags.REDUCE_ONLY);

        //Open Positions
        List<OpenPosition> openPositions = binanceExchange.getTradeService().getOpenPositions().getOpenPositions();
        logger.info("Positions: "+ openPositions);
        assertThat(openPositions.stream().anyMatch(openPosition -> openPosition.getInstrument().equals(instrument))).isTrue();

        // Get UserTrades
        List<UserTrade> userTrades = binanceExchange.getTradeService().getTradeHistory(new BinanceTradeHistoryParams(instrument)).getUserTrades();
        logger.info("UserTrades: "+ userTrades);
        assertThat(userTrades.stream().anyMatch(userTrade -> userTrade.getInstrument().equals(instrument))).isTrue();

        // Place LimitOrder
        String orderId = binanceExchange.getTradeService().placeLimitOrder(new LimitOrder.Builder(Order.OrderType.BID, instrument)
                        .limitPrice(BigDecimal.valueOf(1000))
                        .flags(orderFlags)
                        .originalAmount(BigDecimal.ONE)
                .build());
        // Get OpenOrders
        List<LimitOrder> openOrders = binanceExchange.getTradeService().getOpenOrders(new DefaultOpenOrdersParamInstrument(instrument)).getOpenOrders();
        logger.info("OpenOrders: "+openOrders);
        assertThat(openOrders.stream().anyMatch(openOrder -> openOrder.getInstrument().equals(instrument))).isTrue();

        // Get order
        Collection<Order> order = binanceExchange.getTradeService().getOrder(new BinanceQueryOrderParams(instrument, orderId));
        logger.info("GetOrder: "+ order);
        assertThat(order.stream().anyMatch(order1 -> order1.getInstrument().equals(instrument))).isTrue();

        //Cancel LimitOrder
        logger.info("CancelOrder: "+binanceExchange.getTradeService().cancelOrder(new BinanceCancelOrderParams(instrument, orderId)));
    }
}
