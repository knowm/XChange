package org.xchange.bitz.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitz.BitZExchange;
import org.knowm.xchange.bitz.dto.account.result.BitZUserAssetsResult;
import org.knowm.xchange.bitz.dto.marketdata.*;
import org.knowm.xchange.bitz.dto.trade.result.*;
import org.knowm.xchange.bitz.service.BitZMarketDataServiceRaw;
import org.knowm.xchange.bitz.service.BitZTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class ApiTest {
    static final String apiKey = "";
    static final String secretKey = "";
    static final String tradePwd = "";
    static CurrencyPair pair = CurrencyPair.BTC_USDT;
    static Exchange exchange;
    static MarketDataService marketDataService;
    static AccountService accountService;
    static TradeService tradeService;

    @BeforeClass
    public static void beforeClass() {
        ExchangeSpecification exSpec = new BitZExchange().getDefaultExchangeSpecification();
        exSpec.setApiKey(apiKey);
        exSpec.setSecretKey(secretKey);
        exSpec.setPassword(tradePwd);
        exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
        marketDataService = exchange.getMarketDataService();
        accountService = exchange.getAccountService();
        tradeService = exchange.getTradeService();
    }

    @Before
    public void before() {
        Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());
    }

    /************** 行情 API 测试 ****************/
    @Test
    // todo https://apiv2.bitz.com/Market/ticker
    public void getTickerTest() throws IOException {
        BitZMarketDataServiceRaw serviceRaw = (BitZMarketDataServiceRaw) marketDataService;
        BitZTicker ticker = serviceRaw.getTicker(pair);
        System.out.println(ticker.toString());
    }

    @Test
    // todo https://apiv2.bitz.com/Market/tickerall
    public void getTickerAll() throws IOException {
        BitZMarketDataServiceRaw serviceRaw = (BitZMarketDataServiceRaw) marketDataService;
        BitZTickerAll tickerAll = serviceRaw.getTickerAll(pair);
        tickerAll.getAllTickers().forEach(
                (key, ticker) -> {
                    System.out.println("key:" + key + ";ticker: " + ticker.toString());
                }
        );
    }


    @Test
    // todo https://apiv2.bitz.com/Market/depth
    public void getDepth() throws IOException {
        BitZMarketDataServiceRaw serviceRaw = (BitZMarketDataServiceRaw) marketDataService;
        BitZOrders depth = serviceRaw.getDepth(pair);
        System.out.println(depth.toString());
    }

    @Test
    // todo https://apiv2.bitz.com/Market/order
    public void getOrder() throws IOException {
        BitZMarketDataServiceRaw serviceRaw = (BitZMarketDataServiceRaw) marketDataService;
        BitZTrades orders = serviceRaw.getOrder(pair);
    }

    @Test
    // todo https://apiv2.bitz.com/Market/kline
    public void getKline() throws IOException {
        BitZMarketDataServiceRaw serviceRaw = (BitZMarketDataServiceRaw) marketDataService;
        BitZKline data = serviceRaw.getKline(pair, BitZKlineResolution.MIN5, 300, null);
    }

    @Test
    // todo https://apiv2.bitz.com/Market/symbolList
    public void getSymbolList() throws IOException {
        BitZMarketDataServiceRaw serviceRaw = (BitZMarketDataServiceRaw) marketDataService;
        BitZSymbolList list = serviceRaw.getSymbolList();
        list.getSymbolMap().forEach(
                (key, value) -> {
                    System.out.println("key=" + key + "; value=" + value);
                }
        );
    }

    @Test
    // todo https://apiv2.bitz.com/Market/currencyRate
    public void getCurrencyRate() throws IOException {
        BitZMarketDataServiceRaw serviceRaw = (BitZMarketDataServiceRaw) marketDataService;
        BitZCurrencyRateList list = serviceRaw.getCurrencyRate();
        list.getCurrencyRateMap().forEach(
                (key, value) -> {
                    System.out.println("key=" + key + "; value=" + value);
                }
        );
    }

    @Test
    // todo https://apiv2.bitz.com/Market/currencyRate
    public void getCurrencyCoinRate() throws IOException {
        BitZMarketDataServiceRaw serviceRaw = (BitZMarketDataServiceRaw) marketDataService;
        BitZCurrencyCoinRateList list = serviceRaw.getCurrencyCoinRate(pair);
        list.getCurrencyCoinRateMap().forEach(
                (key, value) -> {
                    System.out.println("key=" + key + "; value=" + value);
                }
        );
    }

    @Test
    // todo https://apiv2.bitz.com/Market/coinRate
    public void getCoinRate() throws IOException {
        BitZMarketDataServiceRaw serviceRaw = (BitZMarketDataServiceRaw) marketDataService;
        BitZCurrencyCoinRateList list = serviceRaw.getCoinRate(pair);
        list.getCurrencyCoinRateMap().forEach(
                (key, value) -> {
                    System.out.println("key=" + key + "; value=" + value);
                }
        );
    }



    @Test
    // todo https://apiv2.bitz.com/Trade/addEntrustSheet
    public void addEntrustSheet() throws IOException {
        BitZTradeServiceRaw serviceRaw = (BitZTradeServiceRaw) tradeService;
        // type 购买类型 1买进 2 卖出
//        BitZTradeAddResult result =serviceRaw.addEntrustSheet(
//                BitZUtils.toSymbolString(pair),
//                "1",
//                BigDecimal.valueOf(500),
//                BigDecimal.valueOf(500));

        // todo 编造的数据
        InputStream is =
                ApiTest.class.getResourceAsStream(
                        "/org/xchange/bitz/dto/marketdata/example-add.json");
        ObjectMapper mapper = new ObjectMapper();
        BitZTradeAddResult result = mapper.readValue(is, BitZTradeAddResult.class);

        System.out.println(result.getData().toString());
    }

    @Test
    // todo https://apiv2.bitz.com/Trade/cancelEntrustSheet
    public void cancelEntrustSheet() throws IOException {
        BitZTradeServiceRaw serviceRaw = (BitZTradeServiceRaw) tradeService;
        //BitZTradeCancelResult result =serviceRaw.cancelEntrustSheet("1234567");

        // todo 编造的数据
        InputStream is =
                ApiTest.class.getResourceAsStream(
                        "/org/xchange/bitz/dto/marketdata/cancel.json");
        ObjectMapper mapper = new ObjectMapper();
        BitZTradeCancelResult result = mapper.readValue(is, BitZTradeCancelResult.class);

        System.out.println(result.getData().getAssetsInfo().toString());
    }

    @Test
    // todo https://apiv2.bitz.com/Trade/cancelAllEntrustSheet
    public void cancelAllEntrustSheet() throws IOException {
        BitZTradeServiceRaw serviceRaw = (BitZTradeServiceRaw) tradeService;
        //BitZTradeCancelListResult result = serviceRaw.cancelAllEntrustSheet("1234567");

        // todo 编造的数据
        InputStream is =
                ApiTest.class.getResourceAsStream(
                        "/org/xchange/bitz/dto/marketdata/cancelAll.json");
        ObjectMapper mapper = new ObjectMapper();
        BitZTradeCancelListResult result = mapper.readValue(is, BitZTradeCancelListResult.class);

        result.getData().getTradeCancelMap().forEach(
                (key,value)->{
                    System.out.println("key=" + key + "; value=" + value);
                }
        );
    }
    @Test
    // todo https://apiv2.bitz.com/Trade/getUserNowEntrustSheet
    public void getUserHistoryEntrustSheet() throws IOException {
        BitZTradeServiceRaw serviceRaw = (BitZTradeServiceRaw) tradeService;
        //BitZUserHistoryResult result = serviceRaw.getUserHistoryEntrustSheet();

        // todo 编造的数据
        // Read in the JSON from the example resources
        InputStream is =
                ApiTest.class.getResourceAsStream(
                        "/org/xchange/bitz/dto/marketdata/userHistory.json");

        // Use Jackson to parse it
        ObjectMapper mapper = new ObjectMapper();
        BitZUserHistoryResult result = mapper.readValue(is, BitZUserHistoryResult.class);


        Arrays.stream(result.getData().getHistory()).forEach(history->System.out.println(history.toString()));

    }

    @Test
    // todo https://apiv2.bitz.com/Trade/getEntrustSheetInfo
    public void getEntrustSheetInfo() throws IOException {
        BitZTradeServiceRaw serviceRaw = (BitZTradeServiceRaw) tradeService;
        //BitZEntrustSheetInfoResult result =serviceRaw.getEntrustSheetInfo("1234567");

        // Read in the JSON from the example resources
        InputStream is =
                ApiTest.class.getResourceAsStream(
                        "/org/xchange/bitz/dto/marketdata/entrustSheetInfo.json");

        // Use Jackson to parse it
        ObjectMapper mapper = new ObjectMapper();
        BitZEntrustSheetInfoResult result = mapper.readValue(is, BitZEntrustSheetInfoResult.class);

        System.out.println(result.getData().toString());
    }


    @Test
    // todo https://apiv2.bitz.com/Assets/getUserAssets
    public void getUserAssets() throws IOException {
        BitZTradeServiceRaw serviceRaw = (BitZTradeServiceRaw) tradeService;
        BitZUserAssetsResult result =serviceRaw.getUserAssets();

        System.out.println(result.getData().getInfo().toString());
    }


}
