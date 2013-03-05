package haier.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-10-8
 * Time: ÏÂÎç12:43
 * To change this template use File | Settings | File Templates.
 */
public class CurcdeMap {
    public static Map setCurcdeMap() {
        Map curMap = new HashMap();
//        if (yyyymm.equals("201108")) {
            curMap.put("EUR","9.2122");
            curMap.put("JPY","0.083296");
            curMap.put("GBP","10.40545");
            curMap.put("HKD","0.8192");
            curMap.put("USD","6.3867");
//        }
//        if (yyyymm.equals("201109")) {
//            curMap.put("EUR","8.6328");
//            curMap.put("JPY","8.2978");
//            curMap.put("GBP","9.927");
//            curMap.put("HKD","0.8154");
//            curMap.put("USD","6.3549");
//        }
        return curMap;
    }
}
