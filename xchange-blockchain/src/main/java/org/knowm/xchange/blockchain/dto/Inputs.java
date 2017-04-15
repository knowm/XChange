package org.knowm.xchange.blockchain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public final class Inputs {

  private final Out prevOut;

  /**
   * Constructor
   *
   * @param prev_out
   */
  public Inputs(@JsonProperty("prev_out") Out prev_out) {

    this.prevOut = prev_out;
  }

  public Out getPrevOut() {

    return this.prevOut;
  }

}
