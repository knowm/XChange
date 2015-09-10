package com.xeiam.xchange.huobi.service.streaming;

import com.xeiam.xchange.huobi.dto.streaming.response.Response;
import com.xeiam.xchange.huobi.dto.streaming.response.payload.Payload;

/**
 * The listener interface for receiving Huobi exchange response.
 */
public interface ResponseListener {

  void onResponse(Response<? extends Payload> response);

}
