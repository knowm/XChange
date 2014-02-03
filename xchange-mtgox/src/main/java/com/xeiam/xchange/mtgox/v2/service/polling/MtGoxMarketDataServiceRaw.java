package com.xeiam.xchange.mtgox.v2.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.mtgox.v2.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v2.MtGoxV2;
import com.xeiam.xchange.mtgox.v2.dto.MtGoxException;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxDepthWrapper;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxTicker;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxTickerWrapper;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxTradesWrapper;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.utils.Assert;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class MtGoxMarketDataServiceRaw extends BasePollingExchangeService {
    protected final MtGoxV2 mtGoxV2;

    /**
     * Initialize common properties from the exchange specification
     *
     * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
     */
    protected MtGoxMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {
        super(exchangeSpecification);

        Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
        this.mtGoxV2 = RestProxyFactory.createProxy(MtGoxV2.class, exchangeSpecification.getSslUri());
    }

    public MtGoxTicker getMtGoxTicker(String tradableIdentifier, String currency) throws IOException {
        try {
            // Request data
            MtGoxTickerWrapper mtGoxTickerWrapper = mtGoxV2.getTicker(tradableIdentifier, currency);

            if (mtGoxTickerWrapper.getResult().equals("success")) {
                // Adapt to XChange DTOs
                return mtGoxTickerWrapper.getMtGoxTicker();
            } else if (mtGoxTickerWrapper.getResult().equals("error")) {
                throw new ExchangeException("Error calling getTicker(): " + mtGoxTickerWrapper.getError());
            } else {
                throw new ExchangeException("Error calling getTicker(): Unexpected result!");
            }
        } catch (MtGoxException e) {
            throw new ExchangeException("Error calling getTicker(): " + e.getError(), e);
        }
    }

    public MtGoxDepthWrapper getMtGoxOrderBook(String tradableIdentifier, String currency, Object[] args) throws IOException {
        try {
            // Request data
            MtGoxDepthWrapper mtGoxDepthWrapper = null;
            if (args.length > 0) {
                if (args[0] instanceof PollingMarketDataService.OrderBookType) {
                    if (PollingMarketDataService.OrderBookType.FULL.equals(args[0])) {
                        mtGoxDepthWrapper = mtGoxV2.getFullDepth(tradableIdentifier, currency);
                    } else {
                        mtGoxDepthWrapper = mtGoxV2.getPartialDepth(tradableIdentifier, currency);
                    }
                } else {
                    throw new ExchangeException("Orderbook size argument must be OrderBookType enum!");
                }
            } else { // default to full orderbook
                mtGoxDepthWrapper = mtGoxV2.getFullDepth(tradableIdentifier, currency);
            }
            if (mtGoxDepthWrapper.getResult().equals("success")) {
                return mtGoxDepthWrapper;
            } else if (mtGoxDepthWrapper.getResult().equals("error")) {
                throw new ExchangeException("Error calling getFullOrderBook(): " + mtGoxDepthWrapper.getError());
            } else {
                throw new ExchangeException("Error calling getFullOrderBook(): Unexpected result!");
            }
        } catch (MtGoxException e) {
            throw new ExchangeException("Error calling getFullOrderBook(): " + e.getError(), e);
        }
    }

    public Trades getMtGoxTrades(String tradableIdentifier, String currency, Object[] args) throws IOException {
        try {
            MtGoxTradesWrapper mtGoxTradeWrapper = null;

            if (args.length > 0) {
                Long sinceTimeStamp = (Long) args[0];
                // Request data
                mtGoxTradeWrapper = mtGoxV2.getTrades(tradableIdentifier, currency, sinceTimeStamp);
            } else {
                // Request data
                mtGoxTradeWrapper = mtGoxV2.getTrades(tradableIdentifier, currency);
            }

            if (mtGoxTradeWrapper.getResult().equals("success")) {
                return MtGoxAdapters.adaptTrades(mtGoxTradeWrapper.getMtGoxTrades());
            } else if (mtGoxTradeWrapper.getResult().equals("error")) {
                throw new ExchangeException("Error calling getTrades(): " + mtGoxTradeWrapper.getError());
            } else {
                throw new ExchangeException("Error calling getTrades(): Unexpected result!");
            }
        } catch (MtGoxException e) {
            throw new ExchangeException("Error calling getTrades(): " + e.getError(), e);
        }
    }
}
