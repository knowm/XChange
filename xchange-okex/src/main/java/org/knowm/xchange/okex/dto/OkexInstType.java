package org.knowm.xchange.okex.dto;

import org.knowm.xchange.service.marketdata.params.Params;

public enum OkexInstType implements Params {

    SPOT, MARGIN, SWAP, FUTURES, OPTION, ANY
}
