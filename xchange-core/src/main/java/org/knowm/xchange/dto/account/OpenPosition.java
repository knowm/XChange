package org.knowm.xchange.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.instrument.Instrument;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OpenPosition implements Serializable {

  private String id;

  /** The instrument */
  private Instrument instrument;

  /** Is this a long or a short position */
  private Type type;

  private MarginMode marginMode;

  /** The size of the position */
  private BigDecimal size;

  /** The average entry price for the position */
  private BigDecimal price;

  /** The estimated liquidation price */
  private BigDecimal liquidationPrice;

  /** The unrealised pnl of the position */
  private BigDecimal unRealisedPnl;

  /** Timestamp of creation */
  private Instant createdAt;

  /** Timestamp of update */
  private Instant updatedAt;

  public enum Type {
    LONG,
    SHORT
  }

  public enum MarginMode {
    CROSS,
    ISOLATED
  }
}
