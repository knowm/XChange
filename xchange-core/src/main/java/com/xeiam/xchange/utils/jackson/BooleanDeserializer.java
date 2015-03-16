package com.xeiam.xchange.utils.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

/**
 * @author Matija Mazi
 */
public abstract class BooleanDeserializer extends JsonDeserializer<Boolean> {

  private String trueValue, falseValue;

  protected BooleanDeserializer(String trueValue, String falseValue) {

    this.trueValue = trueValue;
    this.falseValue = falseValue;
  }

  @Override
  public Boolean deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

    String valueAsString = jp.getValueAsString();
    if (trueValue.equals(valueAsString)) {
      return true;
    } else if (falseValue.equals(valueAsString)) {
      return false;
    }
    throw new InvalidFormatException(String.format("Unrecognized value; expected %s or %s: %s", trueValue, falseValue, valueAsString), valueAsString,
        Boolean.class);
  }
}
