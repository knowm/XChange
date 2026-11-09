package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.math.BigDecimal;

@Getter
@ToString
@Builder
@JsonDeserialize(using = GateioPositionLeverageUpdate.GateioPositionLeverageUpdateDeserializer.class)
public class GateioPositionLeverageUpdate {

  private final BigDecimal leverage;
  private final BigDecimal crossLeverageLimit;

  public GateioPositionLeverageUpdate(BigDecimal leverage, BigDecimal crossLeverageLimit) {
    this.leverage = leverage;
    this.crossLeverageLimit = crossLeverageLimit;
  }

  public static class GateioPositionLeverageUpdateDeserializer
      extends JsonDeserializer<GateioPositionLeverageUpdate> {

    @Override
    public GateioPositionLeverageUpdate deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException {
      JsonNode node = p.getCodec().readTree(p);
      if (node == null || node.isNull()) {
        return null;
      }
      if (node.isArray()) {
        if (node.isEmpty()) {
          return null;
        }
        node = node.get(0);
      }
      JsonNode leverageNode = node.get("leverage");
      JsonNode crossLeverageLimitNode = node.get("cross_leverage_limit");

      BigDecimal leverage =
          leverageNode != null && !leverageNode.isNull() && !leverageNode.asText().isEmpty()
              ? new BigDecimal(leverageNode.asText())
              : null;
      BigDecimal crossLeverageLimit =
          crossLeverageLimitNode != null && !crossLeverageLimitNode.isNull() && !crossLeverageLimitNode.asText().isEmpty()
              ? new BigDecimal(crossLeverageLimitNode.asText())
              : null;

      return new GateioPositionLeverageUpdate(leverage, crossLeverageLimit);
    }
  }
}
