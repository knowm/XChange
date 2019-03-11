package org.knowm.xchange.bibox.dto.marketdata;

import org.knowm.xchange.bibox.dto.BiboxCommand;

public class BiboxOrderBookCommand extends BiboxCommand<BiboxOrderBookCommandBody> {

  public BiboxOrderBookCommand(String pair, Integer size) {
    super("api/depth", new BiboxOrderBookCommandBody(pair, size));
  }
}
