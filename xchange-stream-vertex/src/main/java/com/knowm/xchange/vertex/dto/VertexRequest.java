package com.knowm.xchange.vertex.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface VertexRequest {

  @JsonIgnore
  String getRequestType();

  @JsonIgnore
  String getSignature();
}
