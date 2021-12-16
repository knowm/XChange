package nostro.xchange.binance.futures.sync;

import nostro.xchange.binance.DataSourceTest;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroStreamingPublisher;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesAccountInformation;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesTrade;
import org.knowm.xchange.binance.futures.service.BinanceFuturesAccountService;
import org.knowm.xchange.binance.futures.service.BinanceFuturesTradeService;
import org.knowm.xchange.currency.CurrencyPair;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BinanceFuturesSyncServiceTest extends DataSourceTest {
    public static final int SYNC_DELAY = 1000;
    private TransactionFactory txFactory;
    private NostroStreamingPublisher publisher;
    private BinanceFuturesAccountService accountService;
    private BinanceFuturesTradeService tradeService;

    @Before
    public void setUp() throws Exception {
        // not able to mock currently...
        txFactory = TransactionFactory.get("Binance Futures", "user0001");
        publisher = mock(NostroStreamingPublisher.class);
        accountService = mock(BinanceFuturesAccountService.class);
        tradeService = mock(BinanceFuturesTradeService.class);
    }

    @Test
    public void testGenerateTasks() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        List<Callable<?>> tasks = service.generateTasks();
        assertThat(tasks.size()).isEqualTo(2);
        assertThat(tasks.get(0)).isInstanceOf(BinanceFuturesBalanceSyncTask.class);
        assertThat(tasks.get(1)).isInstanceOf(BinanceFuturesSyncSubscriptionsTask.class);
    }

    @Test
    public void testGetFuturesAccountInfo() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        BinanceFuturesAccountInformation mock = mock(BinanceFuturesAccountInformation.class);
        given(accountService.getFuturesAccountInfo()).willReturn(mock);
        assertThat(service.getFuturesAccountInfo()).isEqualTo(mock);
    }

    @Test
    public void testGetFuturesAccountInfoThrows() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        given(accountService.getFuturesAccountInfo()).willThrow(new RuntimeException());
        assertThatThrownBy(service::getFuturesAccountInfo).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void testGetOpenOrders() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        BinanceFuturesOrder mock = mock(BinanceFuturesOrder.class);
        List<BinanceFuturesOrder> futuresOrders = Collections.singletonList(mock);
        given(tradeService.futuresOpenOrders(eq(CurrencyPair.BTC_USDT))).willReturn(futuresOrders);
        assertThat(service.getOpenOrders(CurrencyPair.BTC_USDT)).isEqualTo(futuresOrders);
    }

    @Test
    public void testGetOpenOrdersThrows() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        given(tradeService.futuresOpenOrders(eq(CurrencyPair.BTC_USDT))).willThrow(new RuntimeException());
        assertThatThrownBy(() -> service.getOpenOrders(CurrencyPair.BTC_USDT)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void testGetLastOrder() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        BinanceFuturesOrder mock = mock(BinanceFuturesOrder.class);
        List<BinanceFuturesOrder> futuresOrders = Collections.singletonList(mock);
        given(tradeService.futuresAllOrders(eq(CurrencyPair.BTC_USDT), eq(1), eq(null), eq(null), eq(null))).willReturn(futuresOrders);
        assertThat(service.getLastOrder(CurrencyPair.BTC_USDT)).isEqualTo(mock);
    }

    @Test
    public void testGetLastOrder2() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        given(tradeService.futuresAllOrders(eq(CurrencyPair.BTC_USDT), eq(1), eq(null), eq(null), eq(null))).willReturn(Collections.emptyList());
        assertThat(service.getLastOrder(CurrencyPair.BTC_USDT)).isEqualTo(null);
    }

    @Test
    public void testGetLastOrderThrows() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        given(tradeService.futuresAllOrders(eq(CurrencyPair.BTC_USDT), eq(1), eq(null), eq(null), eq(null))).willThrow(new RuntimeException());
        assertThatThrownBy(() -> service.getLastOrder(CurrencyPair.BTC_USDT)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void testGetOrder_binanceId() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        BinanceFuturesOrder mock = mock(BinanceFuturesOrder.class);
        given(tradeService.futuresOrderStatus(eq(CurrencyPair.BTC_USDT), eq((long) 1), eq(null))).willReturn(mock);
        assertThat(service.getOrder(CurrencyPair.BTC_USDT, 1)).isEqualTo(mock);
    }

    @Test
    public void testGetOrder_binanceId_Throws() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        given(tradeService.futuresOrderStatus(eq(CurrencyPair.BTC_USDT), eq((long) 1), eq(null))).willThrow(new RuntimeException());
        assertThatThrownBy(() -> service.getOrder(CurrencyPair.BTC_USDT, 1)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void testGetOrder_Id() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        BinanceFuturesOrder mock = mock(BinanceFuturesOrder.class);
        given(tradeService.futuresOrderStatus(eq(CurrencyPair.BTC_USDT), eq(null), eq("1"))).willReturn(mock);
        assertThat(service.getOrder(CurrencyPair.BTC_USDT, "1")).isEqualTo(mock);
    }

    @Test
    public void testGetOrder_Id_Wont_Throw() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        given(tradeService.futuresOrderStatus(eq(CurrencyPair.BTC_USDT), eq(null), eq("1"))).willThrow(new RuntimeException("Order does not exist"));
        assertThat(service.getOrder(CurrencyPair.BTC_USDT, "1")).isEqualTo(null);
    }

    @Test
    public void testGetOrder_Id_Throws() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        given(tradeService.futuresOrderStatus(eq(CurrencyPair.BTC_USDT), eq(null), eq("1"))).willThrow(new RuntimeException());
        assertThatThrownBy(() -> service.getOrder(CurrencyPair.BTC_USDT, "1")).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void testGetFirstTrade() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        BinanceFuturesTrade mock = mock(BinanceFuturesTrade.class);
        given(tradeService.myFuturesTrades(eq(CurrencyPair.BTC_USDT), eq(1), eq((long)10), eq(null), eq(null))).willReturn(Collections.singletonList(mock));
        assertThat(service.getFirstTrade(CurrencyPair.BTC_USDT, (long)10)).isEqualTo(mock);
    }

    @Test
    public void testGetFirstTrade2() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        given(tradeService.myFuturesTrades(eq(CurrencyPair.BTC_USDT), eq(1), eq((long)10), eq(null), eq(null))).willReturn(Collections.emptyList());
        assertThat(service.getFirstTrade(CurrencyPair.BTC_USDT, (long)10)).isEqualTo(null);
    }

    @Test
    public void testGetFirstTradeThrows() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        given(tradeService.myFuturesTrades(eq(CurrencyPair.BTC_USDT), eq(1), eq((long)10), eq(null), eq(null))).willThrow(new RuntimeException());
        assertThatThrownBy(() -> service.getFirstTrade(CurrencyPair.BTC_USDT, (long)10)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void testGetTrades() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        BinanceFuturesTrade mock = mock(BinanceFuturesTrade.class);
        given(tradeService.myFuturesTrades(eq(CurrencyPair.BTC_USDT), eq(5), eq(null), eq(null), eq((long)10))).willReturn(Collections.singletonList(mock));
        assertThat(service.getTrades(CurrencyPair.BTC_USDT, 10, 5)).isEqualTo(Collections.singletonList(mock));
    }

    @Test
    public void testGetTradesThrows() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        BinanceFuturesTrade mock = mock(BinanceFuturesTrade.class);
        given(tradeService.myFuturesTrades(eq(CurrencyPair.BTC_USDT), eq(5), eq(null), eq(null), eq((long)10))).willThrow(new RuntimeException());
        assertThatThrownBy(() -> service.getTrades(CurrencyPair.BTC_USDT, 10, 5)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void testGetOrders() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        BinanceFuturesOrder mock = mock(BinanceFuturesOrder.class);
        given(tradeService.futuresAllOrders(eq(CurrencyPair.BTC_USDT), eq(5), eq(null), eq(null), eq((long)10))).willReturn(Collections.singletonList(mock));
        assertThat(service.getOrders(CurrencyPair.BTC_USDT, 10, 5)).isEqualTo(Collections.singletonList(mock));
    }

    @Test
    public void testGetOrdersThrows() throws Exception {
        BinanceFuturesSyncService service = new BinanceFuturesSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        given(tradeService.futuresAllOrders(eq(CurrencyPair.BTC_USDT), eq(5), eq(null), eq(null), eq((long)10))).willThrow(new RuntimeException());
        assertThatThrownBy(() -> service.getOrders(CurrencyPair.BTC_USDT, 10, 5)).isInstanceOf(RuntimeException.class);
    }
}