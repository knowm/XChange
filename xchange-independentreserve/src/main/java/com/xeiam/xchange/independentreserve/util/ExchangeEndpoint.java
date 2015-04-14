package com.xeiam.xchange.independentreserve.util;

/**
 * Author: Kamil Zbikowski
 * Date: 4/13/15
 */
public enum ExchangeEndpoint {
    GET_ACCOUNTS("GetAccounts");

    private String endpointName;

    ExchangeEndpoint(String endpointName) {
        this.endpointName = endpointName;
    }

    public final static String getUrlBasingOnEndpoint(String sslUri, ExchangeEndpoint endpoint){
        return sslUri+"/Private/"+endpoint.endpointName;

    }
}
