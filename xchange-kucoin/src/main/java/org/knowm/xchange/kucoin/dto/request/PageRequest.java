/** Copyright 2019 Mek Global Limited. */
package org.knowm.xchange.kucoin.dto.request;

import lombok.Data;

/** Created by zicong.lu on 2018/12/14. */
@Data
public class PageRequest {

  private int currentPage = 1;

  private int pageSize = 10;
}
