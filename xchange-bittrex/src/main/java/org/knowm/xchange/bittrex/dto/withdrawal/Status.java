package org.knowm.xchange.bittrex.dto.withdrawal;

public enum Status {
  REQUESTED,
  AUTHORIZED,
  PENDING,
  COMPLETED,
  ERROR_INVALID_ADDRESS,
  CANCELLED,
  NEW
}
