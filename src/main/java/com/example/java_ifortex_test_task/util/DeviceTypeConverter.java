package com.example.java_ifortex_test_task.util;

import com.example.java_ifortex_test_task.entity.DeviceType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DeviceTypeConverter implements AttributeConverter<DeviceType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DeviceType type) {
        if (type == null) return null;
        return type.getCode();
    }

    @Override
    public DeviceType convertToEntityAttribute(Integer code) {
        if (code == null) return null;
        return DeviceType.fromCode(code);
    }
}
