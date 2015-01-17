package com.xeiam.xchange.bitcointoyou.service.polling.trade;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.bitcointoyou.BitcoinToYouAdapters;
import com.xeiam.xchange.bitcointoyou.dto.BitcoinToYouBaseTradeApiResult;
import com.xeiam.xchange.bitcointoyou.dto.trade.BitcoinToYouOrder;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.TradeServiceHelper;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.DefaultTradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParams;

/**
 * @author Felipe Micaroni Lalli
 */
public class BitcoinToYouTradeService extends BitcoinToYouTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BitcoinToYouTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder[]> openOrdersBitcoinResult = getBitcoinToYouUserOrders("OPEN");

    return new OpenOrders(BitcoinToYouAdapters.adaptOrders(openOrdersBitcoinResult));
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    BitcoinToYouOrder[] placeLimitOrderResult = placeBitcoinToYouLimitOrder(limitOrder);

    return String.valueOf(placeLimitOrderResult[0].getId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    cancelBitcoinToYouLimitOrder(orderId);

    return true;
  }

  @Override
  public UserTrades getTradeHistory(Object... args) throws IOException {

    // TODO: see #getTradeHistory(TradeHistoryParams params)
    throw new NotAvailableFromExchangeException();
  }

  /**
   * Required parameter types: {@link com.xeiam.xchange.service.polling.trade.TradeHistoryParamPaging#getPageLength()}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new DefaultTradeHistoryParamPaging(Integer.MAX_VALUE);
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
