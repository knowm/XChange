package org.knowm.xchange.kucoin.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class WebsocketResponse {
  private String token;
  private List<InstanceServer> instanceServers;

  @Data
  private static class InstanceServer {
    private String endpoint;
    private boolean encrypt;
    private String protocol;
    private int pingInternal;
    private int pingTimeout;
  }
}
