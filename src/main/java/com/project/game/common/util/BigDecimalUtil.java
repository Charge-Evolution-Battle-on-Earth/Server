package com.project.game.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {

    public static int multiplyAndRound(int intValue, BigDecimal decimalValue) {
        BigDecimal resultDecimal = decimalValue.multiply(BigDecimal.valueOf(intValue));
        return resultDecimal.setScale(0, RoundingMode.HALF_UP).intValue();
    }
}
