package info.bitrich.xchangestream.okex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class OkexLoginMessage {
    private String op = "login";

    List<LoginArg> args = new LinkedList<>();

    @Data
    @AllArgsConstructor
    public static class LoginArg {
        private String apiKey;
        private String passphrase;
        // Unix Epoch time, the unit is seconds
        private String timestamp;
        // https://www.okx.com/docs-v5/en/#websocket-api-login
        private String sign;
    }
}
