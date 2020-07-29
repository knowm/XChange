package org.knowm.xchange.bittrex.dto.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.knowm.xchange.bittrex.BittrexConstants;
import si.mazi.rescu.HttpResponseAware;

@Data
public class BittrexBalances extends ArrayList<BittrexBalance> implements HttpResponseAware {

  private Map<String, List<String>> headers;

  @Override
  public void setResponseHeaders(Map<String, List<String>> headers) {
    this.headers = headers;
  }

  @Override
  public Map<String, List<String>> getResponseHeaders() {
    return headers;
  }

  public String getSequence() {
    return getResponseHeaders().get(BittrexConstants.SEQUENCE).get(0);
  }
}
