package org.knowm.xchange.dsx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DsxException extends RuntimeException {

  private DsxError dsxError;

  public DsxException(@JsonProperty("error") DsxError dsxError) {

    super(dsxError.getMessage());
    this.dsxError = dsxError;
  }

  public DsxError getDsxError() {
    return dsxError;
  }
}
