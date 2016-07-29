package org.knowm.xchange.hitbtc.service.streaming;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcIncrementalRefresh;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcSnapshotFullRefresh;
import org.knowm.xchange.service.streaming.DefaultExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEventListener;
import org.knowm.xchange.service.streaming.ExchangeEventType;

public class HitbtcStreamingMarketDataRawEventListener extends ExchangeEventListener {

  private final BlockingQueue<ExchangeEvent> consumerEventQueue;
  private final ObjectMapper streamObjectMapper;

  /**
   * @param consumerEventQueue
   */
  public HitbtcStreamingMarketDataRawEventListener(BlockingQueue<ExchangeEvent> consumerEventQueue) {

    this.consumerEventQueue = consumerEventQueue;

    this.streamObjectMapper = new ObjectMapper();
    this.streamObjectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
  }

  @Override
  public void handleEvent(ExchangeEvent event) throws ExchangeException {

    if (event.getEventType() != ExchangeEventType.MESSAGE) {

      forwardEvent(event);

    } else {

      String data = event.getData();

      HitbtcIncrementalRefresh incrementalRefresh;
      try {

        incrementalRefresh = streamObjectMapper.readValue(data, HitbtcIncrementalRefresh.class);

      } catch (JsonMappingException jme) {

        HitbtcSnapshotFullRefresh snapshotFullRefresh;
        try {

          snapshotFullRefresh = streamObjectMapper.readValue(data, HitbtcSnapshotFullRefresh.class);

        } catch (IOException e) {
          throw new ExchangeException("JSON parse error", e);
        }

        handleDTO(data, snapshotFullRefresh);
        return;

      } catch (IOException e) {
        throw new ExchangeException("JSON parse error", e);
      }

      handleDTO(data, incrementalRefresh);
    }
  }

  private void forwardEvent(ExchangeEvent event) {

    try {
      consumerEventQueue.put(event);
    } catch (InterruptedException e) {
      throw new ExchangeException("InterruptedException!", e);
    }
  }

  private void handleDTO(String data, Object dto) {

    ExchangeEvent event = new DefaultExchangeEvent(ExchangeEventType.EVENT, data, dto);

    forwardEvent(event);
  }
}
