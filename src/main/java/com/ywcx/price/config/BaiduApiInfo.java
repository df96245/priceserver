package com.ywcx.price.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BaiduApiInfo {

    @Value("#{configProperties['ak']}")
    private String ak;
    
    @Value("#{configProperties['coord_type_input']}")
    private String coordTypeInput;
    
    @Value("#{configProperties['service_id']}")
    private String serviceId;

	public String getAk() {
		return ak;
	}

	public void setAk(String ak) {
		this.ak = ak;
	}

	public String getCoordTypeInput() {
		return coordTypeInput;
	}

	public void setCoordTypeInput(String coordTypeInput) {
		this.coordTypeInput = coordTypeInput;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	

}