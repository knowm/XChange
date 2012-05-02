package com.xeiam.xchange.streaming.socketio;

/**
 * <p>
 * Enum to provide the following to {@link IOConnection}:
 * </p>
 * <ul>
 * <li>Named state for socket.io protocol</li>
 * </ul>
 */
public enum IOState {

  STATE_INIT,

  STATE_HANDSHAKE,

  STATE_CONNECTING,

  STATE_READY,

  STATE_INTERRUPTED,

  STATE_INVALID,
  // End of enum
  ;

}
