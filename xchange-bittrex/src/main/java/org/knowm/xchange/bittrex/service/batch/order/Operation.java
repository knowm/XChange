package org.knowm.xchange.bittrex.service.batch.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Operation {
  POST("Post"),
  DELETE("Delete");

  private final String operation;
}
