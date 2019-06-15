package cn.stormbirds.payservice.common.bean.outbuilder;

import cn.stormbirds.payservice.common.bean.MsgType;
import cn.stormbirds.payservice.common.bean.PayOutMessage;

/**
 *
 * @description PayJsonOutMessage.java
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/15 16:56
 *
 */
public class PayJsonOutMessage extends PayOutMessage{

    public PayJsonOutMessage() {
        this.msgType = MsgType.json.name();
    }

    @Override
    public String toMessage() {
        return getContent();
    }


}
