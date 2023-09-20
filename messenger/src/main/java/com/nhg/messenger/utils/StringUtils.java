package com.nhg.messenger.utils;

public class StringUtils {

    public static boolean startsWithAny(String s, String... prefixes) {
        for (int i = 0; i < prefixes.length; i++) {
            if (s.startsWith(prefixes[i])) {
                return true;
            }
        }
        return false;
    }
}
