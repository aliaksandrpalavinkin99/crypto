package com.xm.crypto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComparedResult {
    private NormalizedRange leastNormalizedRange;
    private NormalizedRange greatestNormalizedRange;
}
