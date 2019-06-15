package cn.stormbirds.payservice.common.bean.outbuilder;

import cn.stormbirds.payservice.common.bean.PayOutMessage;


/**
 *
 * @description BaseBuilder.java
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/15 16:53
 *
 */
public abstract class BaseBuilder<BuilderType, ValueType> {


    public abstract ValueType build();

    public void setCommon(PayOutMessage m) {

    }

}