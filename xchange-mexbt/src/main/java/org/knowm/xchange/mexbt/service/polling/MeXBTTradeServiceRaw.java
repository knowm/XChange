package org.knowm.xchange.mexbt.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.mexbt.dto.MeXBTException;
import org.knowm.xchange.mexbt.dto.MeXBTInsPaginationRequest;
import org.knowm.xchange.mexbt.dto.MeXBTInsRequest;
import org.knowm.xchange.mexbt.dto.MeXBTRequest;
import org.knowm.xchange.mexbt.dto.MeXBTResponse;
import org.knowm.xchange.mexbt.dto.account.MeXBTOpenOrdersResponse;
import org.knowm.xchange.mexbt.dto.account.MeXBTTradeResponse;
import org.knowm.xchange.mexbt.dto.trade.MeXBTOrderCancelRequest;
import org.knowm.xchange.mexbt.dto.trade.MeXBTOrderCreateRequest;
import org.knowm.xchange.mexbt.dto.trade.MeXBTOrderModifyRequest;
import org.knowm.xchange.mexbt.dto.trade.MeXBTServerOrderIdResponse;

public class MeXBTTradeServiceRaw extends MeXBTAuthenticatedPollingService {

  public MeXBTTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public MeXBTServerOrderIdResponse createOrder(String ins, String side, int orderType, BigDecimal qty, BigDecimal px) throws IOException {
    return meXBTAuthenticated.createOrder(new MeXBTOrderCreateRequest(apiKey, nonceFactory, meXBTDigest, ins, side, orderType, qty, px));
  }

  public MeXBTServerOrderIdResponse modifyOrder(String ins, String serverOrderId, int modifyAction) throws MeXBTException, IOException {
    return meXBTAuthenticated.modifyOrder(new MeXBTOrderModifyRequest(apiKey, nonceFactory, meXBTDigest, ins, serverOrderId, modifyAction));
  }

  public MeXBTServerOrderIdResponse cancelOrder(String ins, String serverOrderId) throws MeXBTException, IOException {
    return meXBTAuthenticated.cancelOrder(new MeXBTOrderCancelRequest(apiKey, nonceFactory, meXBTDigest, ins, serverOrderId));
  }

  public MeXBTResponse cancelAll(String ins) throws MeXBTException, IOException {
    return meXBTAuthenticated.cancelAll(new MeXBTInsRequest(apiKey, nonceFactory, meXBTDigest, ins));
  }

  public MeXBTOpenOrdersResponse getMeXBTOpenOrders() throws MeXBTException, IOException {
    return meXBTAuthenticated.getOpenOrders(new MeXBTRequest(apiKey, nonceFactory, meXBTDigest));
  }

  public MeXBTTradeResponse getTrades(String ins, long startIndex, int count) throws MeXBTException, IOException {
    return meXBTAuthenticated.getTrades(new MeXBTInsPaginationRequest(apiKey, nonceFactory, meXBTDigest, ins, startIndex, count));
  }

}
