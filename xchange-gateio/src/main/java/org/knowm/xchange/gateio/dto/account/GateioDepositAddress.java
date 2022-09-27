package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.gateio.dto.GateioBaseResponse;

import java.util.List;

public class GateioDepositAddress extends GateioBaseResponse {

  private final String addr;
  private final List<GateioMultiChainAddress> multiChainAddresses;

  /**
   * Constructor
   *
   * @param addr
   * @param result
   * @param message
   * @param multiChainAddresses
   */
  public GateioDepositAddress(
      @JsonProperty("addr") String addr,
      @JsonProperty("result") boolean result,
      @JsonProperty("message") final String message,
      @JsonProperty("multichain_addresses")
          final List<GateioMultiChainAddress> multiChainAddresses) {

    super(result, message);
    this.addr = addr;
    this.multiChainAddresses = multiChainAddresses;
  }

  public String getAddr() {
    return addr;
  }

  public List<GateioMultiChainAddress> getMultiChainAddresses() {
    return multiChainAddresses;
  }

  @Override
  public String toString() {
    return "GateioDepositAddress{"
        + "addr='"
        + addr
        + '\''
        + ", multiChainAddresses="
        + multiChainAddresses
        + '}';
  }
}
