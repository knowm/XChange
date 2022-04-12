package info.bitrich.xchangestream.okcoin.dto;

import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.okcoin.OkxStreamingService;

public class OkxStreamingTradeService implements StreamingTradeService {

    private final OkxStreamingService service;


    public OkxStreamingTradeService(OkxStreamingService service) {
        this.service = service;
    }
}
