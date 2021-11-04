package org.knowm.xchange.bitstamp.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;

@JsonDeserialize(using = BitstampRippleDepositAddress.BitstampDepositAddressDeserializer.class)
public class BitstampRippleDepositAddress extends BitstampDepositAddress {

  @JsonProperty("address")
  private  String addressAndDt;
  private String address = null;
  private Long destinationTag = null;

  public BitstampRippleDepositAddress(String address, Long destinationTag) {
    super(null, address);
    this.destinationTag = destinationTag;
    this.addressAndDt = address + "?dt=" + destinationTag;
  }

  public BitstampRippleDepositAddress(String error, String depositAddress) {
    super(error, depositAddress);
    this.addressAndDt = depositAddress;
    final String[] split = addressAndDt.split("\\?dt=");
    if (split.length == 2) {
      address = split[0];
      destinationTag = Long.parseLong(split[1]);
    }
  }

  public String getAddressAndDt() {

    return addressAndDt;
  }

  public String getAddress() {

    return address;
  }

  public Long getDestinationTag() {

    return destinationTag;
  }

  @Override
  public String toString() {

    return (address == null
        ? addressAndDt
        : String.format("RippleAddress[%s, dt=%s]", address, destinationTag));
  }

  static class BitstampDepositAddressDeserializer extends JsonDeserializer<BitstampDepositAddress> {

    @Override
    public BitstampRippleDepositAddress deserialize(
        JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      if (node.get("error") != null) {
        return new BitstampRippleDepositAddress(node.path("error").asText(), "");
      } else if (node.get("address") != null) {
        return new BitstampRippleDepositAddress(null, node.get("address").asText());
      } else {
        return new BitstampRippleDepositAddress(null, node.asText());
      }
    }
  }
}
