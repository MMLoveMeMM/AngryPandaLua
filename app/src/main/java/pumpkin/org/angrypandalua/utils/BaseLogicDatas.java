package pumpkin.org.angrypandalua.utils;

import android.util.Log;

/**
 * @ProjectName: AngryPandaLua
 * @ClassName: BaseLogicDatas
 * @Author: 刘志保
 * @CreateDate: 2019/8/5 16:59
 * @Description: java类作用描述
 */
public class BaseLogicDatas {
    private String city;

    public void showCity(){
        Log.d("BaseLogicDatas","showCity : "+city);
    }

    public String getCity() {
        Log.d("BaseLogicDatas","getCity : "+city);
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
