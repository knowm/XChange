package org.knowm.xchange.coindcx.dto;

import java.util.Date;
import java.util.UUID;

public class CoindcxOrderStatusRequest {

	private UUID id;
	private long timestamp;
	
	public CoindcxOrderStatusRequest(UUID id) {
		 this.id=id;
		 this.timestamp=new Date().getTime();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "CoindcxOrderStatusRequest [id=" + id + ", timestamp=" + timestamp + "]";
	}

}
