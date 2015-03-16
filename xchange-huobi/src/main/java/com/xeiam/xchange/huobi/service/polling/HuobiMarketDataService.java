/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
/**

 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.huobi.service.polling;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.huobi.HuobiAdapters;
import com.xeiam.xchange.huobi.dto.marketdata.HuobiDepthWrapper;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.huobi.dto.marketdata.HuobiFullTrade;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class HuobiMarketDataService extends HuobiMarketDataServiceRaw implements PollingMarketDataService {

    public HuobiMarketDataService(Exchange exchange) {

        super(exchange);
    }

    @Override
    public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

        return HuobiAdapters.adaptTicker(getHuobiTicker(currencyPair), currencyPair);
    }

    @Override
    public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {


        HuobiDepthWrapper huobiOrderBook = getHuobiOrderBook(currencyPair);

        List<LimitOrder> asks = HuobiAdapters.adaptDepths(huobiOrderBook.getAsks(), currencyPair, "ask");
        List<LimitOrder> bids = HuobiAdapters.adaptDepths(huobiOrderBook.getBids(), currencyPair, "bid");

        Date date = new Date();
        return new OrderBook(date, asks, bids);
    }

    @Override
    public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

        Long since = null;
        try {
            since = (Long) args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            // ignore, can happen if no arg given.
        }

        HuobiFullTrade[] huobiTrades = getHuobiTrades(currencyPair, since);

        return HuobiAdapters.adaptTrades(huobiTrades, currencyPair);
    }

}
