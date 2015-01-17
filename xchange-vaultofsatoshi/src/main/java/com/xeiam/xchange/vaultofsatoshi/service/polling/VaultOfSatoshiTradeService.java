package com.xeiam.xchange.vaultofsatoshi.service.polling;

import static com.xeiam.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.TradeServiceHelper;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParams;
import com.xeiam.xchange.utils.DateUtils;
import com.xeiam.xchange.vaultofsatoshi.VaultOfSatoshiAdapters;
import com.xeiam.xchange.vaultofsatoshi.dto.trade.VosTradeOrder;

public class VaultOfSatoshiTradeService extends VaultOfSatoshiTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public VaultOfSatoshiTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    VosTradeOrder[] openOrders = getVaultOfSatoshiOpenOrders(100);

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (VosTradeOrder vosOrder : openOrders) {
      OrderType orderType = vosOrder.getType().equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
      String id = Integer.toString(vosOrder.getOrder_id());
      BigDecimal price = vosOrder.getPrice().getValue();
      CurrencyPair currPair = new CurrencyPair(vosOrder.getOrder_currency(), vosOrder.getPayment_currency());

      limitOrders.add(new LimitOrder(orderType, vosOrder.getUnits().getValue(), currPair, id, DateUtils.fromMillisUtc(vosOrder.getOrder_date() / 1000L), price));
    }

    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    int vosOrderId;
    if (limitOrder.getType() == BID) {
      vosOrderId = buyVaultOfSatoshiOrder(limitOrder.getCurrencyPair(), limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
    }
    else {
      vosOrderId = sellVaultOfSatoshiOrder(limitOrder.getCurrencyPair(), limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
    }

    return Integer.toString(vosOrderId);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    return cancelVaultOfSatoshiOrder(Integer.parseInt(orderId));
  }

  @Override
  public UserTrades getTradeHistory(Object... args) throws IOException {

    int numberOfTransactions = 100;
    if (args.length > 0) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Integer)) {
        throw new ExchangeException("Argument must be of type Integer!");
      }
      else {
        numberOfTransactions = (Integer) args[0];
      }
    }

    return VaultOfSatoshiAdapters.adaptTradeHistory(getVaultOfSatoshiUserTransactions(numberOfTransactions));
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public com.xeiam.xchange.service.polling.trade.TradeHistoryParams createTradeHistoryParams() {

    return null;
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
