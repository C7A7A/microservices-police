package com.police.vehiclesui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.police.vehiclesui.GetVehicleRequest;
import com.police.vehiclesui.GetVehicleResponse;
import com.police.vehiclesui.GetVehicleListRequest;
import com.police.vehiclesui.GetVehicleListResponse;

public class VehicleClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(VehicleClient.class);

    public GetVehicleResponse getVehicle(String name) {

        GetVehicleRequest request = new GetVehicleRequest();
        request.setName(name);

        log.info("Requesting for vehicle " + name);

        GetVehicleResponse response = (GetVehicleResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws/vehicles", request,
                        new SoapActionCallback(
                                "http://www.police.org"
                        )
                );

        return response;
    }

    public GetVehicleListResponse getVehicleList(String name) {

        GetVehicleListRequest request = new GetVehicleListRequest();
        request.setName(name);

        log.info("Requesting for list of vehicles " + name);

        GetVehicleListResponse response = (GetVehicleListResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws/vehicles", request,
                        new SoapActionCallback(
                                "http://www.police.org"
                        )
                );

        return response;
    }

}
