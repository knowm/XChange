package com.knowm.xchange.vertex;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Query {
  private final String queryMsg;
  private final Consumer<JsonNode> respHandler;
  private final BiConsumer<Integer, String> errorHandler;

  public Query(String queryMsg, Consumer<JsonNode> respHandler, BiConsumer<Integer, String> errorHandler) {
    this.queryMsg = queryMsg;
    this.respHandler = respHandler;
    this.errorHandler = errorHandler;
  }

  public String getQueryMsg() {
    return queryMsg;
  }

  public Consumer<JsonNode> getRespHandler() {
    return respHandler;
  }

  public BiConsumer<Integer, String> getErrorHandler() {
    return errorHandler;
  }
}
