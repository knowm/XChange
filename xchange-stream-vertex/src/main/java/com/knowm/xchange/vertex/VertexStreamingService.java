package com.knowm.xchange.vertex;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.Completable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VertexStreamingService extends JsonNettyStreamingService {

  private static final Logger logger = LoggerFactory.getLogger(VertexStreamingService.class);

  //Channel to use to subscribe to all response
  public static final String ALL_MESSAGES = "all_messages";

  private final AtomicLong reqCounter = new AtomicLong(1);
  private final String apiUrl;

  public VertexStreamingService(String apiUrl) {
    super(apiUrl);
    this.apiUrl = apiUrl;
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) {
    JsonNode type = message.get("type");
    JsonNode productId = message.get("product_id");
    JsonNode subaccount = message.get("subaccount");
    if (type != null) {
      if (productId != null) {
        if (subaccount != null) {
          return type.asText() + "." + productId.asText() + "." + subaccount.asText();
        }
        return type.asText() + "." + productId.asText();
      }
      return type.asText();
    } else {
      return ALL_MESSAGES;
    }

  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) {
    if (Objects.equals(channelName, ALL_MESSAGES)) {
      return null;
    }
    String[] typeAndProduct = channelName.split("\\.");
    long reqId = reqCounter.incrementAndGet();
    return "{\n" +
        "  \"method\": \"subscribe\",\n" +
        "  \"stream\": {\n" +
        "    \"type\": \"" + typeAndProduct[0] + "\"\n" +
        productIdField(typeAndProduct) +
        subAccountField(typeAndProduct) +
        "  },\n" +
        "  \"id\": " + reqId + "\n" +
        "}";
  }

  private static String productIdField(String[] typeAndProduct) {
    return typeAndProduct.length > 1 ? ", \"product_id\": " + typeAndProduct[1] + "\n" : "";
  }

  private String subAccountField(String[] typeAndProduct) {
    if (typeAndProduct.length > 2) {
      return ",\"subaccount\": \"" + typeAndProduct[2] + "\"\n";
    } else {
      return "";
    }
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) {
    if (Objects.equals(channelName, ALL_MESSAGES)) {
      return null;
    }
    String[] typeAndProduct = channelName.split("\\.");
    long reqId = reqCounter.incrementAndGet();
    return "{\n" +
        "  \"method\": \"unsubscribe\",\n" +
        "  \"stream\": {\n" +
        "    \"type\": \"" + typeAndProduct[0] + "\"\n" +
        productIdField(typeAndProduct) +
        subAccountField(typeAndProduct) +
        "  },\n" +
        "  \"id\": " + reqId + "\n" +
        "}";
  }


  @Override
  public Completable disconnect() {
    if (isSocketOpen()) {
      logger.info("Disconnecting " + apiUrl);
      return super.disconnect();
    } else {
      logger.info("Already disconnected " + apiUrl);
      return Completable.complete();
    }
  }
}
