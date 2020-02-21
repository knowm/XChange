package org.knowm.xchange.gatehub.dto;

import si.mazi.rescu.ExceptionalReturnContentException;

public class BaseResponse {
  protected BaseResponse() { }

  protected BaseResponse(String errorId) {
    if (errorId != null) {
      throw new ExceptionalReturnContentException(errorId);
    }
  }
}
