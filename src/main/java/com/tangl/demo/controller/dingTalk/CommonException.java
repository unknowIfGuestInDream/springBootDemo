package com.tangl.demo.controller.dingTalk;

/**
 * @author: TangLiang
 * @date: 2020/12/12 0:43
 * @since: 1.0
 */
public class CommonException extends RuntimeException {

    private Integer errCode;

    private String errMsg;

    public CommonException(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
