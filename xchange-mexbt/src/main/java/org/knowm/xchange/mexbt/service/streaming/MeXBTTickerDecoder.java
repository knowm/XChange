package org.knowm.xchange.mexbt.service.streaming;

import java.io.IOException;
import java.io.Reader;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.mexbt.dto.streaming.MeXBTStreamingTicker;

public class MeXBTTickerDecoder implements Decoder.TextStream<MeXBTStreamingTicker> {

  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(EndpointConfig config) {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void destroy() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MeXBTStreamingTicker decode(Reader reader) throws DecodeException, IOException {
    return mapper.readValue(reader, MeXBTStreamingTicker.class);
  }

}
