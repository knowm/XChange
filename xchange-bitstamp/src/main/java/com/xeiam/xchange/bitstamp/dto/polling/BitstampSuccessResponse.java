package com.xeiam.xchange.bitstamp.dto.polling;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author gnandiga
 */
@JsonDeserialize(using = BitstampSuccessResponse.BitstampSuccessResponseDeserializer.class)
public class BitstampSuccessResponse {

  private final String error;
  private final boolean success;

  public BitstampSuccessResponse(String error, boolean result) {

    this.error = error;
    this.success = result;
  }

  public String getError() {

    return error;
  }

  public boolean getSuccess() {

    return success;
  }

  static class BitstampSuccessResponseDeserializer extends JsonDeserializer<BitstampSuccessResponse> {

    @Override
    public BitstampSuccessResponse deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      if (node.isBoolean()) {
        return new BitstampSuccessResponse(null, node.asBoolean());
      }
      else if (node.path("error") != null) {
        return new BitstampSuccessResponse(node.path("error").asText(), false);
      }
      return new BitstampSuccessResponse("Invalid response from Bitstamp Server.", false);
    }
  }

}
