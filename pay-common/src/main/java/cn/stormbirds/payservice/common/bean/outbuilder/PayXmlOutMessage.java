package cn.stormbirds.payservice.common.bean.outbuilder;

import cn.stormbirds.payservice.common.bean.MsgType;
import cn.stormbirds.payservice.common.bean.PayOutMessage;

/**
 *
 * @description PayXmlOutMessage.java
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/15 16:57
 *
 */
public class PayXmlOutMessage extends PayOutMessage{

    private String code;

    public PayXmlOutMessage() {
        this.msgType = MsgType.xml.name();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toMessage() {
       return "<xml><return_code><![CDATA[" + code + "]]></return_code><return_msg><![CDATA[" + content
                + "]]></return_msg></xml>";
    }
}
