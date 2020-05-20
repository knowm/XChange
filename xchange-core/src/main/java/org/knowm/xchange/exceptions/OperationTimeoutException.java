package org.knowm.xchange.exceptions;

/**
 * Exception indicating that the operation took to long and the exchange decided to timeout it
 *
 * <p>This exception should only be thrown in situations in which you know that the operation was
 * disregarded due to the timeout. This is in contrast to timeouts like {@link
 * java.net.SocketTimeoutException} where despite the exception the request might have been
 * compleated on the server.
 */
public class OperationTimeoutException extends OrderNotValidException {

  public OperationTimeoutException() {
    super("Operation took to long and the exchange decided to timeout it");
  }

  public OperationTimeoutException(String message) {
    super(message);
  }

  public OperationTimeoutException(String message, Throwable cause) {
    super(message, cause);
  }
}
