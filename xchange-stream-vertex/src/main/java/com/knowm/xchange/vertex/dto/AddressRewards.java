package com.knowm.xchange.vertex.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class AddressRewards {

  private int product_id;
  private BigDecimal q_score;
  private BigDecimal sum_q_min;
  private long uptime;
  private BigDecimal maker_volume;
  private BigDecimal taker_volume;
  private BigDecimal maker_fee;
  private BigDecimal taker_fee;
  private BigDecimal maker_tokens;
  private BigDecimal taker_tokens;
  private BigDecimal rebates;


}
