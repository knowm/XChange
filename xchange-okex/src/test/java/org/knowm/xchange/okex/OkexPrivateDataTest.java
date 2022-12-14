package org.knowm.xchange.okex;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderByInstrumentAndIdParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamInstrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Ignore
public class OkexPrivateDataTest {

    private final Logger LOG = LoggerFactory.getLogger(OkexPrivateDataTest.class);
    Instrument instrument = new FuturesContract("BTC/USDT/SWAP");
    Exchange exchange;

    @Before
    public void setUp(){
        Properties properties = new Properties();

        try {
            properties.load(this.getClass().getResourceAsStream("/secret.keys"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ExchangeSpecification spec = new OkexExchange().getDefaultExchangeSpecification();

        spec.setApiKey(properties.getProperty("apikey"));
        spec.setSecretKey(properties.getProperty("secret"));
        spec.setExchangeSpecificParametersItem(OkexExchange.PARAM_PASSPHRASE, properties.getProperty("passphrase"));
        spec.setExchangeSpecificParametersItem(OkexExchange.PARAM_SIMULATED, "1");

        exchange = ExchangeFactory.INSTANCE.createExchange(spec);
    }

    @Test
    public void placeLimitOrderGetOpenOrderAndCancelOrder() throws IOException {
        BigDecimal size = BigDecimal.ONE;
        BigDecimal price = BigDecimal.valueOf(1000);

        String orderId = exchange.getTradeService().placeLimitOrder(new LimitOrder.Builder(Order.OrderType.BID, instrument)
                        .originalAmount(size)
                        .limitPrice(price)
                .build());
        List<LimitOrder> openOrders = exchange.getTradeService().getOpenOrders().getOpenOrders();
        LOG.info(openOrders.toString());
        assertThat(openOrders.get(0).getId()).isEqualTo(orderId);
        assertThat(openOrders.get(0).getInstrument()).isEqualTo(instrument);
        assertThat(openOrders.get(0).getOriginalAmount()).isEqualTo(size);
        assertThat(openOrders.get(0).getLimitPrice()).isEqualTo(price);
        List<LimitOrder> openOrdersWithParams = exchange.getTradeService().getOpenOrders(new DefaultOpenOrdersParamInstrument(instrument)).getOpenOrders();
        LOG.info(openOrdersWithParams.toString());
        assertThat(openOrdersWithParams.get(0).getId()).isEqualTo(orderId);
        assertThat(openOrdersWithParams.get(0).getInstrument()).isEqualTo(instrument);
        assertThat(openOrdersWithParams.get(0).getOriginalAmount()).isEqualTo(size);
        assertThat(openOrdersWithParams.get(0).getLimitPrice()).isEqualTo(price);
        exchange.getTradeService().cancelOrder(new DefaultCancelOrderByInstrumentAndIdParams(instrument, orderId));
    }

    @Test
    public void placeOrderAndGetTradeHistory() throws IOException, InterruptedException {
        BigDecimal size = BigDecimal.valueOf(0.1);
        String bidOrderId = exchange.getTradeService().placeMarketOrder(new MarketOrder.Builder(Order.OrderType.BID,instrument).originalAmount(size).build());
        String askOrderId = exchange.getTradeService().placeMarketOrder(new MarketOrder.Builder(Order.OrderType.ASK,instrument).originalAmount(size).build());
        TimeUnit.SECONDS.sleep(2);
        List<UserTrade> userTrades = exchange.getTradeService().getTradeHistory(new DefaultTradeHistoryParamInstrument(instrument)).getUserTrades();
        UserTrade bid = null;
        UserTrade ask = null;

        for (UserTrade userTrade : userTrades) {
            if(userTrade.getId().equals(bidOrderId)){
                bid = userTrade;
            } else if(userTrade.getId().equals(askOrderId)){
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
        List<OpenPosition> openPositions = exchange.getTradeService().getOpenPositions().getOpenPositions();
        LOG.info(openPositions.toString());
        openPositions.forEach(openPosition -> assertThat(openPosition.getSize()).isGreaterThan(BigDecimal.ZERO));
    }

    @Test
    public void checkWallet() throws IOException {
        AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();
        LOG.info(accountInfo.toString());
        assertThat(accountInfo.getWallet(Wallet.WalletFeature.TRADING)).isNotNull();
        assertThat(accountInfo.getWallet(Wallet.WalletFeature.FUNDING)).isNotNull();
        assertThat(accountInfo.getWallet(Wallet.WalletFeature.FUTURES_TRADING)).isNotNull();
    }
}
