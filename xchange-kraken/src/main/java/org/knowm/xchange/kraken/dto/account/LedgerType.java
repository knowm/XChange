package org.knowm.xchange.kraken.dto.account;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.kraken.dto.account.LedgerType.LedgerTypeDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = LedgerTypeDeserializer.class)
public enum LedgerType {

  DEPOSIT, WITHDRAWAL, TRADE, MARGIN, CREDIT, ROLLOVER, TRANSFER;

  @Override
  public String toString() {

    return super.toString().toLowerCase();
  }

  public static LedgerType fromString(String ledgerTypeString) {

    LedgerType ledgerType = fromString.get(ledgerTypeString.toLowerCase());
    if (ledgerType == null) {
      throw new RuntimeException("Not supported kraken ledger type: " + ledgerTypeString);
    }
    return ledgerType;
  }

  private static final Map<String, LedgerType> fromString = new HashMap<>();

  static {
    for (LedgerType ledgerType : values())
      fromString.put(ledgerType.toString(), ledgerType);
  }

  static class LedgerTypeDeserializer extends JsonDeserializer<LedgerType> {

    @Override
    public LedgerType deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      String ledgerTypeString = node.textValue();
      return fromString(ledgerTypeString);
    }

  }
}
