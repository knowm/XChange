package org.knowm.xchange.bittrex.v1.dto.account;

import java.util.List;

public class BittrexBalancesResponse {

  private String message;
  private List<BittrexBalance> result;
  private boolean success;

  public String getMessage() {

    return this.message;
  }

  public void setMessage(String message) {

    this.message = message;
  }

  public List<BittrexBalance> getResult() {

    return this.result;
  }

  public void setResult(List<BittrexBalance> result) {

    this.result = result;
  }

  public boolean getSuccess() {

    return this.success;
  }

  public void setSuccess(boolean success) {

    this.success = success;
  }

  @Override
  public String toString() {

    return "BittrexBalancesResponse [message=" + message + ", result=" + result + ", success=" + success + "]";
  }

}