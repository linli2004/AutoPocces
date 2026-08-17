package com.autoprocess.common.util;

import java.util.UUID;

/**
 * Generates string IDs that match VARCHAR primary keys.
 */
public final class IdUtil {
    private IdUtil() {
    }

    public static String nextId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
