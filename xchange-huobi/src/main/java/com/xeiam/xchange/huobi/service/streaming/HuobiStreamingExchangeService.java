package com.xeiam.xchange.huobi.service.streaming;

import com.google.gson.Gson;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.java_websocket.WebSocket.READYSTATE;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.huobi.dto.streaming.dto.Percent;
import com.xeiam.xchange.huobi.dto.streaming.request.Request;
import com.xeiam.xchange.huobi.dto.streaming.request.marketdata.Message;
import com.xeiam.xchange.huobi.dto.streaming.request.marketdata.PushType;
import static com.xeiam.xchange.huobi.service.streaming.HuobiSocketIOAdapters.adaptSymbol;
import com.xeiam.xchange.service.streaming.DefaultExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeEventType;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;
import io.socket.SocketIOException;
import java.util.HashSet;
import java.util.Set;



public class HuobiStreamingExchangeService implements StreamingExchangeService {
	private HuobiSocketClient client = null;
	private Message message;
	private Gson gson = new Gson();
	private BlockingQueue<ExchangeEvent> consumerEventQueue = new LinkedBlockingQueue<ExchangeEvent>();
	private HuobiExchangeEventListener listener;
	private READYSTATE webSocketStatus = READYSTATE.NOT_YET_CONNECTED;

	public HuobiStreamingExchangeService(final ExchangeSpecification exchangeSpecification,	final ExchangeStreamingConfiguration configuration) {
		String sslUri = (String)exchangeSpecification.getExchangeSpecificParametersItem("Websocket_SslUri");
                
                try{
                	client = new HuobiSocketClient(sslUri);
                }
                catch(Exception e){
                    //TODO log
                    System.err.println("HuobiStreamingExchangeService: " + e.getMessage());
                }

		message = new Message();

		CurrencyPair[] currencyPairs = ((HuobiExchangeStreamingConfiguration)configuration).getMarketDataCurrencyPairs();
		final Set<String> symbols = new HashSet(currencyPairs.length);

		for (CurrencyPair currencyPair : currencyPairs) {
			String symbol = adaptSymbol(currencyPair);
			symbols.add(symbol);

			// Ticker
			message.addMarketOverview(symbol, PushType.PUSH_LONG);

			// Depth
			message.addMarketDepthDiff(symbol, PushType.PUSH_LONG, Percent.PERCENT10);

			// Trade
			message.addTradeDetail(symbol, PushType.PUSH_LONG);
		}

		client.addListener(new HuobiSocketAdapter() {

			@Override
			public void onConnect() {
				webSocketStatus = READYSTATE.OPEN;
				putEvent(ExchangeEventType.CONNECT);

				for (String symbol : symbols) {
					// Depth
					client.reqMarketDepth(symbol, Percent.PERCENT10);

					// Trade
					client.reqTradeDetailTop(symbol, 10);
				}

				client.reqMsgSubscribe(message);
			}

			@Override
			public void onDisconnect() {
				webSocketStatus = READYSTATE.CLOSED;
				putEvent(ExchangeEventType.DISCONNECT);
			}


			@Override
			public void onError(SocketIOException socketIOException) {
				putEvent(new DefaultExchangeEvent(ExchangeEventType.ERROR,socketIOException.getMessage(), socketIOException));
			}

		});

		listener = new HuobiExchangeEventListener(client, consumerEventQueue);
		client.addListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connect() {
            try{
		client.connect();
            }
            catch(Exception e){
                //TODO log
                System.err.println("connect: " + e.getMessage());
            }
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disconnect() {
		client.reqMsgUnsubscribe(message);
		client.disconnect();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExchangeEvent getNextEvent() throws InterruptedException {
		return consumerEventQueue.take();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void send(String msg) {
		client.send(gson.fromJson(msg, Request.class));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public READYSTATE getWebSocketStatus() {
		return webSocketStatus;
	}

	private void putEvent(ExchangeEvent event) {
		try {
			consumerEventQueue.put(event);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private void putEvent(ExchangeEventType exchangeEventType) {
		putEvent(new DefaultExchangeEvent(exchangeEventType, null));
	}

    @Override
    public int countEventsAvailable() {
        return consumerEventQueue.size();
    }
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            