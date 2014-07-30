package com.xeiam.xchange.campbx.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matija Mazi
 */
public class CampBXResponse implements Serializable {

  @JsonProperty("Success")
  private String success;
  @JsonProperty("Info")
  private String info;
  @JsonProperty("Error")
  private String error;

  public String getSuccess() {

    return success;
  }

  public String getInfo() {

    return info;
  }

  public String getError() {

    return error;
  }

  public boolean isError() {

    return error != null;
  }

  public boolean isInfo() {

    return info != null;
  }

  public boolean isSuccess() {

    return success != null;
  }

  private String getUnwrappedResult() {

    return isError() ? error : isInfo() ? info : isSuccess() ? success : null;
  }

  private String getType() {

    return isError() ? "Error" : isInfo() ? "Info" : isSuccess() ? "Success" : "<Unknown>";
  }

  @Override
  public String toString() {

    return String.format("CampBXResponse[%s: %s]", getType(), getUnwrappedResult());
  }
}
