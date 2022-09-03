package com.xm.crypto.dto;

import lombok.Data;

@Data
public class ComparedResultDTO {
    private NormalizedRangeDTO leastPrice;
    private NormalizedRangeDTO greatestPrice;
}
