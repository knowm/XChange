package org.knowm.xchange.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.instrument.Instrument;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ToString
public class OpenPosition implements Serializable {
  /** The instrument */
  private final Instrument instrument;
  /** Is this a long or a short position */
  private final Type type;
  /** The size of the position */
  private final BigDecimal size;
  /** The average entry price for the position */
  @JsonIgnore private final BigDecimal price;
  /** The estimatedLiquidationPrice */
  @JsonIgnore private final BigDecimal liquidationPrice;

  /** The unrealised pnl of the position */
  @JsonIgnore private final BigDecimal unRealisedPnl;

  public OpenPosition(
      @JsonProperty("instrument") Instrument instrument,
      @JsonProperty("type") Type type,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("liquidationPrice") BigDecimal liquidationPrice,
      @JsonProperty("unRealisedPnl") BigDecimal unRealisedPnl) {
    this.instrument = instrument;
    this.type = type;
    this.size = size;
    this.price = price;
    this.liquidationPrice = liquidationPrice;
    this.unRealisedPnl = unRealisedPnl;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final OpenPosition that = (OpenPosition) o;
    return Objects.equals(instrument, that.instrument)
        && type == that.type
        && Objects.equals(size, that.size)
        && Objects.equals(price, that.price)
        && Objects.equals(liquidationPrice, that.liquidationPrice)
        && Objects.equals(unRealisedPnl, that.unRealisedPnl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(instrument, type, size, price, liquidationPrice, unRealisedPnl);
  }

  public enum Type {
    LONG,
    SHORT
  }

  public static class Builder {
    private Instrument instrument;
    private Type type;
    private BigDecimal size;
    private BigDecimal price;
    private BigDecimal liquidationPrice;
    private BigDecimal unRealisedPnl;

    public static Builder from(OpenPosition openPosition) {
      return new Builder()
          .instrument(openPosition.getInstrument())
          .type(openPosition.getType())
          .size(openPosition.getSize())
          .liquidationPrice(openPosition.getLiquidationPrice())
          .unRealisedPnl(openPosition.getUnRealisedPnl())
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

    public Builder liquidationPrice(final BigDecimal liquidationPrice) {
      this.liquidationPrice = liquidationPrice;
      return this;
    }

    public Builder unRealisedPnl(final BigDecimal unRealisedPnl) {
      this.unRealisedPnl = unRealisedPnl;
      return this;
    }

    public OpenPosition build() {
      return new OpenPosition(instrument, type, size, price, liquidationPrice, unRealisedPnl);
    }
  }
}
