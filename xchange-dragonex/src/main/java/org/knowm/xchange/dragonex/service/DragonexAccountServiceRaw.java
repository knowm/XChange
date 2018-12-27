package org.knowm.xchange.dragonex.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dragonex.dto.DragonResult;
import org.knowm.xchange.dragonex.dto.DragonexException;
import org.knowm.xchange.dragonex.dto.Token;
import org.knowm.xchange.dragonex.dto.TokenStatus;
import org.knowm.xchange.dragonex.dto.account.Balance;

public class DragonexAccountServiceRaw extends DragonexBaseService {

  public DragonexAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public Token tokenNew() throws DragonexException, IOException {
    DragonResult<Token> tokenNew =
        exchange
            .dragonexAuthenticated()
            .tokenNew(utcNow(), exchange.signatureCreator(), ContentSHA1);
    return tokenNew.getResult();
  }

  public TokenStatus tokenStatus(String token) throws DragonexException, IOException {
    DragonResult<TokenStatus> tokenStatus =
        exchange
            .dragonexAuthenticated()
            .tokenStatus(utcNow(), token, exchange.signatureCreator(), ContentSHA1);
    return tokenStatus.getResult();
  }

  public List<Balance> userCoins(String token) throws DragonexException, IOException {
    DragonResult<List<Balance>> userCoins =
        exchange
            .dragonexAuthenticated()
            .userCoins(utcNow(), token, exchange.signatureCreator(), ContentSHA1);
    return userCoins.getResult();
  }
}
