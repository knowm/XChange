package org.knowm.xchange.cexio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Author: veken0m */
public class GHashIOWorker {

  private final BigDecimal last5m;
  private final BigDecimal last15m;
  private final BigDecimal last1h;
  private final BigDecimal last1d;
  private final BigDecimal prev5m;
  private final BigDecimal prev15m;
  private final BigDecimal prev1h;
  private final BigDecimal prev1d;
  private final GHashIORejected rejected;

  /**
   * @param last5m
   * @param last15m
   * @param last1h
   * @param last1d
   * @param prev5m
   * @param prev15m
   * @param prev1h
   * @param prev1d
   * @param rejected
   */
  public GHashIOWorker(
      @JsonProperty("last5m") BigDecimal last5m,
      @JsonProperty("last15m") BigDecimal last15m,
      @JsonProperty("last1h") BigDecimal last1h,
      @JsonProperty("last1d") BigDecimal last1d,
      @JsonProperty("prev5m") BigDecimal prev5m,
      @JsonProperty("prev15m") BigDecimal prev15m,
      @JsonProperty("prev1h") BigDecimal prev1h,
      @JsonProperty("prev1d") BigDecimal prev1d,
      @JsonProperty("rejected") GHashIORejected rejected) {

    this.last5m = last5m;
    this.last15m = last15m;
    this.last1h = last1h;
    this.last1d = last1d;
    this.prev5m = prev5m;
    this.prev15m = prev15m;
    this.prev1h = prev1h;
    this.prev1d = prev1d;
    this.rejected = rejected;
  }

  public BigDecimal getLast5m() {

    return last5m;
  }

  public BigDecimal getLast15m() {

    return last15m;
  }

  public BigDecimal getLast1h() {

    return last1h;
  }

  public BigDecimal getLast1d() {

    return last1d;
  }

  public BigDecimal getPrev5m() {

    return prev5m;
  }

  public BigDecimal getPrev15m() {

    return prev15m;
  }

  public BigDecimal getPrev1h() {

    return prev1h;
  }

  public BigDecimal getPrev1d() {

    return prev1d;
  }

  public GHashIORejected getRejected() {

    return rejected;
  }
}
