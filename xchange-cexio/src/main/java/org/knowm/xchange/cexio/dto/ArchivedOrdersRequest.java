package org.knowm.xchange.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArchivedOrdersRequest extends CexIORequest {

  @JsonProperty("limit")
  public final Integer limit;

  @JsonProperty("dateFrom")
  public final Long dateFrom;

  @JsonProperty("dateTo")
  public final Long dateTo;

  @JsonProperty("lastTxDateFrom")
  public final Long lastTxDateFrom;

  @JsonProperty("lastTxDateTo")
  public final Long lastTxDateTo;

  @JsonProperty("status")
  public final String status;

  public ArchivedOrdersRequest(
      Integer limit,
      Long dateFrom,
      Long dateTo,
      Long lastTxDateFrom,
      Long lastTxDateTo,
      String status) {
    super();

    this.limit = limit;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.lastTxDateFrom = lastTxDateFrom;
    this.lastTxDateTo = lastTxDateTo;
    this.status = status;
  }
}
