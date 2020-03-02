package info.bitrich.xchangestream.lgo.domain;

import info.bitrich.xchangestream.lgo.LgoAdapter;
import info.bitrich.xchangestream.lgo.dto.LgoTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trade;

import java.util.List;

import static java.util.stream.Collectors.*;

public class LgoGroupedTradeUpdate {

    private long lastBatchId;
    private CurrencyPair currencyPair;
    private List<Trade> trades;

    public LgoGroupedTradeUpdate(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    public void apply(long batchId, List<LgoTrade> trades) {
        this.lastBatchId = batchId;
        this.trades = trades.stream()
                .map(lgoTrade -> LgoAdapter.adaptTrade(currencyPair, lgoTrade))
                .collect(toList());
    }

    public long getLastBatchId() {
        return lastBatchId;
    }

    public List<Trade> getTrades() {
        return trades;
    }
}
