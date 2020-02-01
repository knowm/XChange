package org.knowm.xchange.enigma.dto.account;

import java.math.BigDecimal;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EnigmaBalance {

  private Map<String, BigDecimal> balances;
}
