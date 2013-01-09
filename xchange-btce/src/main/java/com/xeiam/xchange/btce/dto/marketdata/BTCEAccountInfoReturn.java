package com.xeiam.xchange.btce.dto.marketdata;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Matija Mazi <br/>
 */
public class BTCEAccountInfoReturn extends BTCEReturn<BTCEAccountInfo> {

  public BTCEAccountInfoReturn(@JsonProperty("success") boolean success, @JsonProperty("return") BTCEAccountInfo value, @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
