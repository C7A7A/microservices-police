package com.police.emergencies.data;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TypeConverter implements Converter<String, Type> {

    @Override
    public Type convert(String data) {
        return Type.valueOf(data.toUpperCase());
    }

}
