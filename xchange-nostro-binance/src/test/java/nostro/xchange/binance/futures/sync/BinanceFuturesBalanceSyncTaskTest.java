package nostro.xchange.binance.futures.sync;

import nostro.xchange.binance.DataSourceTest;
import nostro.xchange.binance.utils.NostroDBUtils;
import nostro.xchange.persistence.BalanceEntity;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroStreamingPublisher;
import nostro.xchange.utils.NostroUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesAccountInformation;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesAsset;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesPosition;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.OpenPosition;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static nostro.xchange.binance.utils.NostroBinanceFuturesDTOUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class BinanceFuturesBalanceSyncTaskTest extends DataSourceTest {
    private TransactionFactory txFactory;
    private NostroStreamingPublisher publisher;
    private BinanceFuturesSyncService syncService;

    @Before
    public void setUp() throws Exception {
        txFactory = TransactionFactory.get("Binance Futures", "user0001");
        publisher = mock(NostroStreamingPublisher.class);
        syncService = mock(BinanceFuturesSyncService.class);

        given(syncService.getTXFactory()).willReturn(txFactory);
        given(syncService.getPublisher()).willReturn(publisher);
    }

    @After
    public void tearDown() throws Exception {
        NostroDBUtils.dropTable(TransactionFactory.getDataSource(), "balance$" + txFactory.getAccountId());
    }

    @Test
    public void syncBalance_empty() throws Exception {
        BinanceFuturesAccountInformation accountInformation = generateAccountInformation(0, null, null);
        given(syncService.getFuturesAccountInfo()).willReturn(accountInformation);
        // when
        new BinanceFuturesBalanceSyncTask(syncService).call();
        // then
        assertThat(txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).size()).isEqualTo(0);
        verifyNoInteractions(publisher);
    }

    @Test
    public void syncBalance() throws Exception {
        Timestamp updateTime = new Timestamp(new Date().getTime());
        BigDecimal walletBalance = new BigDecimal(100);
        BigDecimal availableBalance = new BigDecimal(99);
        String symbol = BinanceAdapters.toSymbol(Currency.USDT);
        BinanceFuturesAsset asset = generateAsset(symbol, walletBalance, availableBalance, updateTime.getTime());
        BinanceFuturesAccountInformation accountInformation = generateAccountInformation(0, null, Collections.singletonList(asset));
        given(syncService.getFuturesAccountInfo()).willReturn(accountInformation);
        // when
        new BinanceFuturesBalanceSyncTask(syncService).call();
        // then
        List<BalanceEntity> balanceEntities = txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest());
        assertThat(balanceEntities.size()).isEqualTo(1);
        BalanceEntity balanceEntity = balanceEntities.get(0);
        assertThat(balanceEntity.getAsset()).isEqualTo(symbol);
        Balance balance = NostroUtils.readBalanceDocument(balanceEntity.getDocument());
        assertThat(balance).isEqualTo(BinanceFuturesAdapter.adaptBalance(asset));

        verify(publisher, only()).publish(BinanceFuturesAdapter.adaptBalance(asset));
    }

    @Test
    public void syncBalance_zero() throws Exception {
        Timestamp updateTime = new Timestamp(new Date().getTime());
        BigDecimal walletBalance = new BigDecimal(0);
        BigDecimal availableBalance = new BigDecimal(0);
        BinanceFuturesAsset asset = generateAsset(BinanceAdapters.toSymbol(Currency.USDT), walletBalance, availableBalance, updateTime.getTime());
        BinanceFuturesAccountInformation accountInformation = generateAccountInformation(0, null, Collections.singletonList(asset));
        given(syncService.getFuturesAccountInfo()).willReturn(accountInformation);
        // when
        new BinanceFuturesBalanceSyncTask(syncService).call();
        // then
        assertThat(txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).size()).isEqualTo(0);
        verifyNoInteractions(publisher);
    }

    @Test
    public void syncBalance_became_zero() throws Exception {
        long time = new Timestamp(new Date().getTime()).getTime();
        // sync 1 time
        String symbol = BinanceAdapters.toSymbol(Currency.USDT);
        BinanceFuturesAsset asset = generateAsset(symbol, new BigDecimal(100), new BigDecimal(99), time -10);
        given(syncService.getFuturesAccountInfo()).willReturn(generateAccountInformation(0, null, Collections.singletonList(asset)));
        new BinanceFuturesBalanceSyncTask(syncService).call();
        assertThat(txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).size()).isEqualTo(1);
        Optional<BalanceEntity> balanceEntity1 = txFactory.executeAndGet(tx -> tx.getBalanceRepository().findLatestByAsset(symbol));
        assertThat(balanceEntity1.isPresent()).isTrue();
        assertThat(balanceEntity1.get().isZero()).isFalse();
        verify(publisher, times(1)).publish(BinanceFuturesAdapter.adaptBalance(asset));

        // sync 2 time
        BinanceFuturesAsset assetUpdated = generateAsset(symbol, new BigDecimal(0), new BigDecimal(0), time);
        given(syncService.getFuturesAccountInfo()).willReturn(generateAccountInformation(time, null, Collections.singletonList(assetUpdated)));
        new BinanceFuturesBalanceSyncTask(syncService).call();
        Optional<BalanceEntity> balanceEntity2 = txFactory.executeAndGet(tx -> tx.getBalanceRepository().findLatestByAsset(symbol));
        assertThat(balanceEntity2.isPresent()).isTrue();
        assertThat(balanceEntity2.get().isZero()).isTrue();
        verify(publisher, times(1)).publish(BinanceFuturesAdapter.adaptBalance(assetUpdated));
    }

    @Test
    public void syncBalance_sanity() throws Exception {
        // insert zero balance in case balance disappeared from account
        long time = new Timestamp(new Date().getTime()).getTime();
        // sync 1 time
        String symbol = BinanceAdapters.toSymbol(Currency.USDT);
        BinanceFuturesAsset asset = generateAsset(symbol, new BigDecimal(100), new BigDecimal(99), time -10);
        given(syncService.getFuturesAccountInfo()).willReturn(generateAccountInformation(0, null, Collections.singletonList(asset)));
        new BinanceFuturesBalanceSyncTask(syncService).call();
        assertThat(txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).size()).isEqualTo(1);
        assertThat(txFactory.executeAndGet(tx1 -> tx1.getBalanceRepository().findAllLatest().get(0)).isZero()).isFalse();

        // sync 2 time
        given(syncService.getFuturesAccountInfo()).willReturn(generateAccountInformation(time, null, null));
        new BinanceFuturesBalanceSyncTask(syncService).call();
        Optional<BalanceEntity> balanceEntity = txFactory.executeAndGet(tx -> tx.getBalanceRepository().findLatestByAsset(symbol));
        assertThat(balanceEntity.isPresent()).isTrue();
        assertThat(balanceEntity.get().isZero()).isTrue();

        verify(publisher, times(2)).publish(any(Balance.class));
    }

    @Test
    public void syncBalance_2_updates() throws Exception {
        long time = new Timestamp(new Date().getTime()).getTime();
        String symbol = BinanceAdapters.toSymbol(Currency.USDT);
        // sync 1 time
        BinanceFuturesAsset asset = generateAsset(symbol, new BigDecimal(100), new BigDecimal(99), time - 10);
        given(syncService.getFuturesAccountInfo()).willReturn(generateAccountInformation(0, null, Collections.singletonList(asset)));
        new BinanceFuturesBalanceSyncTask(syncService).call();
        assertThat(txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).size()).isEqualTo(1);
        assertThat(txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).get(0).getTimestamp().getTime()).isEqualTo(time - 10);

        // sync 2 time
        BinanceFuturesAsset assetUpdated = generateAsset(symbol, new BigDecimal(1000), new BigDecimal(1000), time);
        given(syncService.getFuturesAccountInfo()).willReturn(generateAccountInformation(time, null, Collections.singletonList(assetUpdated)));
        new BinanceFuturesBalanceSyncTask(syncService).call();
        assertThat(txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).size()).isEqualTo(1);
        assertThat(txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).get(0).getTimestamp().getTime()).isEqualTo(time);

        verify(publisher, times(2)).publish(any(Balance.class));
    }

    @Test
    public void syncPosition() throws Exception {
        Timestamp updateTime = new Timestamp(new Date().getTime());
        BigDecimal entryPrice = new BigDecimal(1000);
        BigDecimal positionAmount = new BigDecimal(10);
        String symbol = BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT);
        BinanceFuturesPosition futuresPosition = generatePosition(symbol, 1, entryPrice, positionAmount, updateTime.getTime());
        BinanceFuturesAccountInformation accountInformation = generateAccountInformation(0, Collections.singletonList(futuresPosition), null);
        given(syncService.getFuturesAccountInfo()).willReturn(accountInformation);
        // when
        new BinanceFuturesBalanceSyncTask(syncService).call();
        // then
        List<BalanceEntity> balanceEntities = txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest());
        assertThat(balanceEntities.size()).isEqualTo(1);
        BalanceEntity balanceEntity = balanceEntities.get(0);
        OpenPosition position = NostroUtils.readPositionDocument(balanceEntity.getDocument());
        assertThat(position).isEqualTo(BinanceFuturesAdapter.adaptPosition(futuresPosition));
        assertThat(balanceEntity.getAsset()).isEqualTo("BTC/USDT/perpetual");

        verify(publisher, only()).publish(any(OpenPosition.class));
    }

    @Test
    public void syncPosition_zero() throws Exception {
        Timestamp updateTime = new Timestamp(new Date().getTime());
        BigDecimal entryPrice = new BigDecimal(0);
        BigDecimal positionAmount = new BigDecimal(0);
        String symbol = BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT);
        BinanceFuturesPosition futuresPosition = generatePosition(symbol, 1, entryPrice, positionAmount, updateTime.getTime());
        BinanceFuturesAccountInformation accountInformation = generateAccountInformation(0, Collections.singletonList(futuresPosition), null);
        given(syncService.getFuturesAccountInfo()).willReturn(accountInformation);
        // when
        new BinanceFuturesBalanceSyncTask(syncService).call();
        // then
        assertThat(txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).size()).isEqualTo(0);

        verify(publisher, never()).publish(any(OpenPosition.class));
    }

    @Test
    public void syncPosition_became_zero() throws Exception {
        Timestamp updateTime = new Timestamp(new Date().getTime());
        String symbol = BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT);
        // sync 1 time
        BinanceFuturesPosition futuresPosition = generatePosition(symbol, 1, new BigDecimal(1000), new BigDecimal(10), updateTime.getTime() - 10);
        given(syncService.getFuturesAccountInfo()).willReturn(generateAccountInformation(updateTime.getTime() - 10, Collections.singletonList(futuresPosition), null));
        new BinanceFuturesBalanceSyncTask(syncService).call();
        assertThat(txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).size()).isEqualTo(1);
        Optional<BalanceEntity> balanceEntity1 = txFactory.executeAndGet(tx -> tx.getBalanceRepository().findLatestByAsset("BTC/USDT/perpetual"));
        assertThat(balanceEntity1.isPresent()).isTrue();
        assertThat(balanceEntity1.get().isZero()).isFalse();

        // sync 2 time
        BinanceFuturesPosition futuresPosition2 = generatePosition(symbol, 5, new BigDecimal(0), new BigDecimal(0), updateTime.getTime());
        given(syncService.getFuturesAccountInfo()).willReturn(generateAccountInformation(updateTime.getTime(), Collections.singletonList(futuresPosition2), null));
        new BinanceFuturesBalanceSyncTask(syncService).call();
        assertThat(txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).size()).isEqualTo(1);
        Optional<BalanceEntity> balanceEntity2 = txFactory.executeAndGet(tx -> tx.getBalanceRepository().findLatestByAsset("BTC/USDT/perpetual"));
        assertThat(balanceEntity2.isPresent()).isTrue();
        assertThat(balanceEntity2.get().isZero()).isTrue();

        verify(publisher, times(2)).publish(any(OpenPosition.class));
    }

    @Test
    public void syncPosition_sanity() throws Exception{
        Timestamp updateTime = new Timestamp(new Date().getTime());
        String symbol = BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT);
        // sync 1 time
        BinanceFuturesPosition futuresPosition = generatePosition(symbol, 1, new BigDecimal(1000), new BigDecimal(10), updateTime.getTime() - 10);
        given(syncService.getFuturesAccountInfo()).willReturn(generateAccountInformation(updateTime.getTime() - 10, Collections.singletonList(futuresPosition), null));
        new BinanceFuturesBalanceSyncTask(syncService).call();
        assertThat(txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).size()).isEqualTo(1);
        Optional<BalanceEntity> balanceEntity1 = txFactory.executeAndGet(tx -> tx.getBalanceRepository().findLatestByAsset("BTC/USDT/perpetual"));
        assertThat(balanceEntity1.isPresent()).isTrue();
        assertThat(balanceEntity1.get().isZero()).isFalse();

        // sync 2 time
        given(syncService.getFuturesAccountInfo()).willReturn(generateAccountInformation(updateTime.getTime(), null, null));
        new BinanceFuturesBalanceSyncTask(syncService).call();
        assertThat(txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).size()).isEqualTo(1);
        Optional<BalanceEntity> balanceEntity2 = txFactory.executeAndGet(tx -> tx.getBalanceRepository().findLatestByAsset("BTC/USDT/perpetual"));
        assertThat(balanceEntity2.isPresent()).isTrue();
        assertThat(balanceEntity2.get().isZero()).isTrue();

        verify(publisher, times(2)).publish(any(OpenPosition.class));

    }

    @Test
    public void syncPosition_2_updates() throws Exception {
        Timestamp updateTime = new Timestamp(new Date().getTime());
        String symbol = BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT);
        // sync 1 time
        BinanceFuturesPosition futuresPosition = generatePosition(symbol, 1, new BigDecimal(1000), new BigDecimal(10), updateTime.getTime() - 10);
        given(syncService.getFuturesAccountInfo()).willReturn(generateAccountInformation(updateTime.getTime() - 10, Collections.singletonList(futuresPosition), null));
        new BinanceFuturesBalanceSyncTask(syncService).call();
        assertThat(txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).size()).isEqualTo(1);

        // sync 2 time
        BinanceFuturesPosition futuresPosition2 = generatePosition(symbol, 5, new BigDecimal(5000), new BigDecimal(11), updateTime.getTime());
        given(syncService.getFuturesAccountInfo()).willReturn(generateAccountInformation(updateTime.getTime(), Collections.singletonList(futuresPosition2), null));
        new BinanceFuturesBalanceSyncTask(syncService).call();
        assertThat(txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).size()).isEqualTo(1);
        assertThat(txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).get(0).getTimestamp()).isEqualTo(updateTime);

        verify(publisher, times(2)).publish(any(OpenPosition.class));
    }

}