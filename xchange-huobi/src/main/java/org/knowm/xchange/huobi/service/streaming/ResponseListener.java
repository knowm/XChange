package org.knowm.xchange.huobi.service.streaming;

import org.knowm.xchange.huobi.dto.streaming.response.Response;
import org.knowm.xchange.huobi.dto.streaming.response.payload.Payload;

/**
 * The listener interface for receiving Huobi exchange response.
 */
public interface ResponseListener {

  void onResponse(Response<? extends Payload> response);

}
