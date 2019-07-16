package pumpkin.org.angrypandalua.lua;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ProjectName: AngryPandaLua
 * @ClassName: TestJavaFunction
 * @Author: 刘志保
 * @CreateDate: 2019/7/5 20:23
 * @Description: java类作用描述
 */
public class TestJavaFunction extends JavaFunction {
    /**
     * Constructor that receives a LuaState.
     *
     * @param L LuaState object associated with this JavaFunction object
     */
    public TestJavaFunction(LuaState L) {
        super(L);
    }

    @Override
    public int execute() throws LuaException {
        // 获取Lua传入的参数，注意第一个参数固定为上下文环境。
        // Getting the parameters passed in by Lua
        // Notice that the first argument is lua context.
        String str = L.toString(2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        L.pushString(simpleDateFormat.format(date) + str);
        return 1; // 返回值的个数
    }

    public void register() {
        try {
            // 注册为 Lua 全局函数
            // Register as a Lua global function
            // 在lua中可以调用getTime方法,但是这个方式是java语言实现的
            register("getTime");
        } catch (LuaException e) {
            e.printStackTrace();
        }
    }

}
