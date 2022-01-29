package org.knowm.xchange.bitstamp.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;

@JsonDeserialize(using = BitstampRippleDepositAddress.BitstampDepositAddressDeserializer.class)
public class BitstampRippleDepositAddress extends BitstampDepositAddress {

  @JsonProperty("address")
  private String addressAndDt;

  private final String address;
  private final Long destinationTag;

  public BitstampRippleDepositAddress(String error, String address, Long destinationTag) {
    super(error, address);

    final String[] split = address.split("\\?dt=");
    if (split.length == 2) {
      this.address = split[0];
      this.destinationTag = Long.parseLong(split[1]);
      this.addressAndDt = address;
    } else {
      this.address = address;
      if (destinationTag != null) {
        this.addressAndDt = address + "?dt=" + destinationTag;
      } else {
        this.addressAndDt = address;
      }
      this.destinationTag = destinationTag;
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
        JsonParser jsonParser, DeserializationContext ctxt) throws IOException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      if (node.get("error") != null) {
        return new BitstampRippleDepositAddress(node.path("error").asText(), "", null);
      }
      String address;
      Long destinationTag = null;

      if (node.get("address") != null) {

        address = node.get("address").asText();
        if (node.get("destination_tag") != null) {
          destinationTag = node.get("destination_tag").asLong();
        }

        return new BitstampRippleDepositAddress(null, address, destinationTag);

      } else {
        return new BitstampRippleDepositAddress(null, node.asText(), null);
      }
    }
  }
}
