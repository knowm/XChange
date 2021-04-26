package info.bitrich.xchangestream.ftx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FtxAuthenticationMessage {

    private final FtxAuthenticationArgs args;

    @JsonProperty("op")
    private final String op = "login";

    public FtxAuthenticationMessage(
            @JsonProperty("args") FtxAuthenticationArgs args) {
        this.args = args;
    }

    public FtxAuthenticationArgs getArgs() {
        return args;
    }

    public String getOp() {
        return op;
    }

    @Override
    public String toString() {
        return "FtxAuthenticationMessage{" +
                "args=" + args +
                ", op='" + op + '\'' +
                '}';
    }

    public static class FtxAuthenticationArgs {

        private final String key;

        private final String sign;

        private final Long time;

        public FtxAuthenticationArgs(
                @JsonProperty("key") String key,@JsonProperty("sign") String sign,@JsonProperty("time") Long time) {
            this.key = key;
            this.sign = sign;
            this.time = time;
        }

        public String getKey() {
            return key;
        }

        public String getSign() {
            return sign;
        }

        public Long getTime() {
            return time;
        }

        @Override
        public String toString() {
            return "FtxAuthenticationArgs{" +
                    "key='" + key + '\'' +
                    ", sign='" + sign + '\'' +
                    ", time=" + time +
                    '}';
        }
    }
}
