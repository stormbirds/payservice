package cn.stormbirds.payservice.common.bean;

/**
 *
 * @description 基础货币类型
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/15 16:58
 *
 */
public interface CurType {
    /**
     * 获取货币类型
     * @return 货币类型
     */
    String getType();

    /**
     * 货币名称
     * @return 货币名称
     */
    String getName();

}
