package org.knowm.xchange.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import org.knowm.xchange.instrument.Instrument;

public class OpenPosition implements Serializable {
  /** The instrument */
  private final Instrument instrument;
  /** Is this a long or a short position */
  private final Type type;
  /** The size of the position */
  private final BigDecimal size;
  /** The avarage entry price for the position */
  private final BigDecimal price;

  public OpenPosition(
      Instrument instrument, Type type, BigDecimal size, BigDecimal price) {
    this.instrument = instrument;
    this.type = type;
    this.size = size;
    this.price = price;
  }

  public Instrument getInstrument() {
    return instrument;
  }

  public Type getType() {
    return type;
  }

  public BigDecimal getSize() {
    return size;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final OpenPosition that = (OpenPosition) o;
    return Objects.equals(instrument, that.instrument)
        && type == that.type
        && Objects.equals(size, that.size)
        && Objects.equals(price, that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(instrument, type, size, price);
  }

  @Override
  public String toString() {
    return "OpenPosition{"
        + "instrument="
        + instrument
        + ", type="
        + type
        + ", size="
        + size
        + ", price="
        + price
        + '}';
  }

  public enum Type {
    LONG,
    SHORT;
  }

  public static class Builder {
    private Instrument instrument;
    private Type type;
    private BigDecimal size;
    private BigDecimal price;

    public static Builder from(OpenPosition openPosition) {
      return new Builder()
          .instrument(openPosition.getInstrument())
          .type(openPosition.getType())
          .size(openPosition.getSize())
          .price(openPosition.getPrice());
    }

    public Builder instrument(final Instrument instrument) {
      this.instrument = instrument;
      return this;
    }

    public Builder type(final Type type) {
      this.type = type;
      return this;
    }

    public Builder size(final BigDecimal size) {
      this.size = size;
      return this;
    }

    public Builder price(final BigDecimal price) {
      this.price = price;
      return this;
    }

    public OpenPosition build() {
      return new OpenPosition(instrument, type, size, price);
    }
  }
}
