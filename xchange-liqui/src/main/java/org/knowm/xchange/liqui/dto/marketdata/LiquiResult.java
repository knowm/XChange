package org.knowm.xchange.liqui.dto.marketdata;

public class LiquiResult<V> {

    private final V result;

    public LiquiResult(V result) {
        this.result = result;
    }

    public V getResult() {
        return this.result;
    }

    @Override
    public String toString() {
        return "LiquiResult{" +
                "result=" + this.result +
                '}';
    }
}
