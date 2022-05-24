package org.knowm.xchange.blockchain.service;

import org.knowm.xchange.blockchain.BlockchainAdapters;
import org.knowm.xchange.blockchain.BlockchainAuthenticated;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.Blockchain;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BlockchainMarketDataService extends BlockchainMarketDataServiceRaw implements MarketDataService {

    public BlockchainMarketDataService(BlockchainExchange exchange, BlockchainAuthenticated blockchainApi, ResilienceRegistries resilienceRegistries) {
        super(exchange, blockchainApi, resilienceRegistries);
    }

    // ===== TICKERS =====
    @Override
    public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
        return BlockchainAdapters.toTicker(
                this.getTicker(currencyPair)
        );
    }

    @Override
    public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
        CurrencyPair currencyPair = BlockchainAdapters.toCurrencyPair(instrument);
        return BlockchainAdapters.toTicker(
                this.getTicker(currencyPair)
        );
    }

    @Override
    public List<Ticker> getTickers(Params params) throws IOException {
        return this.getTickers().stream()
                .map(BlockchainAdapters::toTicker)
                .collect(Collectors.toList());
    }

    // ===== ORDER BOOKS =====
    @Override
    public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
        return BlockchainAdapters.toOrderBook(this.getOrderBookL3(currencyPair));
    }

    @Override
    public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
        return BlockchainAdapters.toOrderBook(this.getOrderBookL3(BlockchainAdapters.toCurrencyPair(instrument)));
    }

    // ===== TRADES =====
    @Override
    public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
        return BlockchainAdapters.toTrades(
                this.getExchangeTrades(currencyPair),
                currencyPair
        );
    }

    @Override
    public Trades getTrades(Instrument instrument, Object... args) throws IOException {
        CurrencyPair currencyPair = BlockchainAdapters.toCurrencyPair(instrument);
        return BlockchainAdapters.toTrades(
                this.getExchangeTrades(currencyPair),
                currencyPair
        );
    }
}
