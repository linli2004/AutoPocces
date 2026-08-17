package com.autoprocess.engine;

import java.util.Map;

/**
 * 节点执行结果，携带输出数据、分支结果以及是否需要暂停等待。
 */
public record NodeResult(boolean waiting, String branch, Map<String, Object> output) {
    public static NodeResult next(Map<String, Object> output) {
        return new NodeResult(false, null, output);
    }

    public static NodeResult branch(String branch, Map<String, Object> output) {
        return new NodeResult(false, branch, output);
    }

    public static NodeResult waiting(Map<String, Object> output) {
        return new NodeResult(true, null, output);
    }
}
