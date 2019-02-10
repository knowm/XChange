package org.knowm.xchange.bankera.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class BankeraUserInfoTest {
  @Test
  public void jsonMapperTest() throws IOException {
    InputStream is =
        getClass().getResourceAsStream("/org/knowm/xchange/bankera/dto/account/user_info.json");
    ObjectMapper mapper = new ObjectMapper();
    final BankeraUserInfo userInfo = mapper.readValue(is, BankeraUserInfo.class);
    assertThat(userInfo).isNotNull();
    assertThat(userInfo.getUser()).isNotNull();
    BankeraUser user = userInfo.getUser();
    assertThat(user.getId()).isEqualTo(7);
    assertThat(user.getWallets()).isNotEmpty().hasSize(5);

    List<BankeraWallet> wallets = user.getWallets();

    wallets.stream()
        .forEach(
            w -> {
              assertThat(w.getBalance()).isEqualTo("10.000000000000000000000000000000");
              assertThat(w.getTotal()).isEqualTo("30.000000000000000000000000000000");
              assertThat(w.getReserved()).isEqualTo("20.000000000000000000000000000000");
              assertThat(w.getCurrency()).isNotNull();
              assertThat(Arrays.asList("BTC", "ETH", "BNK", "DASH", "XEM"))
                  .contains(w.getCurrency());
            });
  }
}
