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

    protected String params = "this world is very good !";

    private LogicDatas mLogicDatas;

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

    public void setParams(String params) {
        this.params = params;
    }

    public String getParams(){
        return params;
    }

    public void hello() {
        Log.d("Logic", "this is logic class");
    }

    public LogicDatas getLogicDatas() {
        LogicDatas datas = new LogicDatas();
        datas.setName("qiheyi $$$$$$");
        return datas;
    }

    public void setLogicDatas(LogicDatas logicDatas) {
        this.mLogicDatas = logicDatas;
        Log.d("Logic","name : "+logicDatas.getName());
    }

    public void addListener(ILogicListener listener) {
        LogicDatas datas=new LogicDatas();
        datas.setName("Lua is good LuaActivity !");
        listener.onLogicListener(datas);
    }
}
