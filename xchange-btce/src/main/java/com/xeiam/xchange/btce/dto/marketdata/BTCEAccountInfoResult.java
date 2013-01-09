package com.xeiam.xchange.btce.dto.marketdata;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Matija Mazi <br/>
 */
public class BTCEAccountInfoResult extends BTCEResult<BTCEAccountInfo> {

  public BTCEAccountInfoResult(@JsonProperty("success") boolean success, @JsonProperty("return") BTCEAccountInfo value, @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
