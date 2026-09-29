package org.knowm.xchange.gateio.dto.trade;

public enum GateioTimeInForce {
  GTC, // GoodTillCancelled
  IOC, //ImmediateOrCancelled, taker only
  POC, //PendingOrCancelled, makes a post-only order that always enjoys a maker fee
  FOK; // FillOrKill, fill either completely or none
}
