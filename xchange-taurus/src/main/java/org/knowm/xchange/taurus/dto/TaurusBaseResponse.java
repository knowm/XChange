package org.knowm.xchange.taurus.dto;

import si.mazi.rescu.ExceptionalReturnContentException;

public class TaurusBaseResponse {

  protected TaurusBaseResponse(Object error) {
    if (error != null) {
      throw new ExceptionalReturnContentException("Error returned: " + error);
    }
  }
}
