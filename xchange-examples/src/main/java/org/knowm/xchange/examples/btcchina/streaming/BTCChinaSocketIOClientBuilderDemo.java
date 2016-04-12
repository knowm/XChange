package org.knowm.xchange.examples.btcchina.streaming;

import static org.knowm.xchange.btcchina.service.streaming.BTCChinaSocketIOClientBuilder.EVENT_TICKER;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import org.knowm.xchange.btcchina.service.streaming.BTCChinaSocketIOClientBuilder;
import org.knowm.xchange.currency.CurrencyPair;

public class BTCChinaSocketIOClientBuilderDemo {

  public static void main(String[] args) throws InterruptedException {

    Socket socket = BTCChinaSocketIOClientBuilder.create().setUri(URI.create("https://websocket.btcchina.com"))
        .subscribeMarketData(CurrencyPair.BTC_CNY).build();

    socket.on(EVENT_TICKER, new Emitter.Listener() {

      @Override
      public void call(Object... args) {

        JSONObject jsonObject = (JSONObject) args[0];
        System.out.println(jsonObject);
      }
    });

    socket.connect();

    // Demonstrate for 30 seconds.
    TimeUnit.SECONDS.sleep(30);

    socket.disconnect();
  }

}
