package info.bitrich.xchangestream.lgo.domain;

import java.util.Date;

public class LgoAckOrderEvent extends LgoOrderEvent {

  protected LgoAckOrderEvent(String type, String orderId, Date time) {
    super(type, orderId, time);
  }
}
