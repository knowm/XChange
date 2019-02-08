package org.knowm.xchange.dragonex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.concurrent.TimeUnit;

public class Token {

  /** unix time in seconds */
  public final long expireTime;

  public final String token;

  public Token(@JsonProperty("expire_time") long expireTime, @JsonProperty("token") String token) {
    this.expireTime = expireTime;
    this.token = token;
  }

  @Override
  public String toString() {
    return "Token [expireTime=" + expireTime + ", " + (token != null ? "token=" + token : "") + "]";
  }

  /** assume the token is expired 1 minute before the actual expiry */
  public boolean valid() {
    return expireTime * 1000 - System.currentTimeMillis() > TimeUnit.MINUTES.toMillis(1);
  }
}
