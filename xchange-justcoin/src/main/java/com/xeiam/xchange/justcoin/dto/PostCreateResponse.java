package com.xeiam.xchange.justcoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jamespedwards42
 */
public class PostCreateResponse {

  private final String id;

  /**
   * Constructor
   * 
   * @param id
   */
  public PostCreateResponse(final @JsonProperty("id") String id) {

    this.id = id;
  }

  public String getId() {

    return id;
  }
}
