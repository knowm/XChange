package org.knowm.xchange.blockchain.service;

import org.knowm.xchange.blockchain.BlockchainAdapters;
import org.knowm.xchange.blockchain.BlockchainAuthenticated;
import org.knowm.xchange.blockchain.BlockchainErrorAdapter;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.dto.BlockchainException;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

import java.io.IOException;
import java.util.List;

import static org.knowm.xchange.blockchain.BlockchainConstants.NOT_IMPLEMENTED_YET;

public class BlockchainMarketDataService extends BlockchainMarketDataServiceRaw implements MarketDataService {

    public BlockchainMarketDataService(BlockchainExchange exchange, BlockchainAuthenticated blockchainApi, ResilienceRegistries resilienceRegistries) {
        super(exchange, blockchainApi, resilienceRegistries);
    }

    @Override
    public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
        throw new NotYetImplementedForExchangeException(NOT_IMPLEMENTED_YET);
    }

    @Override
    public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
        throw new NotYetImplementedForExchangeException(NOT_IMPLEMENTED_YET);
    }

    @Override
    public List<Ticker> getTickers(Params params) {
        throw new NotYetImplementedForExchangeException(NOT_IMPLEMENTED_YET);
    }

    @Override
    public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
        try {
            return BlockchainAdapters.toOrderBook(this.getOrderBookL3(currencyPair));
        } catch (BlockchainException e) {
            throw BlockchainErrorAdapter.adapt(e);
        }
    }

    @Override
    public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
        try {
            return BlockchainAdapters.toOrderBook(this.getOrderBookL3(BlockchainAdapters.toCurrencyPair(instrument)));
        } catch (BlockchainException e) {
            throw BlockchainErrorAdapter.adapt(e);
        }
    }

    @Override
    public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
        try {
            Long startTime = null;
            Long endTime = null;
            Integer limit = null;
            String symbol = BlockchainAdapters.toSymbol(currencyPair);

            if (args != null) {
                switch (args.length) {
                    case 2:
                        if (args[1] != null && args[1] instanceof Long) {
                            endTime = (Long) args[1];
                        }
                    case 1:
                        if (args[0] != null && args[0] instanceof Long) {
                            startTime = (Long) args[0];
                        }
                }
            }

            return BlockchainAdapters.toTrades(
                    this.getExchangeTrades(symbol, startTime, endTime, limit),
                    currencyPair
            );
        }catch (BlockchainException e){
            throw BlockchainErrorAdapter.adapt(e);
        }
    }

   @Override
    public Trades getTrades(Instrument instrument, Object... args) throws IOException {
        CurrencyPair currencyPair = BlockchainAdapters.toCurrencyPair(instrument);
        return getTrades(currencyPair,args);
    }

}
