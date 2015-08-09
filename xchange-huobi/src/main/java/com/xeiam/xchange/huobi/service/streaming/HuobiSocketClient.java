package com.xeiam.xchange.huobi.service.streaming;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.xeiam.xchange.huobi.dto.streaming.dto.Percent;
import com.xeiam.xchange.huobi.dto.streaming.dto.Period;
import com.xeiam.xchange.huobi.dto.streaming.request.Request;
import com.xeiam.xchange.huobi.dto.streaming.request.historydata.ReqKLineRequest;
import com.xeiam.xchange.huobi.dto.streaming.request.historydata.ReqMarketDepthRequest;
import com.xeiam.xchange.huobi.dto.streaming.request.historydata.ReqMarketDepthTopRequest;
import com.xeiam.xchange.huobi.dto.streaming.request.historydata.ReqMarketDetailRequest;
import com.xeiam.xchange.huobi.dto.streaming.request.historydata.ReqTimeLineRequest;
import com.xeiam.xchange.huobi.dto.streaming.request.historydata.ReqTradeDetailTopRequest;
import com.xeiam.xchange.huobi.dto.streaming.request.marketdata.Message;
import com.xeiam.xchange.huobi.dto.streaming.request.service.ReqMsgSubscribeRequest;
import com.xeiam.xchange.huobi.dto.streaming.request.service.ReqMsgUnsubscribeRequest;
import com.xeiam.xchange.huobi.dto.streaming.request.service.ReqSymbolDetailRequest;
import com.xeiam.xchange.huobi.dto.streaming.request.service.ReqSymbolListRequest;
import com.xeiam.xchange.huobi.dto.streaming.response.Response;
import com.xeiam.xchange.huobi.dto.streaming.response.ResponseFactory;
import com.xeiam.xchange.huobi.dto.streaming.response.payload.Payload;

import io.socket.IOAcknowledge;

/**
 * The entry point of the Huobi WebSocket API Client.
 */
public class HuobiSocketClient {

  private static final int VERSION = 1;

  private final HuobiSocket socket;
  private final Gson gson = new Gson();
  private final List<ResponseListener> listeners = new ArrayList();
  private final ResponseFactory responseFactory = ResponseFactory.getInstance();

  public HuobiSocketClient(String url) throws Exception {
    socket = new HuobiSocket(new URL(url));
    socket.addListener(new HuobiSocketAdapter() {

      @Override
      public void on(String event, IOAcknowledge ack, JsonElement... args) {
        Response<? extends Payload> response = responseFactory.fromJson(event, args);
        onResponse(response);
      }

    });
  }

  public void connect() throws Exception {
    socket.connect();
  }

  public void disconnect() {
    socket.disconnect();
  }

  public void addListener(HuobiSocketListener listener) {
    socket.addListener(listener);
  }

  public void addListener(ResponseListener listener) {
    listeners.add(listener);
  }

  protected void onResponse(Response<? extends Payload> response) {
    for (ResponseListener listener : listeners) {
      listener.onResponse(response);
    }
  }

  public void send(Request request) {
    socket.emit("request", gson.toJson(request));
  }

  public void reqSymbolList(String... symbol) {
    ReqSymbolListRequest request = new ReqSymbolListRequest(VERSION);
    request.setSymbolIdList(symbol);
    send(request);
  }

  public void reqSymbolDetail(String... symbol) {
    ReqSymbolDetailRequest request = new ReqSymbolDetailRequest(VERSION, symbol);
    send(request);
  }

  public void reqMsgSubscribe(Message message) {
    ReqMsgSubscribeRequest request = new ReqMsgSubscribeRequest(VERSION, message);
    send(request);
  }

  public void reqMsgUnsubscribe(Message message) {
    ReqMsgUnsubscribeRequest request = new ReqMsgUnsubscribeRequest(VERSION, message);
    send(request);
  }

  public void reqTimeLine(String symbol) {
    ReqTimeLineRequest request = new ReqTimeLineRequest(VERSION, symbol);
    send(request);
  }

  public void reqKLine(String symbol, Period period, Date from, Date to) {
    ReqKLineRequest request = new ReqKLineRequest(VERSION, symbol, period);
    request.setFrom(from);
    request.setTo(to);
    send(request);
  }

  public void reqMarketDepthTop(String symbol) {
    ReqMarketDepthTopRequest request = new ReqMarketDepthTopRequest(VERSION, symbol);
    send(request);
  }

  public void reqMarketDepth(String symbol, Percent percent) {
    ReqMarketDepthRequest request = new ReqMarketDepthRequest(VERSION, symbol, percent);
    send(request);
  }

  public void reqTradeDetailTop(String symbol, int count) {
    ReqTradeDetailTopRequest request = new ReqTradeDetailTopRequest(VERSION, symbol);
    request.setCount(count);
    send(request);
  }

  public void reqMarketDetail(String symbol) {
    ReqMarketDetailRequest request = new ReqMarketDetailRequest(VERSION, symbol);
    send(request);
  }

}
