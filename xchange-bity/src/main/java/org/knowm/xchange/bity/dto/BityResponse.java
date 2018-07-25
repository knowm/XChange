package org.knowm.xchange.bity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BityResponse<T> {

  @JsonProperty("objects")
  private List<T> objects = null;

  @JsonProperty("objects")
  public List<T> getObjects() {
    return objects;
  }

  @JsonProperty("objects")
  public void setObjects(List<T> objects) {
    this.objects = objects;
  }
}
