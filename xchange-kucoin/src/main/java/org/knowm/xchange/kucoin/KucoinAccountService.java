package org.knowm.xchange.kucoin;

import static java.util.stream.Collectors.toList;

import com.kucoin.sdk.rest.response.AccountBalancesResponse;
import java.io.IOException;
import java.util.List;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;

public class KucoinAccountService extends KucoinAccountServiceRaw implements AccountService {

  KucoinAccountService(KucoinExchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<AccountBalancesResponse> accounts = getKucoinAccounts();
    return new AccountInfo(
        accounts.stream()
            .map(AccountBalancesResponse::getType)
            .distinct()
            .map(
                type ->
                    new Wallet(
                        type,
                        accounts.stream()
                            .filter(a -> a.getType().equals(type))
                            .map(KucoinAdapters::adaptBalance)
                            .collect(toList())))
            .collect(toList()));
  }
}
