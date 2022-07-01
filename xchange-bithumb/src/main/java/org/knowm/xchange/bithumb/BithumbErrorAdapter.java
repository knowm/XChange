package org.knowm.xchange.bithumb;

import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.InternalServerException;

public class BithumbErrorAdapter {

  public static ExchangeException adapt(BithumbException e) {
    final String message = e.getMessage();
    switch (e.getStatus()) {
      case "5100":
      case "5200": // Not Member
      case "5300": // Invalid Apikey
      case "5302":
        return new ExchangeSecurityException(message);
      case "5500": // Invalid Parameter
        return new ExchangeException(message);
      case "5400": // Database Fail
        return new InternalServerException(message);
      case "5600": // 상황 별 에러 메시지 출력
      default:
        return new ExchangeException(message);
    }
  }
}
