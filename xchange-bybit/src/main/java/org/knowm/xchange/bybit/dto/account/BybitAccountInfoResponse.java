package org.knowm.xchange.bybit.dto.account;

import lombok.Getter;

@Getter
public class BybitAccountInfoResponse {
  private int unifiedMarginStatus;
  private String marginMode;
  private boolean isMasterTrader;
  private String spotHedgingStatus;
  private String updatedTime;
  private String dcpStatus;
  private int timeWindow;
  private int smpGroup;
}
