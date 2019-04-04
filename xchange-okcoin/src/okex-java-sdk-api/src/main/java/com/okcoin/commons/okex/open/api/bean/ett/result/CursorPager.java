package com.okcoin.commons.okex.open.api.bean.ett.result;

import java.util.List;

/**
 * @author chuping.cui
 * @date 2018/7/5
 */
public class CursorPager<T> {

    private List<T> data;
    private String before;
    private String after;
    private int limit;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
