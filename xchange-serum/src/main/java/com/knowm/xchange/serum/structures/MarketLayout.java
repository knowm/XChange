package com.knowm.xchange.serum.structures;

import com.knowm.xchange.serum.dto.PublicKey;

public abstract class MarketLayout extends Struct {

  public static StructDecoder<MarketStat> getLayout(final PublicKey programId) {
    if (getLayoutVersion(programId) == 1) {
      return MarketStatLayoutV1.DECODER;
    }
    return MarketStatLayoutV2.DECODER;
  }
}
