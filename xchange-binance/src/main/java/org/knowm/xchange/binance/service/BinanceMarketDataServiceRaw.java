package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.marketdata.BinanceAggTrades;
import org.knowm.xchange.binance.dto.marketdata.BinanceKline;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;
import org.knowm.xchange.binance.dto.marketdata.BinancePriceQuantity;
import org.knowm.xchange.binance.dto.marketdata.BinanceSymbolPrice;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker24h;
import org.knowm.xchange.binance.dto.marketdata.KlineInterval;
import org.knowm.xchange.binance.dto.meta.BinanceTime;

public class BinanceMarketDataServiceRaw extends BinanceBaseService {

    protected BinanceMarketDataServiceRaw(Exchange exchange) {
        super(exchange);
    }
    
    public void ping() throws IOException {
        binance.ping();
    }
    
    public Date time() throws IOException {
        BinanceTime time = binance.time();
        return time.getServerTime();
    }

    public BinanceOrderbook getBinanceOrderbook(String symbol, Integer limit) throws BinanceException, IOException {
        return binance.depth(symbol, limit);
    }
    
    public List<BinanceAggTrades> aggTrades(String symbol, Long fromId, Long startTime, Long endTime
            , Integer limit) throws BinanceException, IOException {
        return binance.aggTrades(symbol, fromId, startTime, endTime, limit);
    }
    
    public List<BinanceKline> klines(String symbol, KlineInterval interval, Integer limit, Long startTime, Long endTime)
            throws BinanceException, IOException {
        List<Object[]> raw = binance.klines(symbol, interval, limit, startTime, endTime);
        return raw.stream().map(obj -> new BinanceKline(obj)).collect(Collectors.toList());
    }
    
    public BinanceTicker24h ticker24h(String symbol) throws BinanceException, IOException {
        return binance.ticker24h(symbol);
    }
    
    public List<BinanceSymbolPrice> tickerAllPrices() throws BinanceException, IOException {
        return binance.tickerAllPrices();
    }
    
    public List<BinancePriceQuantity> tickerAllBookTickers() throws BinanceException, IOException {
        return binance.tickerAllBookTickers();
    }
}
