package com.example.java_ifortex_test_task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDeviceTypeCodeDTO {
    private Integer id;
    private Integer deviceTypeCode;
    private Timestamp endedAtUtc;
    private Timestamp startedAtUtc;
    private Integer userId;
}
