package com.xeiam.xchange.okcoin.service.streaming;

import java.util.TimerTask;

class MonitorTask extends TimerTask {  
  private long  startTime = System.currentTimeMillis();
  private int checkTime = 5000;
  private WebSocketBase client = null;
  
  public void updateTime(){
    startTime = System.currentTimeMillis();
  }
  
  public MonitorTask(WebSocketBase client){
    this.client = client;
  }
  
  public void run() {  
    if(System.currentTimeMillis() - startTime > checkTime){
      client.setStatus(false);

      client.reConnect();
    } 
    
    client.sendPing();
  }  
}