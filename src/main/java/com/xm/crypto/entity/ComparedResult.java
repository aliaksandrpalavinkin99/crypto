package com.xm.crypto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComparedResult {
    private NormalizedRange leastNormalizedRange;
    private NormalizedRange greatestNormalizedRange;
}
