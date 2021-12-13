package org.knowm.xchange.raydium.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.raydium.RaydiumBaseService;
import io.github.makarid.solanaj.core.PublicKey;
import org.knowm.xchange.raydium.dto.FarmListDto;
import org.knowm.xchange.raydium.dto.LpListDto;
import org.knowm.xchange.raydium.dto.TokenListDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class RaydiumAccountServiceRaw extends RaydiumBaseService {

  public RaydiumAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public TokenListDto getTokenList() throws IOException {
    return raydium.getTokenList();
  }

  public LpListDto getLpList() throws IOException {
    return raydium.getLpList();
  }

  public FarmListDto getFarmList() throws IOException {
    return raydium.getFarmList();
  }
}
