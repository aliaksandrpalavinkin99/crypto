package com.xm.crypto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComparedResultDTO {
    private NormalizedRangeDTO leastPrice;
    private NormalizedRangeDTO greatestPrice;
}
