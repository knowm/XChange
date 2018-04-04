package org.known.xchange.acx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class AcxAccountInfo {
  /** Unique identifier of user */
  public final String sn;
  /** User name */
  public final String name;
  /** User email */
  public final String email;
  /** Whether user is activated */
  public final boolean activated;
  /** User’s accounts info, see {@link AcxAccount} */
  public final List<AcxAccount> accounts;

  public AcxAccountInfo(
      @JsonProperty("an") String sn,
      @JsonProperty("name") String name,
      @JsonProperty("email") String email,
      @JsonProperty("activated") boolean activated,
      @JsonProperty("accounts") List<AcxAccount> accounts) {
    this.sn = sn;
    this.name = name;
    this.email = email;
    this.activated = activated;
    this.accounts = accounts;
  }
}
