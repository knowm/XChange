package org.knowm.xchange.stream.wrapper;

import lombok.Getter;

enum MarketDataType {

  TICKER(PollBehaviour.POLL_AS_FALLBACK),
  ORDERBOOK(PollBehaviour.POLL_AS_FALLBACK),
  TRADES(PollBehaviour.POLL_AS_FALLBACK),
  ORDER(PollBehaviour.POLL_ALWAYS),
  USER_TRADE(PollBehaviour.POLL_ALWAYS),
  BALANCE(PollBehaviour.POLL_ALWAYS);

  @Getter
  private final PollBehaviour pollBehaviour;

  MarketDataType(PollBehaviour pollBehaviour) {
    this.pollBehaviour = pollBehaviour;
  }

  enum PollBehaviour {
    POLL_ALWAYS,
    POLL_AS_FALLBACK
  }

}

