package org.knowm.xchange.bittrex.service.batch;

import java.util.Map;
import lombok.Data;

@Data
public class BatchOrderResponse {
  private Map payload;
  private String status;
}
