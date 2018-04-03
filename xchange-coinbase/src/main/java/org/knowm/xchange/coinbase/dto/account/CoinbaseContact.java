package org.knowm.xchange.coinbase.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author jamespedwards42 */
public class CoinbaseContact {

  private final CoinbaseContactEmail contact;

  public CoinbaseContact(@JsonProperty("contact") final CoinbaseContactEmail contact) {

    this.contact = contact;
  }

  public String getEmail() {

    return contact.email;
  }

  @Override
  public String toString() {

    return "CoinbaseContact [contact=" + contact + "]";
  }

  private static class CoinbaseContactEmail {

    private final String email;

    private CoinbaseContactEmail(@JsonProperty("email") final String email) {

      this.email = email;
    }

    @Override
    public String toString() {

      return "CoinbaseContactEmail [email=" + email + "]";
    }
  }
}
