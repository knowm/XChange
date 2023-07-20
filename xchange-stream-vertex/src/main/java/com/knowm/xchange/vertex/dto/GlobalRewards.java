package com.knowm.xchange.vertex.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GlobalRewards {

  private int product_id;
  private long uptimes;
  private BigDecimal reward_coefficient;
  private BigDecimal q_scores;
  private BigDecimal maker_volume;
  private BigDecimal taker_volume;
  private BigDecimal maker_fee;
  private BigDecimal taker_fee;
  private BigDecimal maker_tokens;
  private BigDecimal taker_tokens;

}
