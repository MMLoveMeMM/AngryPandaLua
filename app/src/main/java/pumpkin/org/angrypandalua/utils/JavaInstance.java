package pumpkin.org.angrypandalua.utils;

import android.util.Log;

/**
 * @ProjectName: AngryPandaLua
 * @ClassName: JavaInstance
 * @Author: 刘志保
 * @CreateDate: 2019/8/6 11:27
 * @Description: java类作用描述
 */
public class JavaInstance {
    public static final JavaInstance ourInstance = new JavaInstance();

    public static JavaInstance getInstance() {
        return ourInstance;
    }

    public JavaInstance() {
    }

    public void sayHello(){
        Log.d("JavaInstance","hello this is instance sayHello !");
    }
}
