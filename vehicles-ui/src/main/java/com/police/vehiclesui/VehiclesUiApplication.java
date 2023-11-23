package com.police.vehiclesui;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.police.vehiclesui.VehicleClient;

import java.util.List;

@SpringBootApplication
public class VehiclesUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehiclesUiApplication.class, args);
    }

//    @Bean
//    CommandLineRunner lookup(VehicleClient quoteClient) {
//        return args -> {
//            String name = "BMW X5";
//
//            if (args.length > 0) {
//                name = args[0];
//            }
//            GetVehicleResponse response = quoteClient.getVehicle(name);
//            System.err.println(response.getVehicle().getName());
//
//
//            String listName = "chase";
//
//            if (args.length > 0) {
//                listName = args[0];
//            }
//            GetVehicleListResponse responseList = quoteClient.getVehicleList(listName);
//            List<Vehicle> vehicleList = responseList.getVehicles();
//            for (Vehicle vehicle: vehicleList) {
//                System.err.println("name: " + vehicle.getName());
//                System.err.println("type: " + vehicle.getType());
//                System.err.println("capacity: " + vehicle.getCapacity());
//            }
//        };
//    }
}
