package org.knowm.xchange.gatehub.dto;

import java.util.Date;

import si.mazi.rescu.HttpStatusExceptionSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class GatehubException extends HttpStatusExceptionSupport {

  public GatehubException(@JsonProperty("message") String message) {
    super(message);
  }

  private String type;

  private String errorId;

  private Date createdAt;

  public String getType() {
    return type;
  }

  public String getErrorId() {
    return errorId;
  }

  public Date getCreatedAt() {
    return createdAt;
  }
}
