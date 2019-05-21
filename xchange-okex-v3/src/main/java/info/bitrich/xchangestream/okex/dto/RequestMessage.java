package info.bitrich.xchangestream.okex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

public class RequestMessage {

    public enum Operation {
        SUBSCRIBE, UNSUBSCRIBE, LOGIN;

        @JsonValue
        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    private Operation op;

    private List<String> args;

    public RequestMessage(@JsonProperty("op") Operation op, @JsonProperty("args") List<String> args) {
        this.op = op;
        this.args = args;
    }

    public Operation getOp() {
        return op;
    }

    public List<String> getArgs() {
        return args;
    }
}
