package info.bitrich.xchangestream.binance.exceptions;

import info.bitrich.xchangestream.binance.BinanceUserDataChannelImpl;

/**
   * Thrown on calls to {@link BinanceUserDataChannelImpl#getListenKey()} if no channel is currently
   * open.
   *
   * @author Graham Crockford
   */
public class NoActiveChannelException extends Exception {

    private static final long serialVersionUID = -8161003286845820286L;

    public NoActiveChannelException() {
      super();
    }
  }