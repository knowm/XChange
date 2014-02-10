package com.xeiam.xchange.cexio.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.cexio.CexIOAuthenticated;
import com.xeiam.xchange.cexio.CexIOUtils;
import com.xeiam.xchange.cexio.dto.trade.CexIOOrder;
import com.xeiam.xchange.cexio.service.CexIODigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.utils.DateUtils;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.xeiam.xchange.dto.Order.OrderType.BID;

/**
 * Author: brox
 * Since:  2/6/14
 */

public class CexIOTradeService extends BasePollingExchangeService implements PollingTradeService {

  private final CexIOAuthenticated exchange;
  private ParamsDigest signatureCreator;

  /**
   * Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public CexIOTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.exchange = RestProxyFactory.createProxy(CexIOAuthenticated.class, exchangeSpecification.getSslUri());
    signatureCreator = CexIODigest.createInstance(exchangeSpecification.getSecretKey(), exchangeSpecification.getUserName(), exchangeSpecification.getApiKey());
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (CurrencyPair p : CexIOUtils.CURRENCY_PAIRS) {
      String tradableIdentifier = p.baseCurrency;
      String transactionCurrency = p.counterCurrency;
      CexIOOrder[] openOrders = exchange.getOpenOrders(tradableIdentifier, transactionCurrency, exchangeSpecification.getApiKey(), signatureCreator, CexIOUtils.nextNonce());

      for (CexIOOrder o : openOrders) {
        Order.OrderType orderType = o.getType() == CexIOOrder.Type.buy ? Order.OrderType.BID : Order.OrderType.ASK;
        String id = Integer.toString(o.getId());
        BigMoney price = BigMoney.of(CurrencyUnit.of(transactionCurrency), o.getPrice());
        limitOrders.add(new LimitOrder(orderType, o.getAmount(), tradableIdentifier, transactionCurrency, id, DateUtils.fromMillisUtc(o.getTime() * 1000L), price));
      }
    }
    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    CexIOOrder order = exchange.placeOrder(limitOrder.getTradableIdentifier(), limitOrder.getTransactionCurrency(), exchangeSpecification.getApiKey(), signatureCreator, CexIOUtils.nextNonce(), (limitOrder.getType() == BID ? CexIOOrder.Type.buy : CexIOOrder.Type.sell), limitOrder.getLimitPrice().getAmount(), limitOrder.getTradableAmount());
    if (order.getErrorMessage() != null) {
      throw new ExchangeException(order.getErrorMessage());
    }

    return Integer.toString(order.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return exchange.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, CexIOUtils.nextNonce(), Integer.parseInt(orderId)).equals(true);
  }

  @Override
  public Trades getTradeHistory(Object... arguments) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotAvailableFromExchangeException();
  }

}
