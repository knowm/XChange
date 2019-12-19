package org.knowm.xchange.lgo.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.lgo.*;
import org.knowm.xchange.lgo.dto.*;
import org.knowm.xchange.lgo.dto.currency.LgoCurrency;
import org.knowm.xchange.lgo.dto.key.LgoKey;
import org.knowm.xchange.lgo.dto.order.*;
import org.knowm.xchange.lgo.dto.product.*;
import org.knowm.xchange.lgo.dto.trade.LgoUserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;

import java.io.IOException;
import java.util.*;

public class LgoTradeService extends LgoTradeServiceRaw implements TradeService {

  private final Random random;
  private final LgoKeyService keyService;

  public LgoTradeService(LgoExchange exchange, LgoKeyService keyService) {
    super(exchange);
    this.keyService = keyService;
    random = new Random();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    CurrencyPair productId = getProductId(params);
    Integer maxResults = getMaxResults(params);
    String page = getPage(params);
    TradeHistoryParamsSorted.Order sort = getSort(params);
    try {
      WithCursor<LgoUserTrades> lgoTrades = super.getLastTrades(productId, maxResults, page, sort);
      return LgoAdapters.adaptUserTrades(lgoTrades);
    } catch (LgoException e) {
      throw LgoErrorAdapter.adapt(e);
    }
  }

  private TradeHistoryParamsSorted.Order getSort(TradeHistoryParams params) {
    if (!(params instanceof TradeHistoryParamsSorted)) {
      return TradeHistoryParamsSorted.Order.desc;
    }
    return ((TradeHistoryParamsSorted) params).getOrder();
  }

  private String getPage(TradeHistoryParams params) {
    if (!(params instanceof TradeHistoryParamNextPageCursor)) {
      return null;
    }
    return ((TradeHistoryParamNextPageCursor) params).getNextPageCursor();
  }

  private CurrencyPair getProductId(TradeHistoryParams params) {
    if (!(params instanceof TradeHistoryParamCurrencyPair)) {
      return null;
    }
    return ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
  }

  private int getMaxResults(TradeHistoryParams params) {
    if (!(params instanceof TradeHistoryParamLimit)) {
      return 100;
    }
    return ((TradeHistoryParamLimit) params).getLimit();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new LgoTradeHistoryParams();
  }

  @Override
  public void verifyOrder(MarketOrder marketOrder) {
    LgoProduct product = getProduct(marketOrder.getCurrencyPair());
    LgoProductCurrency currencyToCheck =
        OrderType.BID.equals(marketOrder.getType()) ? product.getQuote() : product.getBase();
    if (currencyToCheck.getLimits().getMin().compareTo(marketOrder.getRemainingAmount()) > 0) {
      throw new IllegalArgumentException("Quantity to low");
    }
    if (currencyToCheck.getLimits().getMax().compareTo(marketOrder.getRemainingAmount()) < 0) {
      throw new IllegalArgumentException("Quantity to high");
    }
  }

  @Override
  public void verifyOrder(LimitOrder limitOrder) {
    super.verifyOrder(limitOrder);
    LgoProduct product = getProduct(limitOrder.getCurrencyPair());
    if (product.getBase().getLimits().getMax().compareTo(limitOrder.getOriginalAmount()) < 0) {
      throw new IllegalArgumentException("Order amount more than maximum");
    }
    if (product.getQuote().getLimits().getMin().compareTo(limitOrder.getLimitPrice()) > 0) {
      throw new IllegalArgumentException("Order price to low");
    }
    if (product.getQuote().getLimits().getMax().compareTo(limitOrder.getLimitPrice()) < 0) {
      throw new IllegalArgumentException("Order price to high");
    }
    LgoCurrency base = getCurrency(limitOrder.getCurrencyPair().counter);
    if (limitOrder.getLimitPrice().setScale(base.getDecimals()).unscaledValue().longValue()
            % product
                .getQuote()
                .getIncrement()
                .setScale(base.getDecimals())
                .unscaledValue()
                .intValue()
        != 0) {
      throw new IllegalArgumentException("Invalid price increment");
    }
  }

  private LgoCurrency getCurrency(org.knowm.xchange.currency.Currency counter) {
    for (LgoCurrency currency : exchange.getCurrencies().getCurrencies()) {
      if (currency.getCode().equalsIgnoreCase(counter.getCurrencyCode())) {
        return currency;
      }
    }
    throw new IllegalArgumentException("Currency not supported " + counter.toString());
  }

  private LgoProduct getProduct(CurrencyPair currencyPair) {
    for (LgoProduct product : exchange.getProducts().getProducts()) {
      if (product.getBase().getId().equalsIgnoreCase(currencyPair.base.getCurrencyCode())
          && product.getQuote().getId().equalsIgnoreCase(currencyPair.counter.getCurrencyCode())) {
        return product;
      }
    }
    throw new IllegalArgumentException("Product not supported " + currencyPair.toString());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    LgoPlaceOrder lgoOrder = LgoAdapters.adaptLimitOrder(limitOrder);
    return placeOrder(lgoOrder);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    LgoPlaceOrder lgoOrder = LgoAdapters.adaptMarketOrder(marketOrder);
    return placeOrder(lgoOrder);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    LgoPlaceOrder lgoOrder = LgoAdapters.adaptCancelOrder(orderId, new Date());
    placeOrder(lgoOrder);
    return true;
  }

  private String placeOrder(LgoPlaceOrder lgoOrder) throws IOException {
    try {
      LgoKey lgoKey = keyService.selectKey();
      Long ref = exchange.getNonceFactory().createValue();
      String encryptedOrder = CryptoUtils.encryptOrder(lgoKey, lgoOrder);
      LgoOrderSignature signature = exchange.getSignatureService().signOrder(encryptedOrder);
      LgoEncryptedOrder lgoEncryptedOrder =
          new LgoEncryptedOrder(lgoKey.getId(), encryptedOrder, signature, ref);
      return placeLgoEncryptedOrder(lgoEncryptedOrder);
    } catch (LgoException e) {
      throw LgoErrorAdapter.adapt(e);
    }
  }

  /** Place a limit order without encrypting it's content. */
  public String placeUnencryptedLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      LgoUnencryptedOrder lgoOrder = LgoAdapters.adaptUnencryptedLimitOrder(limitOrder);
      return placeLgoUnencryptedOrder(lgoOrder);
    } catch (LgoException e) {
      throw LgoErrorAdapter.adapt(e);
    }
  }

  /** Place a market order without encrypting it's content. */
  public String placeUnencryptedMarketOrder(MarketOrder marketOrder) throws IOException {
    try {
      LgoUnencryptedOrder lgoOrder = LgoAdapters.adaptUnencryptedMarketOrder(marketOrder);
      return placeLgoUnencryptedOrder(lgoOrder);
    } catch (LgoException e) {
      throw LgoErrorAdapter.adapt(e);
    }
  }

  /** Place a cancellation order without encrypting it's content. */
  public String placeUnencryptedCancelOrder(String orderId) throws IOException {
    try {
      return placeLgoUnencryptedCancelOrder(orderId);
    } catch (LgoException e) {
      throw LgoErrorAdapter.adapt(e);
    }
  }
}
