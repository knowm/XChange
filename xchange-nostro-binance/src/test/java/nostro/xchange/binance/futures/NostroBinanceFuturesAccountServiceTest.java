package nostro.xchange.binance.futures;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binance.futures.dto.AccountUpdateBinanceWebsocketTransaction;
import nostro.xchange.binance.DataSourceTest;
import nostro.xchange.binance.NostroBinanceUtils;
import nostro.xchange.binance.utils.NostroBinanceFuturesDtoUtils;
import nostro.xchange.binance.utils.NostroDBUtils;
import nostro.xchange.persistence.BalanceEntity;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.binance.dto.account.BinanceMarginPositionSide;
import org.knowm.xchange.binance.dto.account.BinanceMarginPositionType;
import org.knowm.xchange.binance.dto.account.BinanceMarginType;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesAsset;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesPosition;
import org.knowm.xchange.binance.futures.service.BinanceFuturesAccountService;
import org.knowm.xchange.binance.service.account.params.BinanceAccountMarginParams;
import org.knowm.xchange.binance.service.account.params.BinanceAccountPositionMarginParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.service.account.params.AccountLeverageParamsCurrencyPair;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class NostroBinanceFuturesAccountServiceTest extends DataSourceTest {

    private NostroBinanceFuturesAccountService service;
    private BinanceFuturesAccountService inner;
    private TransactionFactory txFactory;
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        txFactory = TransactionFactory.get("Binance", "user0001");
        inner = mock(BinanceFuturesAccountService.class);
        service = new NostroBinanceFuturesAccountService(inner, txFactory);

        mapper = new ObjectMapper();
    }

    @After
    public void tearDown() throws Exception {
        NostroDBUtils.dropTable(TransactionFactory.getDataSource(), "balance$" + txFactory.getAccountId());
    }

    @Test
    public void testSaveAccountInfo_verifyBalances() throws Exception {
        long time = Long.parseLong("1564745798938");
        AccountUpdateBinanceWebsocketTransaction transaction = generateAccountUpdateBinanceWebsocketTransaction();

        // when
        Pair<List<Balance>, List<OpenPosition>> pair = service.saveAccountInfo(transaction);

        // then
        // there are only 2 balances - one is skipped because of zero value
        assertThat(pair.getLeft().size()).isEqualTo(2);

        Optional<BalanceEntity> e1 = txFactory.executeAndGet(tx -> tx.getBalanceRepository().findLatestByAsset("USDT"));
        assertThat(e1.isPresent()).isTrue();
        assertThat(e1.get().getAsset()).isEqualTo("USDT");
        assertThat(e1.get().isZero()).isFalse();
        assertThat(e1.get().getDocument()).isNotNull();
        assertThat(e1.get().getTimestamp().getTime()).isEqualTo(time);

        Optional<BalanceEntity> e2 = txFactory.executeAndGet(tx -> tx.getBalanceRepository().findLatestByAsset("BUSD"));
        assertThat(e2.isPresent()).isTrue();
        assertThat(e2.get().getAsset()).isEqualTo("BUSD");
        assertThat(e2.get().isZero()).isFalse();
        assertThat(e2.get().getDocument()).isNotNull();
        assertThat(e2.get().getTimestamp().getTime()).isEqualTo(time);
    }

    @Test
    public void testSaveAccountInfo_verifyPositions() throws Exception {
        long time = Long.parseLong("1564745798938");
        AccountUpdateBinanceWebsocketTransaction transaction = generateAccountUpdateBinanceWebsocketTransaction();

        // when
        Pair<List<Balance>, List<OpenPosition>> pair = service.saveAccountInfo(transaction);

        // then
        // there are only 2 positions - one is skipped because of zero value
        assertThat(pair.getRight().size()).isEqualTo(2);

        Optional<BalanceEntity> e1 = txFactory.executeAndGet(tx -> tx.getBalanceRepository().findLatestByAsset("BTC/USDT/perpetual"));
        assertThat(e1.isPresent()).isTrue();
        assertThat(e1.get().getAsset()).isEqualTo("BTC/USDT/perpetual");
        assertThat(e1.get().isZero()).isFalse();
        assertThat(e1.get().getDocument()).isNotNull();
        assertThat(e1.get().getTimestamp().getTime()).isEqualTo(time);

        Optional<BalanceEntity> e2 = txFactory.executeAndGet(tx -> tx.getBalanceRepository().findLatestByAsset("ETH/USDT/perpetual"));
        assertThat(e2.isPresent()).isTrue();
        assertThat(e2.get().getAsset()).isEqualTo("ETH/USDT/perpetual");
        assertThat(e2.get().isZero()).isFalse();
        assertThat(e2.get().getDocument()).isNotNull();
        assertThat(e2.get().getTimestamp().getTime()).isEqualTo(time);
    }

    @Test
    public void testSaveAccountInfo_updateNotRequired() throws Exception {
        service.saveAccountInfo(generateAccountUpdateBinanceWebsocketTransaction_singleBalance("USDT", Long.parseLong("1564745798839")));
        service.saveAccountInfo(generateAccountUpdateBinanceWebsocketTransaction_singleBalance("USDT", Long.parseLong("1564745798837")));

        // then
        BalanceEntity entity = txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).get(0);

        // entity has initial balance - second update was outdated
        assertThat(entity.getAsset()).isEqualTo("USDT");
        assertThat(entity.getTimestamp().getTime()).isEqualTo(Long.parseLong("1564745798839"));
        assertThat(NostroUtils.readBalanceDocument(entity.getDocument()).getTotal()).isEqualTo(new BigDecimal("150.12345678"));
    }

    @Test
    public void testSetMarginType() throws Exception {
        BinanceAccountMarginParams params = new BinanceAccountMarginParams(BinanceMarginType.CROSSED, CurrencyPair.BTC_USDT);
        service.setMarginType(params);
        verify(inner, times(1)).setMarginType(params);
    }

    @Test
    public void testSetLeverage() throws Exception {
        AccountLeverageParamsCurrencyPair params = new AccountLeverageParamsCurrencyPair(1, CurrencyPair.BTC_USDT);
        service.setLeverage(params);
        verify(inner, times(1)).setLeverage(params);
    }

    @Test
    public void testSetIsolatedPositionMargin() throws Exception {
        BinanceAccountPositionMarginParams params = new BinanceAccountPositionMarginParams(CurrencyPair.BTC_USDT, BinanceMarginPositionSide.LONG, null, BinanceMarginPositionType.ADD);
        service.setIsolatedPositionMargin(params);
        verify(inner, times(1)).setIsolatedPositionMargin(params);
    }

    @Test
    public void testGetDynamicTradingFees() {
        Map<CurrencyPair, Fee> map = Collections.singletonMap(CurrencyPair.BCH_GBP, new Fee(new BigDecimal(1), new BigDecimal(2)));
        given(service.getDynamicTradingFees()).willReturn(map);
        assertThat(service.getDynamicTradingFees()).isEqualTo(map);
    }

    @Test
    public void getAccountInfo_empty() {
        AccountInfo accountInfo = service.getAccountInfo();
        assertThat(accountInfo.getTimestamp()).isNull();
        assertThat(accountInfo.getWallet().getBalances().isEmpty()).isTrue();
        assertThat(accountInfo.getOpenPositions().isEmpty()).isTrue();
    }

    @Test
    public void getAccountInfo() throws Exception {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        BinanceFuturesAsset asset1 = NostroBinanceFuturesDtoUtils.generateAsset("BTC", new BigDecimal("10.0"), new BigDecimal("10.0"), timestamp.getTime()-10);
        BinanceFuturesAsset asset2 = NostroBinanceFuturesDtoUtils.generateAsset("USDT", new BigDecimal("1000.0"), new BigDecimal("1000.0"), timestamp.getTime() - 20);
        txFactory.execute(tx -> tx.getBalanceRepository().insert(NostroBinanceUtils.toEntity(BinanceFuturesAdapter.adaptBalance(asset1))));
        txFactory.execute(tx -> tx.getBalanceRepository().insert(NostroBinanceUtils.toEntity(BinanceFuturesAdapter.adaptBalance(asset2))));

        BinanceFuturesPosition position = NostroBinanceFuturesDtoUtils.generatePosition("BTCUSDT", 1, new BigDecimal("55500"), new BigDecimal(1), timestamp.getTime());
        txFactory.execute(tx -> tx.getBalanceRepository().insert(NostroBinanceUtils.toEntity(BinanceFuturesAdapter.adaptPosition(position))));

        AccountInfo accountInfo = service.getAccountInfo();
        assertThat(accountInfo.getTimestamp()).isEqualTo(new Date(timestamp.getTime()));

        assertThat(accountInfo.getWallet().getBalance(new Currency("USDT")).getTotal()).isEqualTo(new BigDecimal("1000.0"));
        assertThat(accountInfo.getWallet().getBalance(new Currency("BTC")).getTotal()).isEqualTo(new BigDecimal("10.0"));

        assertThat(accountInfo.getOpenPositions().isEmpty()).isFalse();
        OpenPosition openPosition = accountInfo.getOpenPositions().iterator().next();
        assertThat(openPosition.getPrice()).isEqualTo(new BigDecimal("55500"));
        assertThat(openPosition.getSize()).isEqualTo(new BigDecimal("1"));
        assertThat(openPosition.getLeverage()).isEqualTo(new BigDecimal("1"));
    }

    private AccountUpdateBinanceWebsocketTransaction generateAccountUpdateBinanceWebsocketTransaction_singleBalance(String symbol, long time) throws JsonProcessingException {
        final String json = "{\n" +
                "  \"e\": \"ACCOUNT_UPDATE\"," +
                "  \"E\": " + time + "," +
                "  \"T\": " + time + "," +
                "  \"a\":" +
                "    {\n" +
                "      \"m\":\"ORDER\"," +
                "      \"B\":[" +
                "        {\n" +
                "          \"a\":\"" + symbol + "\"," +
                "          \"wb\":\"150.12345678\"," +
                "          \"cw\":\"100.12345678\"," +
                "          \"bc\":\"50.0000000\"" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "}";
        return mapper.readValue(json, AccountUpdateBinanceWebsocketTransaction.class);
    }

    private AccountUpdateBinanceWebsocketTransaction generateAccountUpdateBinanceWebsocketTransaction() throws JsonProcessingException {
        final String json = "{\n" +
                "    \"e\": \"ACCOUNT_UPDATE\",\n" +
                "    \"E\": 1564745798939,\n" +
                "    \"T\": 1564745798938,\n" +
                "    \"a\": {\n" +
                "        \"m\": \"ORDER\",\n" +
                "        \"B\": [\n" +
                "            {\n" +
                "                \"a\": \"USDT\",\n" +
                "                \"wb\": \"122624.12345678\",\n" +
                "                \"cw\": \"100.12345678\",\n" +
                "                \"bc\": \"50.12345678\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"a\": \"BUSD\",\n" +
                "                \"wb\": \"1.00000000\",\n" +
                "                \"cw\": \"0.00000000\",\n" +
                "                \"bc\": \"-49.12345678\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"a\": \"USDS\",\n" +
                "                \"wb\": \"0.00000000\",\n" +
                "                \"cw\": \"0.00000000\",\n" +
                "                \"bc\": \"-49.12345678\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"P\": [\n" +
                "            {\n" +
                "                \"s\": \"BTCUSDT\",\n" +
                "                \"pa\": \"0\",\n" +
                "                \"ep\": \"0.00000\",\n" +
                "                \"cr\": \"200\",\n" +
                "                \"up\": \"0\",\n" +
                "                \"mt\": \"isolated\",\n" +
                "                \"iw\": \"0.00000000\",\n" +
                "                \"ps\": \"BOTH\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"s\": \"BTCUSDT\",\n" +
                "                \"pa\": \"20\",\n" +
                "                \"ep\": \"6563.66500\",\n" +
                "                \"cr\": \"0\",\n" +
                "                \"up\": \"2850.21200\",\n" +
                "                \"mt\": \"isolated\",\n" +
                "                \"iw\": \"13200.70726908\",\n" +
                "                \"ps\": \"LONG\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"s\": \"ETHUSDT\",\n" +
                "                \"pa\": \"-10\",\n" +
                "                \"ep\": \"6563.86000\",\n" +
                "                \"cr\": \"-45.04000000\",\n" +
                "                \"up\": \"-1423.15600\",\n" +
                "                \"mt\": \"isolated\",\n" +
                "                \"iw\": \"6570.42511771\",\n" +
                "                \"ps\": \"SHORT\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        AccountUpdateBinanceWebsocketTransaction transaction = mapper.readValue(json, AccountUpdateBinanceWebsocketTransaction.class);
        return transaction;
    }

}