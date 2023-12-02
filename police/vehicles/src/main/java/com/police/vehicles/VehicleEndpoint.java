package com.police.vehicles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.police.vehicles.data.GetVehicleRequest;
import com.police.vehicles.data.GetVehicleResponse;
import com.police.vehicles.data.GetVehicleListRequest;
import com.police.vehicles.data.GetVehicleListResponse;


@Endpoint
public class VehicleEndpoint {
    public static final String NAMESPACE_URI = "http://www.police.org";

    private final VehiclesService vehiclesService;

    @Autowired
    public VehicleEndpoint(VehiclesService vehiclesService) {
        this.vehiclesService = vehiclesService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getVehicleRequest")
    @ResponsePayload
    public GetVehicleResponse getVehicle(@RequestPayload GetVehicleRequest request) {
        GetVehicleResponse response = new GetVehicleResponse();
        response.setVehicle(vehiclesService.getVehicle(request.getName()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getVehicleListRequest")
    @ResponsePayload
    public GetVehicleListResponse getVehicleList(@RequestPayload GetVehicleListRequest request) {
        GetVehicleListResponse response = new GetVehicleListResponse();
        response.setVehicles(vehiclesService.getVehicles(request.getName()));

        return response;
    }
}
