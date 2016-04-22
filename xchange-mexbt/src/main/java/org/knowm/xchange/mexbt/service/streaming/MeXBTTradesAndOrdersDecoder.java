package org.knowm.xchange.mexbt.service.streaming;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.knowm.xchange.mexbt.dto.streaming.MeXBTStreamingTradeOrOrder;

public class MeXBTTradesAndOrdersDecoder implements Decoder.TextStream<MeXBTStreamingTradeOrOrder[]> {

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
  public MeXBTStreamingTradeOrOrder[] decode(Reader reader) throws DecodeException, IOException {
    JsonReader jsonReader = Json.createReader(reader);
    JsonArray jsonArray = jsonReader.readArray();
    MeXBTStreamingTradeOrOrder[] ret = new MeXBTStreamingTradeOrOrder[jsonArray.size()];
    for (int i = 0; i < jsonArray.size(); i++) {
      JsonArray record = (JsonArray) jsonArray.get(i);
      long id = record.getJsonNumber(0).longValue();
      long tick = record.getJsonNumber(1).longValue();
      BigDecimal price = record.getJsonNumber(2).bigDecimalValue();
      BigDecimal quantity = record.getJsonNumber(3).bigDecimalValue();
      int action = record.getJsonNumber(4).intValue();
      int side = record.getJsonNumber(5).intValue();
      Date timestamp = new Date((tick - 621355968000000000L) / 10000);
      ret[i] = new MeXBTStreamingTradeOrOrder(id, timestamp, price, quantity, action, side);
    }
    return ret;
  }

}
