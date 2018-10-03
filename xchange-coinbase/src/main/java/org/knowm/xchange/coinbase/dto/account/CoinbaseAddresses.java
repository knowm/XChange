package org.knowm.xchange.coinbase.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.coinbase.dto.CoinbasePagedResult;
import org.knowm.xchange.coinbase.dto.account.CoinbaseAddresses.CoinbaseAddressesDeserializer;
import org.knowm.xchange.utils.DateUtils;

/** @author jamespedwards42 */
@JsonDeserialize(using = CoinbaseAddressesDeserializer.class)
public class CoinbaseAddresses extends CoinbasePagedResult {

  private final List<CoinbaseAddress> addresses;

  private CoinbaseAddresses(
      @JsonProperty("addresses") final List<CoinbaseAddress> addresses,
      @JsonProperty("total_count") final int totalCount,
      @JsonProperty("num_pages") final int numPages,
      @JsonProperty("current_page") final int currentPage) {

    super(totalCount, numPages, currentPage);
    this.addresses = addresses;
  }

  public List<CoinbaseAddress> getAddresses() {

    return addresses;
  }

  @Override
  public String toString() {

    return "CoinbaseAddresses [addresses=" + addresses + "]";
  }

  static class CoinbaseAddressesDeserializer extends JsonDeserializer<CoinbaseAddresses> {

    @Override
    public CoinbaseAddresses deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      final ObjectCodec oc = jp.getCodec();
      final JsonNode node = oc.readTree(jp);
      final JsonNode addressesArrayNode = node.path("addresses");

      final List<CoinbaseAddress> addresses = new ArrayList<>();
      for (JsonNode addressNode : addressesArrayNode) {
        addresses.add(getAddressFromNode(addressNode));
      }

      final int totalCount = node.path("total_count").asInt();
      final int numPages = node.path("num_pages").asInt();
      final int currentPage = node.path("current_page").asInt();

      return new CoinbaseAddresses(addresses, totalCount, numPages, currentPage);
    }

    private CoinbaseAddress getAddressFromNode(JsonNode addressNode)
        throws com.fasterxml.jackson.databind.exc.InvalidFormatException {

      final JsonNode nestedAddressNode = addressNode.path("address");
      final String address = nestedAddressNode.path("address").asText();
      final String callbackUrl = nestedAddressNode.path("callback_url").asText();
      final String label = nestedAddressNode.path("label").asText();
      final Date createdAt =
          DateUtils.fromISO8601DateString(nestedAddressNode.path("created_at").asText());

      return new CoinbaseAddress(address, callbackUrl, label, createdAt);
    }
  }
}
