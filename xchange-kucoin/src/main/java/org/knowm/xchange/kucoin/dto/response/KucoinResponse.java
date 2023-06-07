/** Copyright 2019 Mek Global Limited. */
package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.Data;
import si.mazi.rescu.ExceptionalReturnContentException;

/** Created by zicong.lu on 2018/12/14. */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KucoinResponse<R> implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final String SUCCESS_CODE = "200000";
  private String code;
  private String msg;

  private R data;

  public boolean isSuccessful() {
    return SUCCESS_CODE.equals(this.code);
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return msg;
  }

  public void setCode(String code) {
    if (!SUCCESS_CODE.equals(code)) {
      throw new ExceptionalReturnContentException(code);
    }
    this.code = code;
  }


}
