package pumpkin.org.angrypandalua.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ProjectName: AngryPandaLua
 * @ClassName: LuaJavaTestByLonvoy
 * @Author: 刘志保
 * @CreateDate: 2019/7/30 16:20
 * @Description: java类作用描述
 */
public class LuaJavaFuncTest {
    private static final String TAG = LuaJavaFuncTest.class.getName();
    /**
     * 欢迎
     * @return
     */
    public String hello(){
        Log.d(TAG,"LuaJavaFuncTest hello");
        return "Hello Lonvoy!";
    }

    /**
     * 私有方法测试
     * @return
     */
    @SuppressWarnings("unused")
    private String testPrivate(){
        return "this method is private, you can call it!";
    }

    /**
     * 测试 返回String类型
     * @param arg
     * @return
     */
    public String testString(String arg){
        return "your input param is ：".concat(arg);
    }

    /**
     * 测试返回 Map 类型
     * @return
     */
    public Map<String,Object> testMap(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("A", "value type is String");
        map.put("B", 10);
        map.put("C", false);
        map.put("D", 6.9);
        map.put("E", new String [] {"11","22","33"});
        // Set s = map.

        return map;
    }

    /**
     * 测试返回List类型
     * @return
     */
    public List<String> testList(){
        List<String> list = new ArrayList<String>();
        list.add("first");
        list.add("second");
        list.add("third");
        list.add("fourth");
        return list;
    }
}
