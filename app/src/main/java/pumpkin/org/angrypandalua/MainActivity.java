package pumpkin.org.angrypandalua;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import java.io.IOException;
import java.io.InputStream;

import pumpkin.org.angrypandalua.lua.AsyncJavaFunction;
import pumpkin.org.angrypandalua.lua.TestJavaFunction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LuaState lua = null;
    private TextView mText;

    private Button mBtn;
    private Button mLoadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) findViewById(R.id.text);

        if (!isCheckPermission()) {
            requestPermission();
        }

        /**
         * 初始化lua
         */
        lua = LuaStateFactory.newLuaState();
        if (lua == null) {
            mText.setText("newLuaState false");
            return;
        }
        lua.openLibs();

        // 直接加载lua源码,这种方式适合加载少量的或者启动代码
        // lua.LdoString(readAssetsTxt(this, "test.lua"));

        // 这种可以批量加载代码
        int ret = lua.LdoFile("/sdcard/libmodule.lua");
        Log.d("LdoFile","ret : "+ret);
        lua.LdoFile("/sdcard/test.lua");
        initLuaPath(lua);

        mBtn = (Button) findViewById(R.id.func);
        mBtn.setOnClickListener(this);

        mLoadBtn = (Button)findViewById(R.id.load);
        mLoadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 每次更新sdcard里面的lua文件
                 */
                loadLua();
            }
        });

    }

    private void loadLua(){
        // 直接加载lua源码,这种方式适合加载少量的或者启动代码
        // lua.LdoString(readAssetsTxt(this, "test.lua"));
        // 这种可以批量加载代码
        int ret = lua.LdoFile("/sdcard/libmodule.lua");
        Log.d("LdoFile","ret : "+ret);
        lua.LdoFile("/sdcard/test.lua");
        initLuaPath(lua);
    }

    private void initLuaPath(LuaState L) {
        L.getGlobal("package");
        L.pushString("/sdcard/libmodule.lua;");
        L.setField(-2, "path");
        /*L.pushString(getLuaCpath());
        L.setField(-2, "cpath");*/
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id){
            case R.id.func:{
                callLuaFunction();
                // pushJavaObj2Lua(mText);
                // luaUsingJavaFunction();
                // pushLuaVar("v2");
                // setLuaTable();
                //getLuaTable();
                //luaCallBackFromJava();
            }
            default:
                break;
        }

    }

    /**
     * 从lua中获取变量值
     * @param key : 变量名
     */
    private void getLuaVar(String key){
        lua.getGlobal(key);
        mText.setText(lua.toString(-1));
        lua.pop(1);
    }

    /**
     * 给lua中变量赋值
     * @param key
     */
    private void pushLuaVar(String key){
        lua.pushString("value from java");
        lua.setGlobal(key);
    }

    /**
     * 获取Table值
     */
    private void getLuaTable(){
        StringBuilder s = new StringBuilder();
        lua.getGlobal("table");
        s.delete(0, s.length());
        if (lua.isTable(-1)) {
            lua.pushNil();
            while (lua.next(-2) != 0) {
                s.append(lua.toString(-2)).append(" = ")
                        .append(lua.toString(-1)).append("\n");
                lua.pop(1);
            }
            lua.pop(1);
            mText.setText(s.toString());
        }
    }

    /**
     * 设置Table中的值,注意这个不是追加
     */
    private void setLuaTable(){
        lua.newTable();
        lua.pushString("from");
        lua.pushString("java");
        lua.setTable(-3);
        lua.pushString("value");
        lua.pushString("Hello lua");
        lua.setTable(-3);
        lua.setGlobal("table");
        getLuaTable();
    }

    /**
     * 将java对象传递给lua调用
     * lua调用方式:"obj:obj's method name(parameters)"
     * @param textView
     */
    private void pushJavaObj2Lua(TextView textView){
        lua.pushJavaObject(textView);
        // luaText这个是textView在lua中对应的变量名
        lua.setGlobal("luaText");
        // 这个时候java对象可以在lua中使用了
        lua.pushInteger(Color.GREEN);
        lua.setGlobal("red");
        lua.LdoString("luaText:setTextColor(red)");
    }

    /**
     * lua调用Java方法
     */
    private void luaUsingJavaFunction(){
        /**
         * 首先注册:需要依赖JavaFunction子类进行,这里是TestJavaFunction类
         * 然后使用lua语言调用注册的方法,并且传入一个参数进去(实际上是两个)
         */
        new TestJavaFunction(lua).register();
        lua.LdoString("return getTime(' - passing by lua')");
        mText.setText(lua.toString(-1));
        lua.pop(1);

    }

    /**
     * java调用Lua方法
     */
    private void callLuaFunction(){
        StringBuilder s = new StringBuilder();
        lua.getGlobal("extreme"); // 获取到函数入栈
        lua.pushNumber(15.6); // 依次压入三个参数
        lua.pushNumber(0.8);
        lua.pushNumber(189);
        /**
         * 第一个参数是函数传入的参数个数;
         * 第二个是返回的结果数量
         * 返回状态值
         */
        lua.pcall(3, 2, 0);
        /**
         * 返回值:
         * lua.toString(-1) 其中-1代表其次返回值
         * lua.toString(-2) 其中-2代表最前面的一个返回值
         * 这个顺序标号是逆序的
         */
        s.append("max : " + lua.toString(-2)+"  min : "+lua.toString(-1));
        mText.setText(s);
    }

    private void luaCallBackFromJava(){
        mText.setText("Loading...");
        new AsyncJavaFunction(lua).register();
        lua.getGlobal("luaCallback");
        lua.pushJavaObject(mText);
        lua.pcall(1, 0, 0);
    }

    public static String readAssetsTxt(Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "utf-8");
            // Finally stick the string into the text view.
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "err";
    }

    private boolean isCheckPermission() {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, 0x233);
    }

}
