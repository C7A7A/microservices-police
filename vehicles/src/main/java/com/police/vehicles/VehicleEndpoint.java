package com.police.vehicles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import org.police.GetVehicleRequest;
import org.police.GetVehicleResponse;
import org.police.GetVehicleListRequest;
import org.police.GetVehicleListResponse;


@Endpoint
public class VehicleEndpoint {
    private static final String NAMESPACE_URI = "http://www.police.org";

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleEndpoint(VehicleRepository VehicleRepository) {
        this.vehicleRepository = VehicleRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getVehicleRequest")
    @ResponsePayload
    public GetVehicleResponse getVehicle(@RequestPayload GetVehicleRequest request) {
        GetVehicleResponse response = new GetVehicleResponse();
        response.setVehicle(vehicleRepository.findVehicle(request.getName()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getVehicleListRequest")
    @ResponsePayload
    public GetVehicleListResponse getVehicleList(@RequestPayload GetVehicleListRequest request) {
        GetVehicleListResponse response = new GetVehicleListResponse();
        response.setVehicles(vehicleRepository.findVehicles(request.getName()));

        return response;
    }
}