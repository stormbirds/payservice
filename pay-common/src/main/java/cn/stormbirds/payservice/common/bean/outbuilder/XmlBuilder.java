package cn.stormbirds.payservice.common.bean.outbuilder;

import cn.stormbirds.payservice.common.bean.PayOutMessage;

/**
 *
 * @description XmlBuilder.java
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/15 16:55
 *
 */
public class XmlBuilder extends BaseBuilder<XmlBuilder, PayOutMessage> {
    private String content;
    private String code;
    public XmlBuilder content(String content) {
        this.content = content;
        return this;
    }

    public XmlBuilder code(String code) {
        this.code = code;
        return this;
    }


    @Override
    public PayOutMessage build() {
        PayXmlOutMessage message = new PayXmlOutMessage();
        setCommon(message);
        message.setContent(content);
        message.setCode(code);
        return message;
    }
}
