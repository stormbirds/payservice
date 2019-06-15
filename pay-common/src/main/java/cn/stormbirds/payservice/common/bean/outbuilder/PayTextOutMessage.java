package cn.stormbirds.payservice.common.bean.outbuilder;

import cn.stormbirds.payservice.common.bean.MsgType;
import cn.stormbirds.payservice.common.bean.PayOutMessage;

/**
 *
 * @description PayTextOutMessage.java
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/15 16:56
 *
 */
public class PayTextOutMessage extends PayOutMessage{

    public PayTextOutMessage() {
        this.msgType = MsgType.text.name();
    }

    @Override
    public String toMessage() {
        return getContent();
    }
}
