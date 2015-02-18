package com.xeiam.xchange.okcoin.service.streaming;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.okcoin.OkCoinAdapters;
import com.xeiam.xchange.okcoin.dto.marketdata.OkCoinDepth;
import com.xeiam.xchange.okcoin.dto.marketdata.OkCoinStreamingTicker;
import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeEventType;


public class OkCoinWebSocketService implements WebSocketService {
  private final ObjectMapper mapper = new ObjectMapper();
  private final JsonFactory jsonFactory = new JsonFactory();
  private final BlockingQueue<ExchangeEvent> eventQueue;
  private final CurrencyPair[] currencyPairs;

  public OkCoinWebSocketService(BlockingQueue<ExchangeEvent> eventQueue, CurrencyPair[] currencyPairs) {
    this.eventQueue = eventQueue; 
    this.currencyPairs = currencyPairs;
  }

  @Override
  public void onReceive(String msg) {
    try {
      JsonParser parser = jsonFactory.createParser(msg);
      if(parser.nextToken() == JsonToken.START_ARRAY) {
        ArrayNode readTree = (ArrayNode) mapper.readTree(msg);

        Iterator<JsonNode> iterator = readTree.iterator();
        while(iterator.hasNext()) {
          JsonNode node = iterator.next();
          
          // parse any requested channels
          for(int i = 0; i < currencyPairs.length; i++) {
            parseMarketData(node, currencyPairs[i]);
          }
        }
      } else {
        // only pong should be here
      }

    } catch (JsonParseException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /** Parse depth, trades, and ticker for a given currency pair */
  private void parseMarketData(JsonNode node, CurrencyPair currencyPair) throws JsonParseException, JsonMappingException, IOException {
    String basename = currencyPair.baseSymbol.toLowerCase() + currencyPair.counterSymbol.toLowerCase();
    
    if(node.get("channel").textValue().equals("ok_" + basename + "_depth")) {                
      parseDepth(node, currencyPair);
      
    } else if(node.get("channel").textValue().equals("ok_" + basename + "_trades_v1")) {
      parseTrades(node, currencyPair);
      
    } else if(node.get("channel").textValue().equals("ok_" + basename + "_ticker")) {
      parseTicker(node, currencyPair);
    }
  }

  private void parseTicker(JsonNode node, CurrencyPair currencyPair) throws IOException, JsonParseException, JsonMappingException {
    
    OkCoinStreamingTicker ticker = mapper.readValue(node.get("data").toString(), OkCoinStreamingTicker.class);    
    putEvent(ExchangeEventType.TICKER, OkCoinJsonAdapters.adaptTicker(ticker, currencyPair));
  }

  private void parseDepth(JsonNode node, CurrencyPair currencyPair) throws IOException, JsonParseException, JsonMappingException {

    OkCoinDepth depth = mapper.readValue(node.get("data").toString(), OkCoinDepth.class);    
    putEvent(ExchangeEventType.DEPTH, OkCoinAdapters.adaptOrderBook(depth, currencyPair));
  }

  private void parseTrades(JsonNode node, CurrencyPair currencyPair) {

    JsonNode jsonNode = node.get("data");
    for(int i = 0; i < jsonNode.size(); i++) {
      JsonNode trade = jsonNode.get(i);
      putEvent(ExchangeEventType.TRADE, OkCoinJsonAdapters.adaptTrade(trade, currencyPair));                  
    }
  }
  
  private void putEvent(ExchangeEventType eventType, Object payload) {
    try {
      eventQueue.put(new OkCoinExchangeEvent(eventType, payload));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
