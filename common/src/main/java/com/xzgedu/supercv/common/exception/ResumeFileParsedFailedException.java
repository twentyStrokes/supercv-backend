package com.xzgedu.supercv.common.exception;

/**
 * 简历解析失败异常
 * @author wangzheng
 */
public class ResumeFileParsedFailedException  extends BizException {

    public ResumeFileParsedFailedException() {
        super(ErrorCode.RESUME_FILE_PARSED_FAILED);
    }

    public ResumeFileParsedFailedException(Exception e) {
        super(e, ErrorCode.RESUME_FILE_PARSED_FAILED);
    }

    public ResumeFileParsedFailedException(String msg) {
        super(msg, ErrorCode.RESUME_FILE_PARSED_FAILED);
    }

    public ResumeFileParsedFailedException(String msg, Exception e) {
        super(msg, e, ErrorCode.RESUME_FILE_PARSED_FAILED);
    }
}
