package pumpkin.org.angrypandalua.lua;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;

/**
 * @ProjectName: AngryPandaLua
 * @ClassName: JavaObjectFunction
 * @Author: 刘志保
 * @CreateDate: 2019/7/31 9:29
 * @Description: java类作用描述
 */
public class JavaObjectFunction  extends JavaFunction {

    public JavaObjectFunction(LuaState L) {
        super(L);
    }

    @Override
    public int execute() throws LuaException {
        return 0;
    }

    @Override
    public void register(String name) throws LuaException {
        super.register(name);
    }
}
