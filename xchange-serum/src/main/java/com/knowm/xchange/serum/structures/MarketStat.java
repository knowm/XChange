package com.knowm.xchange.serum.structures;

import com.knowm.xchange.serum.dto.PublicKey;
import com.knowm.xchange.serum.structures.AccountFlagsLayout.AccountFlags;

public interface MarketStat {

  AccountFlags getAccountFlags();

  PublicKey getOwnAddress();

  PublicKey baseMint();

  PublicKey quoteMint();

  PublicKey getBids();

  PublicKey getAsks();

  PublicKey getEventQueue();

  PublicKey getRequestQueue();

  long getBaseLotSize();

  long getQuoteLotSize();
}
