package org.knowm.xchange.dragonex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dragonex.dto.DragonResult;
import org.knowm.xchange.dragonex.dto.DragonexException;
import org.knowm.xchange.dragonex.dto.Token;
import org.knowm.xchange.dragonex.dto.TokenStatus;
import org.knowm.xchange.dragonex.dto.account.Balance;
import org.knowm.xchange.dragonex.dto.account.CoinPrepayHistory;
import org.knowm.xchange.dragonex.dto.account.CoinWithdrawHistory;
import org.knowm.xchange.dragonex.dto.account.Withdrawal;
import org.knowm.xchange.dragonex.dto.account.WithdrawalAddress;

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

  public CoinPrepayHistory coinPrepayHistory(long coinId, Long pageNum, Long pageSize)
      throws IOException {
    String token = exchange.getOrCreateToken().token;
    DragonResult<CoinPrepayHistory> coinPrepareHistory =
        exchange
            .dragonexAuthenticated()
            .coinPrepayHistory(
                utcNow(),
                token,
                exchange.signatureCreator(),
                ContentSHA1,
                coinId,
                pageNum,
                pageSize);
    return coinPrepareHistory.getResult();
  }

  public CoinWithdrawHistory coinWithdrawHistory(long coinId, Long pageNum, Long pageSize)
      throws IOException {
    String token = exchange.getOrCreateToken().token;
    DragonResult<CoinWithdrawHistory> coinWithdrawHistory =
        exchange
            .dragonexAuthenticated()
            .coinWithdrawHistory(
                utcNow(),
                token,
                exchange.signatureCreator(),
                ContentSHA1,
                coinId,
                pageNum,
                pageSize);
    return coinWithdrawHistory.getResult();
  }

  public Withdrawal coinWithdrawNew(long coinId, BigDecimal volume, long addressId)
      throws IOException {
    String token = exchange.getOrCreateToken().token;
    DragonResult<Withdrawal> coinWithdrawHistory =
        exchange
            .dragonexAuthenticated()
            .coinWithdrawNew(
                utcNow(),
                token,
                exchange.signatureCreator(),
                ContentSHA1,
                coinId,
                volume,
                addressId);
    return coinWithdrawHistory.getResult();
  }

  public List<WithdrawalAddress> coinWithdrawAddrList(long coinId) throws IOException {
    String token = exchange.getOrCreateToken().token;
    DragonResult<List<WithdrawalAddress>> coinWithdrawHistory =
        exchange
            .dragonexAuthenticated()
            .coinWithdrawAddrList(
                utcNow(), token, exchange.signatureCreator(), ContentSHA1, coinId);
    return coinWithdrawHistory.getResult();
  }
}
