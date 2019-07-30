package pumpkin.org.angrypandalua.utils;

import android.util.Log;

/**
 * @ProjectName: AngryPandaLua
 * @ClassName: LuaManager
 * @Author: 刘志保
 * @CreateDate: 2019/7/30 16:15
 * @Description: java类作用描述
 */
public class LuaManager {

    private static final String TAG = LuaManager.class.getName();

    private static final LuaManager ourInstance = new LuaManager();

    public static LuaManager getInstance() {
        return ourInstance;
    }

    private LuaManager() {
    }

    public void getManager(){
        Log.d(TAG,"getManager information !");
    }
}
