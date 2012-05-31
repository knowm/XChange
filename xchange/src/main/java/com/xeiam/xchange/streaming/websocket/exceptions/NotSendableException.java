package com.xeiam.xchange.streaming.websocket.exceptions;

/**
 * Exception to indicate that data could not be sent (invalid format etc)
 */
public class NotSendableException extends RuntimeException {

  public NotSendableException() {
  }

  public NotSendableException(String message) {
    super(message);
  }

  public NotSendableException(Throwable cause) {
    super(cause);
  }

  public NotSendableException(String message, Throwable cause) {
    super(message, cause);
  }

}
