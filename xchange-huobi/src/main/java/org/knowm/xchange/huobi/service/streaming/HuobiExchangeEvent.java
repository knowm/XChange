package org.knowm.xchange.huobi.service.streaming;

import org.knowm.xchange.huobi.dto.streaming.response.Response;
import org.knowm.xchange.huobi.dto.streaming.response.payload.Payload;
import org.knowm.xchange.service.streaming.DefaultExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEventType;

public class HuobiExchangeEvent extends DefaultExchangeEvent {

  private final Response<? extends Payload> response;

  public HuobiExchangeEvent(ExchangeEventType exchangeEventType, Response<? extends Payload> response, Object payload) {
    super(exchangeEventType, null, payload);
    this.response = response;
  }

  public Response<? extends Payload> getResponse() {
    return response;
  }

}
