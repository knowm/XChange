package org.knowm.xchange.dragonex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenStatus {

  /** user id in DragonEx platform */
  public final long uid;

  public TokenStatus(@JsonProperty("uid") long uid) {
    this.uid = uid;
  }

  @Override
  public String toString() {
    return "TokenStatus [uid=" + uid + "]";
  }
}
