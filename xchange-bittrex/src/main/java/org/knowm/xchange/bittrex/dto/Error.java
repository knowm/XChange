package org.knowm.xchange.bittrex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Error {
  private String code;
  private String detail;
  private Object data;
}
