package cn.stormbirds.payservice.wx.bean;

import cn.stormbirds.payservice.common.bean.result.PayError;

/**
 *
 * @ Description 微信支付异常
 * @ Author StormBirds
 * @ Email xbaojun@gmail.com
 * @ Date 2019/6/17 20:35
 *
 */
public class WxPayError implements PayError {

    private String errorCode;

    private String errorMsg;
    private String content;


    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    public WxPayError(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public WxPayError(String errorCode, String errorMsg, String content) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.content = content;
    }

    @Override
    public String getString() {
            return "支付错误: errcode=" + errorCode + ", errmsg=" + errorMsg + (null == content ? "" : "\n content:" + content);
    }
}
