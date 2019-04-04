package com.okcoin.commons.okex.open.api.bean.futures;

/**
 * Cursor pagination   <br/>
 * Created by Tony Tian on 2018/2/26 19:55. <br/>
 */
public class CursorPageParams {
    /**
     * Request page before (newer) this pagination id.
     * eg: before=2, page number = 1.
     */
    protected int before;
    /**
     * Request page after (older) this pagination id.
     * eg: after=2, page number = 3.
     */
    protected int after;
    /**
     * Number of results per request. Maximum 100. (default 100)
     */
    protected int limit;

    public int getBefore() {
        return before;
    }

    public void setBefore(int before) {
        this.before = before;
    }

    public int getAfter() {
        return after;
    }

    public void setAfter(int after) {
        this.after = after;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
