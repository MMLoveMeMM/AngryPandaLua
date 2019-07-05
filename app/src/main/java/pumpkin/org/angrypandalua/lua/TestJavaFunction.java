package pumpkin.org.angrypandalua.lua;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;

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
        return 0;
    }
}
