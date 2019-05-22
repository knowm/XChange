package info.bitrich.xchangestream.okex;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.okex.dto.OkCoinOrderbook;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.okcoin.FuturesContract;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinDepth;
import org.knowm.xchange.okex.v3.OkexFuturesPrompt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class OkexStreamingMarketDataService implements StreamingMarketDataService {

    private final Logger LOG = LoggerFactory.getLogger(OkexStreamingMarketDataService.class);

    private final OkexStreamingService service;
    private final OkexStreamingExchange exchange;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, OkCoinOrderbook> orderbooks = new HashMap<>();

    OkexStreamingMarketDataService(OkexStreamingService service, OkexStreamingExchange exchange) {
        this.service = service;
        this.exchange = exchange;
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        if (args.length > 0 && args[0] instanceof OkexFuturesPrompt) {
            OkexFuturesPrompt contract = (OkexFuturesPrompt) args[0];
            final String instrumentId = exchange.determineFuturesInstrumentId(currencyPair, contract);
            String channel = "futures/depth:" + instrumentId;
            return this.service.subscribeChannel(channel).map((s) -> {
                OkCoinOrderbook okCoinOrderbook;
                for (JsonNode data : s.get("data")) {
                    if (instrumentId.equals(data.get("instrument_id").asText())) {
                        if ("partial".equals(s.get("action").asText()) || !this.orderbooks.containsKey(instrumentId)) {
                            if (!"partial".equals(s.get("action").asText())) {
                                LOG.warn("Received Orderbook Update Before Partial");
                            }
                            OkCoinDepth okCoinDepth = this.mapper.treeToValue(data, OkCoinDepth.class);
                            okCoinOrderbook = new OkCoinOrderbook(okCoinDepth);
                            this.orderbooks.put(instrumentId, okCoinOrderbook);
                        } else {
                            okCoinOrderbook = this.orderbooks.get(instrumentId);
                            BigDecimal[][] bidLevels;
                            if (data.has("asks") && data.get("asks").size() > 0) {
                                bidLevels = this.mapper.treeToValue(data.get("asks"), BigDecimal[][].class);
                                okCoinOrderbook.updateLevels(bidLevels, Order.OrderType.ASK);
                            }

                            if (data.has("bids") && data.get("bids").size() > 0) {
                                bidLevels = this.mapper.treeToValue(data.get("bids"), BigDecimal[][].class);
                                okCoinOrderbook.updateLevels(bidLevels, Order.OrderType.BID);
                            }
                        }
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        format.setTimeZone(TimeZone.getTimeZone("UTC"));
                        long epoch = format.parse(data.get("timestamp").asText()).getTime();
                        return OkCoinAdapters.adaptOrderBook(okCoinOrderbook.toOkCoinDepth(epoch), currencyPair);
                    }
                }
                return null;
            });
        } else {
            throw new NotYetImplementedForExchangeException();
        }
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        throw new NotYetImplementedForExchangeException();
    }


    /**
     * #### spot ####
     * 4. ok_sub_spot_X_deals   订阅成交记录
     * <p>
     * #### future ####
     * 5. ok_sub_futureusd_X_trade_Y   订阅合约交易信息
     * 5. ok_sub_futureusd_X_trade_Y   Subscribe Contract Trade Record
     *
     * @param currencyPair Currency pair of the trades
     * @param args         the first arg {@link FuturesContract}
     * @return
     */
    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        throw new NotYetImplementedForExchangeException();
//        String channel = String.format("ok_sub_spot_%s_%s_deals", currencyPair.base.toString().toLowerCase(), currencyPair.counter.toString().toLowerCase());
//
//        if (args.length > 0) {
//            FuturesContract contract = (FuturesContract) args[0];
//            channel = String.format("ok_sub_future%s_%s_trade_%s", currencyPair.counter.toString().toLowerCase(), currencyPair.base.toString().toLowerCase(), contract.getName());
//        }
//
//        return service.subscribeChannel(channel)
//                .map(s -> {
//                    String[][] trades = mapper.treeToValue(s.get("data"), String[][].class);
//
//                    // I don't know how to parse this array of arrays in Jacson.
//                    OkCoinWebSocketTrade[] okCoinTrades = new OkCoinWebSocketTrade[trades.length];
//                    for (int i = 0; i < trades.length; ++i) {
//                        OkCoinWebSocketTrade okCoinWebSocketTrade = new OkCoinWebSocketTrade(trades[i]);
//                        okCoinTrades[i] = okCoinWebSocketTrade;
//                    }
//
//                    return OkCoinAdapters.adaptTrades(okCoinTrades, currencyPair);
//                }).flatMapIterable(Trades::getTrades);
    }
}
