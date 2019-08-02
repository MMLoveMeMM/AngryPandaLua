package pumpkin.org.angrypandalua.utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: AngryPandaLua
 * @ClassName: JavaString
 * @Author: 刘志保
 * @CreateDate: 2019/8/2 14:55
 * @Description: java类作用描述
 */
public class JavaString {

    public static List<String> split(String src, String regex) {
        if (TextUtils.isEmpty(src)) {
            return null;
        }
        List<String> lists = new ArrayList<>();
        String[] strArr = src.split(regex);
        if (strArr != null && strArr.length > 0) {
            for (int i = 0; i < strArr.length; i++) {
                lists.add(strArr[i]);
            }
        }
        return lists;
    }

}
