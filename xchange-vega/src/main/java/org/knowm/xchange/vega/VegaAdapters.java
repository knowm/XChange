package org.knowm.xchange.vega;

import io.vegaprotocol.vega.Markets;
import io.vegaprotocol.vega.Vega;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VegaAdapters {

    public static ExchangeMetaData adaptMetaData(List<CurrencyPair> currencyPairs) {
        Map<CurrencyPair, CurrencyPairMetaData> currencyPairMap = new HashMap<>(currencyPairs.size());

        for (CurrencyPair pair : currencyPairs) {
            currencyPairMap.put(pair, null);
        }

        return new ExchangeMetaData(
                currencyPairMap,
                Collections.<Currency, CurrencyMetaData>emptyMap(),
                null,
                null,
                null
        );
    }

    public static CurrencyPair adaptMarketToCurrencyPair(Markets.Market market) {
        String base = null;
        String quote = null;

        for (String tag : market.getTradableInstrument().getInstrument().getMetadata().getTagsList()) {
            if (tag.startsWith("base:")) {
                base = tag.replace("base:", "");
            } else if (tag.startsWith("ticker:")) {
                base = tag.replace("ticker:", "");
            } else if (tag.startsWith("quote:")) {
                quote = tag.replace("quote:", "");
            }
        }

        return new CurrencyPair(base, quote);
    }

    public static Trade adaptTrade(Vega.Trade trade, CurrencyPair currencyPair) {
        Order.OrderType orderType = null;

        if (trade.getAggressor() == Vega.Side.SIDE_BUY) {
            orderType = Order.OrderType.BID;
        } else if (trade.getAggressor() == Vega.Side.SIDE_SELL) {
            orderType = Order.OrderType.ASK;
        }

        return new Trade(
                orderType,
                BigDecimal.valueOf(trade.getSize()),
                currencyPair,
                BigDecimal.valueOf(trade.getPrice()),
                DateUtils.fromUnixTime(trade.getTimestamp()),
                trade.getId(),
                trade.getSellOrder(),
                trade.getBuyOrder()
        );
    }
}
