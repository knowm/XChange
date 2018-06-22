package info.bitrich.xchangestream.fcoin.dto;

import java.util.Arrays;
import java.util.List;

public class FCoinMessage {

    private final String id;
    private final String cmd;
    private final List<String> args;

    public FCoinMessage(String id, String cmd, String ... args) {
        this.id = id;
        this.cmd = cmd;
        this.args = Arrays.asList(args);
    }

    public String getId() {
        return id;
    }

    public String getCmd() {
        return cmd;
    }

    public List<String> getArgs() {
        return args;
    }

}
