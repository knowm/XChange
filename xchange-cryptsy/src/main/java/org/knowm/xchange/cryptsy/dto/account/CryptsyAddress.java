package org.knowm.xchange.cryptsy.dto.account;

import java.text.ParseException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ObsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptsyAddress {

  private final String address;

  public CryptsyAddress(@JsonProperty("address") String address) throws ParseException {

    this.address = address;
  }

  public String getAddress() {

    return address;
  }

  @Override
  public String toString() {

    return "CryptsyTransactionHistory[" + "Address='" + address + "']";
  }
}
