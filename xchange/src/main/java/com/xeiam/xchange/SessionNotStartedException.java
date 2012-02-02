package com.xeiam.xchange;

/**
 *  <p>Exception to provide the following to {@link Session}:</p>
 *  <ul>
 *  <li>Indication that the session could not start</li>
 *  </ul>
 *
 * @since 0.0.1
 *         
 */
public class SessionNotStartedException extends RuntimeException {

  public SessionNotStartedException(String s) {
    super(s);
  }

  public SessionNotStartedException(String s, Throwable throwable) {
    super(s, throwable);
  }
}
