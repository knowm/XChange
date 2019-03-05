package org.knowm.xchange.simulated;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;

@Data
final class BookLevel {
  private final BigDecimal price;
  private final List<BookOrder> orders = new LinkedList<>();
}
