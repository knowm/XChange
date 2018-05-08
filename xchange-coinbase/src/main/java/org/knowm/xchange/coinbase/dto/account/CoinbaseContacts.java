package org.knowm.xchange.coinbase.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.coinbase.dto.CoinbasePagedResult;

/** @author jamespedwards42 */
public class CoinbaseContacts extends CoinbasePagedResult {

  private final List<CoinbaseContact> contacts;

  private CoinbaseContacts(
      @JsonProperty("contacts") List<CoinbaseContact> contacts,
      @JsonProperty("total_count") final int totalCount,
      @JsonProperty("num_pages") final int numPages,
      @JsonProperty("current_page") final int currentPage) {

    super(totalCount, numPages, currentPage);
    this.contacts = contacts;
  }

  public List<CoinbaseContact> getContacts() {

    return contacts;
  }

  @Override
  public String toString() {

    return "CoinbaseContacts [contacts=" + contacts + "]";
  }
}
