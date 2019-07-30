package pumpkin.org.angrypandalua.utils;

import android.util.Log;

/**
 * @ProjectName: AngryPandaLua
 * @ClassName: Print
 * @Author: 刘志保
 * @CreateDate: 2019/7/30 16:29
 * @Description: java类作用描述
 */
public class Print {

    private static final String TAG = Print.class.getName();

    public static void show(String info){
        Log.d(TAG,""+info);
    }

    public static void debug(){
        Log.d(TAG,"debug .............");
    }
}
