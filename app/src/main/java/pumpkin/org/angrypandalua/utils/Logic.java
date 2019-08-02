package pumpkin.org.angrypandalua.utils;

import android.util.Log;

/**
 * @ProjectName: AngryPandaLua
 * @ClassName: Logic
 * @Author: 刘志保
 * @CreateDate: 2019/8/2 10:24
 * @Description: java类作用描述
 */
public class Logic extends BaseLogic {

    public String params = "this world is very good !";

    public Logic() {
        mControl = new Control();
    }

    @Override
    public void helloBase() {
        super.helloBase();
        Log.d("BaseLogic", "this is sub method .");
    }

    public void showArgs(String[]... args) {

        if (args != null && args.length > 0) {
            Log.d("Logic", "args[0] : " + args[0]);
        }

    }

    public void hello() {
        Log.d("Logic", "this is logic class");
    }

    public LogicDatas getLogicDatas() {
        LogicDatas datas = new LogicDatas();
        datas.setName("qiheyi $$$$$$");
        return datas;
    }

    public void addListener(ILogicListener listener) {
        listener.onLogicListener();
    }
}
