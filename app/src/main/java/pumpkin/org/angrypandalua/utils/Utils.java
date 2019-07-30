package pumpkin.org.angrypandalua.utils;

import android.util.Log;

/**
 * @ProjectName: AngryPandaLua
 * @ClassName: Utils
 * @Author: 刘志保
 * @CreateDate: 2019/7/30 15:41
 * @Description: java类作用描述
 */
public class Utils {

    private static final String TAG = Utils.class.getName();

    public Utils(){
        Log.d(TAG,"inital utils !");
    }

    public static void getVersion(){
        Log.d(TAG,"app version : 0.1.0");
    }

    public static void getName(){
        Log.d(TAG,"your name is my name");
        LuaManager.getInstance().getManager();
    }

}
