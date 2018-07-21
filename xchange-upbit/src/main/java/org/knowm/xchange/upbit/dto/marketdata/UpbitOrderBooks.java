package org.knowm.xchange.upbit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.upbit.service.UpbitArrayOrMessageDeserializer;

/** @author interwater */
@JsonDeserialize(using = UpbitOrderBooks.UpbitOrderbooksDeserializer.class)
public class UpbitOrderBooks {

  private final UpbitOrderBook[] upbitOrderBooks;

  public UpbitOrderBooks(@JsonProperty() UpbitOrderBook[] upbitOrderBooks) {
    this.upbitOrderBooks = upbitOrderBooks;
  }

  public UpbitOrderBook[] getUpbitOrderBooks() {
    return upbitOrderBooks;
  }

  static class UpbitOrderbooksDeserializer
      extends UpbitArrayOrMessageDeserializer<UpbitOrderBook, UpbitOrderBooks> {
    public UpbitOrderbooksDeserializer() {
      super(UpbitOrderBook.class, UpbitOrderBooks.class);
    }
  }
}
