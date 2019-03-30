package org.knowm.xchange.globitex.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.globitex.GlobitexAdapters;
import org.knowm.xchange.globitex.dto.trade.GlobitexActiveOrders;
import org.knowm.xchange.globitex.dto.trade.GlobitexUserTrades;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import si.mazi.rescu.HttpStatusIOException;

public class GlobitexTradeServiceRaw extends GlobitexBaseService {

  public GlobitexTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public GlobitexUserTrades getGlobitexUserTrades(TradeHistoryParamsAll params) throws IOException {
    try {

      return globitex.getTradeHistory(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory(),
            signatureCreator,
            "ts",
            0,
            params.getLimit(),
            GlobitexAdapters.adaptCurrencyPairToGlobitexSymbol(params.getCurrencyPair()),
            exchange.getExchangeSpecification().getUserName()
      );

    } catch (HttpStatusIOException e) {
        throw new ExchangeException(e.getHttpBody());
    }
  }

  public GlobitexActiveOrders getGlobitexActiveOrders() throws IOException{
    try{
      return globitex.getActiveOrders(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory(),
              signatureCreator,
              null,null,
              exchange.getExchangeSpecification().getUserName()
      );
    }catch (HttpStatusIOException e){
        throw new ExchangeException(e.getHttpBody());
    }
  }

  public GlobitexActiveOrders getGlobitexActiveOrders(OpenOrdersParamCurrencyPair params) throws IOException{
    try{

      return globitex.getActiveOrders(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory(),
              signatureCreator,
              GlobitexAdapters.adaptCurrencyPairToGlobitexSymbol(params.getCurrencyPair()),
              null,
              exchange.getExchangeSpecification().getUserName()
      );
    }catch (HttpStatusIOException e){
      throw new ExchangeException(e.getHttpBody());
    }
  }
}
