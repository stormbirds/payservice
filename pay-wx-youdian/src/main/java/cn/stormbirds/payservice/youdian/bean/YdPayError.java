package cn.stormbirds.payservice.youdian.bean;

import cn.stormbirds.payservice.common.bean.result.PayError;

/**
 *
 * @ Description YdPayError.java
 * @ Author StormBirds
 * @ Email xbaojun@gmail.com
 * @ Date 2019/6/17 20:37
 *
 */
public class YdPayError implements PayError {

    private int errorcode;
    private String msg;
    private String content;

    @Override
    public String getErrorCode() {
        return errorcode + "";
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }

    public YdPayError(int errorcode, String msg) {
        this.errorcode = errorcode;
        this.msg = msg;
    }

    public YdPayError(int errorcode, String msg, String content) {
        this.errorcode = errorcode;
        this.msg = msg;
        this.content = content;
    }

    @Override
    public String getString() {
        return "支付错误: errcode=" + errorcode + ", msg=" + msg + (null == content ? "" : "\n content:" + content);
    }
}
