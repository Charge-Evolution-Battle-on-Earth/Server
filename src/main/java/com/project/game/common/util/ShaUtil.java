package com.project.game.common.util;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

/**
 * SHA 256 암호화 Util 클래스
 */
public class ShaUtil {

    public static String sha256Encode(String plainText) {
        return Hashing.sha256()
            .hashString(plainText, StandardCharsets.UTF_8)
            .toString();
    }
}
