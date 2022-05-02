package com.underestimatedme.util;

/**
 * <p></p>
 *
 * @author mj
 * @date TestUtil.java v1.0
 */
public class TestUtil {

    private TestUtil() {
    }

    public static boolean equals(Object a, Object b) {
        if (a == null) {
            System.out.println(a + ";;" + b + "=" + (a == b));
            return b == null;
        }
        System.out.println(a + ";;" + b + "=" + a.equals(b));
        return a.equals(b);
    }

    public static void assertIsTrue(boolean condition, String description) {
        if (!condition) {
            throw new IllegalArgumentException(description);
        }
    }
}
