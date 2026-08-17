package com.autoprocess.common.exception;

/**
 * 可预期的业务异常，例如流程不存在、审批任务重复处理等。
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
