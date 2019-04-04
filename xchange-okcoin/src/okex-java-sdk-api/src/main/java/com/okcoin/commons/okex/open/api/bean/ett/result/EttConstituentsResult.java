package com.okcoin.commons.okex.open.api.bean.ett.result;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author chuping.cui
 * @date 2018/7/5
 */
public class EttConstituentsResult {
    private String ett;
    private BigDecimal net_value;
    private List<EttConstituents> constituents;

    public String getEtt() {
        return ett;
    }

    public void setEtt(String ett) {
        this.ett = ett;
    }

    public BigDecimal getNet_value() {
        return net_value;
    }

    public void setNet_value(BigDecimal net_value) {
        this.net_value = net_value;
    }

    public List<EttConstituents> getConstituents() {
        return constituents;
    }

    public void setConstituents(List<EttConstituents> constituents) {
        this.constituents = constituents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        EttConstituentsResult that = (EttConstituentsResult)o;
        return Objects.equals(ett, that.ett) &&
            Objects.equals(net_value, that.net_value) &&
            Objects.equals(constituents, that.constituents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ett, net_value, constituents);
    }

    @Override
    public String toString() {
        return "EttConstituentsResult{" +
            "ett='" + ett + '\'' +
            ", net_value=" + net_value +
            ", constituents=" + constituents +
            '}';
    }
}
