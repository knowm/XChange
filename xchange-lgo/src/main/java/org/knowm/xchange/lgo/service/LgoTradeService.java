package org.knowm.xchange.lgo.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.lgo.LgoAdapters;
import org.knowm.xchange.lgo.LgoEnv;
import org.knowm.xchange.lgo.LgoErrorAdapter;
import org.knowm.xchange.lgo.LgoExchange;
import org.knowm.xchange.lgo.dto.LgoException;
import org.knowm.xchange.lgo.dto.WithCursor;
import org.knowm.xchange.lgo.dto.key.LgoKey;
import org.knowm.xchange.lgo.dto.order.LgoEncryptedOrder;
import org.knowm.xchange.lgo.dto.order.LgoOrderSignature;
import org.knowm.xchange.lgo.dto.order.LgoPlaceOrder;
import org.knowm.xchange.lgo.dto.order.LgoUnencryptedOrder;
import org.knowm.xchange.lgo.dto.product.LgoProduct;
import org.knowm.xchange.lgo.dto.product.LgoProductCurrency;
import org.knowm.xchange.lgo.dto.trade.LgoUserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamNextPageCursor;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;

public class LgoTradeService extends LgoTradeServiceRaw implements TradeService {

  private final LgoKeyService keyService;
  private final boolean shouldEncryptOrders;

  public LgoTradeService(LgoExchange exchange, LgoKeyService keyService) {
    super(exchange);
    this.keyService = keyService;
    shouldEncryptOrders =
        (boolean)
            exchange
                .getExchangeSpecification()
                .getExchangeSpecificParameters()
                .getOrDefault(LgoEnv.SHOULD_ENCRYPT_ORDERS, false);
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
    if (limitOrder
            .getLimitPrice()
            .remainder(product.getQuote().getIncrement())
            .compareTo(BigDecimal.ZERO)
        != 0) {
      throw new IllegalArgumentException("Invalid price increment");
    }
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
    if (shouldEncryptOrders) {
      return placeEncryptedLimitOrder(limitOrder);
    }
    return placeUnencryptedLimitOrder(limitOrder);
  }

  private String placeEncryptedLimitOrder(LimitOrder limitOrder) throws IOException {
    LgoPlaceOrder lgoOrder = LgoAdapters.adaptLimitOrder(limitOrder);
    return placeEncryptedOrder(lgoOrder);
  }

  private String placeUnencryptedLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      LgoUnencryptedOrder lgoOrder = LgoAdapters.adaptUnencryptedLimitOrder(limitOrder);
      return placeLgoUnencryptedOrder(lgoOrder);
    } catch (LgoException e) {
      throw LgoErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    if (shouldEncryptOrders) {
      return placeEncryptedMarketOrder(marketOrder);
    }
    return placeUnencryptedMarketOrder(marketOrder);
  }

  private String placeEncryptedMarketOrder(MarketOrder marketOrder) throws IOException {
    LgoPlaceOrder lgoOrder = LgoAdapters.adaptEncryptedMarketOrder(marketOrder);
    return placeEncryptedOrder(lgoOrder);
  }

  private String placeUnencryptedMarketOrder(MarketOrder marketOrder) throws IOException {
    try {
      LgoUnencryptedOrder lgoOrder = LgoAdapters.adaptUnencryptedMarketOrder(marketOrder);
      return placeLgoUnencryptedOrder(lgoOrder);
    } catch (LgoException e) {
      throw LgoErrorAdapter.adapt(e);
    }
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (!(orderParams instanceof CancelOrderByIdParams)) {
      return false;
    }
    CancelOrderByIdParams cancelParams = (CancelOrderByIdParams) orderParams;
    return cancelOrder(cancelParams.getOrderId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    if (shouldEncryptOrders) {
      placeEncryptedCancelOrder(orderId);
      return true;
    }
    return placeUnencryptedCancelOrder(orderId);
  }

  private boolean placeUnencryptedCancelOrder(String orderId) throws IOException {
    try {
      placeLgoUnencryptedCancelOrder(orderId);
      return true;
    } catch (LgoException e) {
      throw LgoErrorAdapter.adapt(e);
    }
  }

  /** Place a cancellation order encrypting it's content. */
  private String placeEncryptedCancelOrder(String orderId) throws IOException {
    LgoPlaceOrder lgoOrder = LgoAdapters.adaptEncryptedCancelOrder(orderId, new Date());
    return placeEncryptedOrder(lgoOrder);
  }

  private String placeEncryptedOrder(LgoPlaceOrder lgoOrder) throws IOException {
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
}
