
package cn.stormbirds.payservice.common.api;

import java.util.Map;


/**
 *
 * @description 回调，同时类型转换
 * @author StormBirds
 * @email xbaojun@gmail.com
 * @date 2019/6/15 16:44
 *
 */
public interface Callback<T> {
     /**
      * 执行者
      * @param map 需要转化的map
      * @return 处理过后的类型对象
      */
     T perform(Map<String, Object> map);

}
