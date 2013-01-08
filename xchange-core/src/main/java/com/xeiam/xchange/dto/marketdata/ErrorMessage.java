package com.xeiam.xchange.dto.marketdata;

/**
 * <p>
 * Value object to provide the following to XChange feeds:
 * </p>
 * <ul>
 * <li>Unified representation of an error message</li>
 * </ul>
 * 
 * @since 1.3.0  
 */
public class ErrorMessage {

  /**
   * HTTP Status code equivalent
   */
  private int httpStatus;

  /**
   * XChange internal error code
   */
  private int xchangeCode;

  /**
   * Message suitable for an end-user to see
   */
  private String userMessage;

  /**
   * Message for developer to aid in debugging
   */
  private String developerMessage;

  public ErrorMessage(int httpStatus, int xchangeCode, String userMessage, String developerMessage) {

    this.httpStatus = httpStatus;
    this.xchangeCode = xchangeCode;
    this.userMessage = userMessage;
    this.developerMessage = developerMessage;
  }

  public int getHttpStatus() {

    return httpStatus;
  }

  public int getXchangeCode() {

    return xchangeCode;
  }

  public String getUserMessage() {

    return userMessage;
  }

  public String getDeveloperMessage() {

    return developerMessage;
  }
}
