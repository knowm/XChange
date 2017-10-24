package org.knowm.xchange.btcchina.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author David Yam
 */
public class BTCChinaID {

  private final String id;

  /**
   * Constructor
   *
   * @param id
   */
  public BTCChinaID(@JsonProperty("id") String id) {

    this.id = id;
  }

  public String getId() {

    return id;
  }

  @Override
  public String toString() {

    return String.format("BTCChinaID{id=%s}", id);
  }

}
