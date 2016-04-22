package org.knowm.xchange.mexbt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.exceptions.ExchangeException;

public class MeXBTException extends ExchangeException {

  private static final long serialVersionUID = 2015061801L;

  public MeXBTException(@JsonProperty("rejectReason") String rejectReason) {
    super(rejectReason);
  }

}
