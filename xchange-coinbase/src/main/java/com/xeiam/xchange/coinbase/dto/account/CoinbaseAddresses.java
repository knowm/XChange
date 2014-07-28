/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.coinbase.dto.account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.xeiam.xchange.coinbase.dto.CoinbasePagedResult;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAddresses.CoinbaseAddressesDeserializer;
import com.xeiam.xchange.utils.DateUtils;

/**
 * @author jamespedwards42
 */
@JsonDeserialize(using = CoinbaseAddressesDeserializer.class)
public class CoinbaseAddresses extends CoinbasePagedResult {

  private final List<CoinbaseAddress> addresses;

  private CoinbaseAddresses(@JsonProperty("addresses") final List<CoinbaseAddress> addresses, @JsonProperty("total_count") final int totalCount, @JsonProperty("num_pages") final int numPages,
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
    public CoinbaseAddresses deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jp.getCodec();
      final JsonNode node = oc.readTree(jp);
      final JsonNode addressesArrayNode = node.path("addresses");

      final List<CoinbaseAddress> addresses = new ArrayList<CoinbaseAddress>();
      for (final JsonNode addressNode : addressesArrayNode) {
        addresses.add(getAddressFromNode(addressNode));
      }

      final int totalCount = node.path("total_count").asInt();
      final int numPages = node.path("num_pages").asInt();
      final int currentPage = node.path("current_page").asInt();

      return new CoinbaseAddresses(addresses, totalCount, numPages, currentPage);
    }

    private CoinbaseAddress getAddressFromNode(final JsonNode addressNode) throws InvalidFormatException {

      final JsonNode nestedAddressNode = addressNode.path("address");
      final String address = nestedAddressNode.path("address").asText();
      final String callbackUrl = nestedAddressNode.path("callback_url").asText();
      final String label = nestedAddressNode.path("label").asText();
      final Date createdAt = DateUtils.fromISO8601DateString(nestedAddressNode.path("created_at").asText());

      return new CoinbaseAddress(address, callbackUrl, label, createdAt);
    }
  }
}
