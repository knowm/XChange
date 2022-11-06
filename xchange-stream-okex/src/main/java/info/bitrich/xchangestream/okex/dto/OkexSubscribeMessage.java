package info.bitrich.xchangestream.okex.dto;

import info.bitrich.xchangestream.okex.dto.enums.OkexInstType;
import java.util.LinkedList;
import java.util.List;
import lombok.*;

@Data
@RequiredArgsConstructor
public class OkexSubscribeMessage {
  private String op;
  List<SubscriptionTopic> args = new LinkedList<>();

  @Data
  @AllArgsConstructor
  public static class SubscriptionTopic {
    private String channel;

    private OkexInstType instType;

    private String uly;

    private String instId;
  }
}
