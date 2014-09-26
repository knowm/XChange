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
package com.xeiam.xchange.huobi;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.huobi.dto.account.polling.HuobiAccountInfo;
import com.xeiam.xchange.huobi.dto.marketdata.HuobiFullTrade;
import com.xeiam.xchange.huobi.dto.marketdata.HuobiTicker;
import com.xeiam.xchange.huobi.dto.marketdata.HuobiTickerWrapper;
import com.xeiam.xchange.huobi.dto.trade.polling.HuobiOrder;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * Various adapters for converting from mtgox DTOs to XChange DTOs
 */
public final class HuobiAdapters {

    private static final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss Z");
    private static final String timezone = "+0800";

  /**
   * private Constructor
   */
  private HuobiAdapters() {

  }

  /**
   * Adapts a HitBtcAccountInfo to a AccountInfo
   * 
   * @param HuobiAccountInfo
   * @return
   */
  public static AccountInfo adaptAccountInfo(HuobiAccountInfo HuobiAccountInfo) {

    List<Wallet> wallets = new ArrayList<Wallet>();
    wallets.add(new Wallet(Currencies.BTC, HuobiAccountInfo.getAvailable_btc_display()));
    wallets.add(new Wallet(Currencies.CNY, HuobiAccountInfo.getAvailable_cny_display()));

    AccountInfo accountInfo = new AccountInfo(null, null, wallets);
    return accountInfo;
  }

    public static LimitOrder adaptDepth(CurrencyPair currencyPair, BigDecimal amount, BigDecimal price, String orderTypeString, String id, Date timestamp) {

        OrderType orderType = orderTypeString.equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;
        LimitOrder limitOrder = new LimitOrder(orderType, amount, currencyPair, id, timestamp, price);

        return limitOrder;
    }

    public static List<LimitOrder> adaptDepths(List<List<BigDecimal>> depths, CurrencyPair currencyPair, String orderType) {

        List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
        for (List<BigDecimal> depth : depths) {
            limitOrders.add(adaptDepth(currencyPair, depth.get(1), depth.get(0), orderType, "", null));
        }
        return limitOrders;
    }

    public static List<LimitOrder> adaptOrders(HuobiOrder[] huobiOpenOrders) {
    
        List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

        if (huobiOpenOrders != null) {
            for (HuobiOrder huobiOpenOrder : huobiOpenOrders) {

                OrderType orderType = huobiOpenOrder.getType() == 1 ? OrderType.BID : OrderType.ASK;
                LimitOrder limitOrder = new LimitOrder(orderType, huobiOpenOrder.getOrderAmount(),
                        CurrencyPair.BTC_CNY, huobiOpenOrder.getId(), new Date(huobiOpenOrder.getOrderTime() * 1000), huobiOpenOrder.getOrderPrice());

                limitOrders.add(limitOrder);
            }
        }
        return limitOrders;
    }

    public static HuobiOrder.Status adaptOrderStatus(HuobiOrder huobiOrder) {

        return HuobiOrder.Status.values()[huobiOrder.getStatus()];
    }

    public static Trade adaptTrade(HuobiFullTrade huobiTrade, CurrencyPair currencyPair) {
        OrderType type = huobiTrade.getType().equals("sell") ? OrderType.ASK : OrderType.BID;
        return new Trade(type, huobiTrade.getAmount(), currencyPair,
                huobiTrade.getPrice(), new Date(huobiTrade.getDate() * 1000), String.valueOf(huobiTrade.getTid()));
    }

  public static Trades adaptTrades(HuobiFullTrade[] huobiTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>();
      for (HuobiFullTrade huobiTrade : huobiTrades) {
          tradesList.add(adaptTrade(huobiTrade, currencyPair));
      }
    return new Trades(tradesList, TradeSortType.SortByTimestamp);
  }

  public static Ticker adaptTicker(HuobiTickerWrapper huobiTickerWrapper, CurrencyPair currencyPair) {

    HuobiTicker huobiTicker = huobiTickerWrapper.getHuobiTicker();

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair)
            .withLast(huobiTicker.getLast()).withBid(huobiTicker.getBuy()).withAsk(huobiTicker.getSell())
            .withHigh(huobiTicker.getHigh()).withLow(huobiTicker.getLow()).withVolume(huobiTicker.getVol())
            .withTimestamp(new Date(huobiTickerWrapper.getTime() * 1000)).build();

  }
}
