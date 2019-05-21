package info.bitrich.xchangestream.okex.dto;

import java.util.List;

public class RequestMessage {

    public enum Operation {
        SUBSCRIBE, UNSUBSCRIBE, LOGIN;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    private Operation op;

    private List<String> args;

    public RequestMessage(Operation op, List<String> args) {
        this.op = op;
        this.args = args;
    }

}
