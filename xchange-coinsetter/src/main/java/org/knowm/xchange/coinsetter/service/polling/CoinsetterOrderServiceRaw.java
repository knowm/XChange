package org.knowm.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.util.UUID;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.CoinsetterException;
import org.knowm.xchange.coinsetter.dto.order.request.CoinsetterOrderRequest;
import org.knowm.xchange.coinsetter.dto.order.response.CoinsetterOrder;
import org.knowm.xchange.coinsetter.dto.order.response.CoinsetterOrderList;
import org.knowm.xchange.coinsetter.dto.order.response.CoinsetterOrderResponse;

import si.mazi.rescu.RestProxyFactory;

/**
 * Order raw service.
 */
public class CoinsetterOrderServiceRaw extends CoinsetterBasePollingService {

  private final org.knowm.xchange.coinsetter.rs.CoinsetterOrder order;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinsetterOrderServiceRaw(Exchange exchange) {

    super(exchange);

    String baseUrl = exchange.getExchangeSpecification().getSslUri();
    order = RestProxyFactory.createProxy(org.knowm.xchange.coinsetter.rs.CoinsetterOrder.class, baseUrl);
  }

  public CoinsetterOrderResponse add(UUID clientSessionId, CoinsetterOrderRequest request) throws CoinsetterException, IOException {

    return order.add(clientSessionId, request);
  }

  public CoinsetterOrder get(UUID clientSessionId, UUID orderUuid) throws CoinsetterException, IOException {

    return order.get(clientSessionId, orderUuid);
  }

  public CoinsetterOrderList list(UUID clientSessionId, UUID accountUuid, String view) throws CoinsetterException, IOException {

    return order.list(clientSessionId, accountUuid, view);
  }

  public CoinsetterOrderResponse cancel(UUID clientSessionId, UUID orderUuid) throws CoinsetterException, IOException {

    return order.cancel(clientSessionId, orderUuid);
  }

}
