package info.bitrich.xchangestream.okex.dto;

import java.util.List;
import lombok.*;
import org.knowm.xchange.okex.dto.OkexInstType;

@Data
@AllArgsConstructor
public class OkexSubscribeMessage {
  private final String op;
  private final List<SubscriptionTopic> args;

  @Data
  @AllArgsConstructor
  public static class SubscriptionTopic {
    private final String channel;

    private final OkexInstType instType;

    private final String uly;

    private final String instId;
  }
}
