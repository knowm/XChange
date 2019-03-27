/** Copyright 2019 Mek Global Limited. */
package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** Created by chenshiwei on 2019/1/18. */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderBookResponse extends OrderBook {

  private String sequence;

  private long time;
}
