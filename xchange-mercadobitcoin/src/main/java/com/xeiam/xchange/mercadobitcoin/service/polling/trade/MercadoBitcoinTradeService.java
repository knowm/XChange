package com.xeiam.xchange.mercadobitcoin.service.polling.trade;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.TradeServiceHelper;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.mercadobitcoin.MercadoBitcoinAdapters;
import com.xeiam.xchange.mercadobitcoin.MercadoBitcoinUtils;
import com.xeiam.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;
import com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinPlaceLimitOrderResult;
import com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinUserOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.DefaultTradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Felipe Micaroni Lalli
 */
public class MercadoBitcoinTradeService extends MercadoBitcoinTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public MercadoBitcoinTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinUserOrders> openOrdersBitcoinResult = getMercadoBitcoinUserOrders("btc_brl", null, "active", null, null, null, null);
    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinUserOrders> openOrdersLitecoinResult = getMercadoBitcoinUserOrders("ltc_brl", null, "active", null, null, null, null);

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    limitOrders.addAll(MercadoBitcoinAdapters.adaptOrders(CurrencyPair.BTC_BRL, openOrdersBitcoinResult));
    limitOrders.addAll(MercadoBitcoinAdapters.adaptOrders(new CurrencyPair(Currencies.LTC, Currencies.BRL), openOrdersLitecoinResult));

    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * The result is not the pure order id. It is a composition with
   * the currency pair and the order id (the same format used as parameter of {@link #cancelOrder}).
   * Please see {@link com.xeiam.xchange.mercadobitcoin.MercadoBitcoinUtils#makeMercadoBitcoinOrderId}.
   */
  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    String pair;

    if (limitOrder.getCurrencyPair().equals(CurrencyPair.BTC_BRL)) {
      pair = "btc_brl";
    }
    else if (limitOrder.getCurrencyPair().equals(new CurrencyPair(Currencies.LTC, Currencies.BRL))) {
      pair = "ltc_brl";
    }
    else {
      throw new NotAvailableFromExchangeException();
    }

    String type;

    if (limitOrder.getType() == Order.OrderType.BID) {
      type = "buy";
    }
    else {
      type = "sell";
    }

    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinPlaceLimitOrderResult> newOrderResult = mercadoBitcoinPlaceLimitOrder(pair, type, limitOrder.getTradableAmount(), limitOrder.getLimitPrice());

    return MercadoBitcoinUtils.makeMercadoBitcoinOrderId(limitOrder.getCurrencyPair(), newOrderResult.getTheReturn().keySet().iterator().next());
  }

  /**
   * The ID is composed by the currency pair and the id number separated by colon, like: <code>btc_brl:3498</code> Please see and use
   * {@link com.xeiam.xchange.mercadobitcoin.MercadoBitcoinUtils#makeMercadoBitcoinOrderId}.
   */
  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    String[] pairAndId = orderId.split(":");

    mercadoBitcoinCancelOrder(pairAndId[0], pairAndId[1]);

    return true;
  }

  @Override
  public UserTrades getTradeHistory(Object... args) throws IOException {

    // TODO: see #getTradeHistory(TradeHistoryParams params)
    throw new NotYetImplementedForExchangeException();
  }

  /**
   * Required parameter types: {@link com.xeiam.xchange.service.polling.trade.TradeHistoryParamPaging#getPageLength()}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    // TODO: use getMercadoBitcoinUserOrders of MercadoBitcoinTradeServiceRaw and get order of all status (active, completed and canceled) and look for getOperations()
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new DefaultTradeHistoryParamPaging(1000); // the API limit of Mercado Bitcoin is 1000
  }

  /**
   * Fetch the {@link com.xeiam.xchange.dto.marketdata.TradeServiceHelper} from the exchange.
   *
   * @return Map of currency pairs to their corresponding metadata.
   * @see com.xeiam.xchange.dto.marketdata.TradeServiceHelper
   */
  @Override public Map<CurrencyPair, ? extends TradeServiceHelper> getTradeServiceHelperMap() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }

}
