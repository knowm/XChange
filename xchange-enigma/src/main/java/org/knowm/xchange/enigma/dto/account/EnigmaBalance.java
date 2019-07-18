package org.knowm.xchange.enigma.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class EnigmaBalance {

  private Map<String, BigDecimal> balances;
}
