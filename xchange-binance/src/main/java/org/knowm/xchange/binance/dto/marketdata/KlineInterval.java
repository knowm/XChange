package org.knowm.xchange.binance.dto.marketdata;

public enum KlineInterval {
    _1m, _3m, _5m, _15m, _30m           // minutes
    , _1h, _2h, _4h, _6h, _8h, _12h     // hours
    , _1d, _3d                          // days 
    , _1w                               // weeks
    , _1M;                              // months
    
    @Override
    public String toString() {
        return name().substring(1);     // get rid of leading underscore
    }
    
    
}
