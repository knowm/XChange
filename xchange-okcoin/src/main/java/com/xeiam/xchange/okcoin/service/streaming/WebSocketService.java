package com.xeiam.xchange.okcoin.service.streaming;

public interface WebSocketService {
   public void onReceive(String msg);
   public void onDisconnect();
}