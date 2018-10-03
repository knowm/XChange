package org.knowm.xchange.kraken.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.kraken.dto.marketdata.KrakenFee.KrakenFeeDeserializer;

@JsonDeserialize(using = KrakenFeeDeserializer.class)
public class KrakenFee {

  private final BigDecimal volume;
  private final BigDecimal percentFee;

  public KrakenFee(BigDecimal volume, BigDecimal percentFee) {

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

  static class KrakenFeeDeserializer extends JsonDeserializer<KrakenFee> {

    @Override
    public KrakenFee deserialize(JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      BigDecimal volume = new BigDecimal(node.path(0).asText());
      BigDecimal fee = new BigDecimal(node.path(1).asText());

      return new KrakenFee(volume, fee);
    }
  }
}
