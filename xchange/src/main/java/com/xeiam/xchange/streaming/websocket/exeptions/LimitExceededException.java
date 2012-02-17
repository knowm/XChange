package com.xeiam.xchange.streaming.websocket.exeptions;

import com.xeiam.xchange.streaming.websocket.CloseFrame;

public class LimitExceededException extends InvalidDataException {

  public LimitExceededException() {
    super(CloseFrame.TOOBIG);
  }

  public LimitExceededException(String s) {
    super(CloseFrame.TOOBIG, s);
  }

}
