package org.knowm.xchange.bibox.dto;

import java.util.List;

/**
 * Result of batched calls (all POST requests)
 * 
 * @author odrotleff
 * @param <R>
 */
public class BiboxMultipleResponses<T> extends BiboxResponse<List<BiboxResponse<T>>> {
}
