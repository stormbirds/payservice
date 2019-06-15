package cn.stormbirds.payservice.common.bean.outbuilder;

import cn.stormbirds.payservice.common.bean.PayOutMessage;

/**
 *
 * @description TextBuilder.java
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/15 16:57
 *
 */
public class TextBuilder extends BaseBuilder<TextBuilder, PayOutMessage> {
    private String content;

    public TextBuilder content(String content) {
        this.content = content;
        return this;
    }

    @Override
    public PayOutMessage build() {
        PayTextOutMessage message = new PayTextOutMessage();
        setCommon(message);
        message.setContent(content);
        return message;
    }
}
