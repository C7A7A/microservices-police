package com.police.emergencies;

import com.police.emergencies.data.Emergency;
import com.police.emergencies.data.Type;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class EmergenciesService {
    public String getEmergencyResult(Emergency emergency) {
        Type type = emergency.getType();

        return decideEmergencyResult(type);
    }

    private String decideEmergencyResult(Type type) {
        if (type == Type.BAD_PARKING || type == Type.DISTURBANCE_OF_ORDER) {
            return "standard";
        }

        if (type == Type.RECKLESS_DRIVING) {
            return "chase";
        }

        if (type == Type.THEFT) {
            return "prisoner";
        }

        if (type == Type.BEATING) {
            return "beating";
        }

        if (type == Type.SHOOTING || type == Type.TERRORISM) {
            return "high risk";
        }

        return "standard";
    }
}
