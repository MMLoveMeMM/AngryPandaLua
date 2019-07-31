package pumpkin.org.angrypandalua;

import android.app.Application;

import org.keplerproject.luajava.LuaManager;

/**
 * @ProjectName: AngryPandaLua
 * @ClassName: LuaApplication
 * @Author: 刘志保
 * @CreateDate: 2019/7/31 20:02
 * @Description: java类作用描述
 */
public class LuaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LuaManager.getInstance().init(this.getApplicationContext());

    }
}
