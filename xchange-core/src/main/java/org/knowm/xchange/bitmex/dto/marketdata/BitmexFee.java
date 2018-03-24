package org.knowm.xchange.bitmex.dto.marketdata;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.bitmex.dto.marketdata.BitmexFee.BitmexFeeDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = BitmexFeeDeserializer.class)
public class BitmexFee {

  private final BigDecimal volume;
  private final BigDecimal percentFee;

  public BitmexFee(BigDecimal volume, BigDecimal percentFee) {

    this.volume = volume;
    this.percentFee = percentFee;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public BigDecimal getPercentFee() {

    return percentFee;
  }

  @Override
  public String toString() {

    return "KrakenFee [volume=" + volume + ", percentFee=" + percentFee + "]";
  }

  static class BitmexFeeDeserializer extends JsonDeserializer<BitmexFee> {

    @Override
    public BitmexFee deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      BigDecimal volume = new BigDecimal(node.path(0).asText());
      BigDecimal fee = new BigDecimal(node.path(1).asText());

      return new BitmexFee(volume, fee);
    }
  }
}
