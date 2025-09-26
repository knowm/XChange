package info.bitrich.xchangestream.okex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.knowm.xchange.okex.dto.OkexInstType;

@Data
@AllArgsConstructor
public class OkexSubscriptionTopic {
  private final String channel;

  private final OkexInstType instType;

  private final String uly;

  private final String instId;
}
