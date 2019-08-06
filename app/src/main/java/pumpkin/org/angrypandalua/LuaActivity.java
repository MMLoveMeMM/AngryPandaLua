package pumpkin.org.angrypandalua;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaManager;
import org.keplerproject.luajava.LuaState;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import pumpkin.org.angrypandalua.utils.ILogicListener;
import pumpkin.org.angrypandalua.utils.Logic;
import pumpkin.org.angrypandalua.utils.LogicDatas;

public class LuaActivity extends AppCompatActivity implements View.OnClickListener,ILogicListener {

    private static final String TAG = LuaActivity.class.getName();

    private LuaState L;
    private LuaManager luaManager;
    private Button mBtn;
    private JSONObject mJson;
    private Map<String,String> maps;
    private ArrayList<String> lists;
    private Pair<String,String> pairs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lua);

        if (!isCheckPermission()) {
            requestPermission();
        }

        pairs=new Pair<>("name","liuzhibao");

        mJson = new JSONObject();

        String hello = "125.25.26";
        hello.split(".");

        Integer in;

        mBtn=(Button)findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
        /*mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    mJson.put("name","liuzhibao");
                    JSONObject mSubJson = new JSONObject();
                    mSubJson.put("year","100");
                    mJson.put("old",mSubJson);
                    Log.d(TAG,"json : "+mJson.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if (L == null) {
                        initLuaEnv();
                    }
                    luaManager.doFile(L, getSDPath()+"/test.lua");
                    luaManager.runFunc(L, "extreme",10.2,20.0,250,new Logic());
                } catch (LuaException e) {
                    e.printStackTrace();
                }
            }
        });*/

        initLuaEnv();
        click();
    }

    private String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();
        }
        return sdDir.toString();
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

    private void initLuaEnv() {
        if (luaManager == null) {
            luaManager = LuaManager.getInstance();
            luaManager.init(this);
        }

        if (L == null) {
            L = luaManager.initLua(this);
        }


        /*if (luaDexLoader == null) {
            luaDexLoader = new LuaDexLoader();
            try {
                luaDexLoader.loadLibs();
            } catch (LuaException e) {
                e.printStackTrace();
            }
        }*/

        // copy assets files to sdcard
        /**
         * 暂时不考虑使用assets目录下lua文件
         */
        try {
            String outputDir = luaManager.getLuaDir();
            String[] files = getAssets().list("script");
            if (files == null) {
                return;
            }
            /*for (String file : files) {
                if(file.contains(".lua")) {
                    copyFile(getAssets().open("script/" + file), outputDir + "/" + file);
                }
            }
            luaManager.appendLuaDir(L, outputDir);

            files = getAssets().list("script/base");
            if (files == null) {
                return;
            }
            for (String file : files) {
                if(file.contains(".lua")) {
                    copyFile(getAssets().open("script/base/" + file), outputDir + "/" + file);
                }
            }*/
            /*for (String file : files) {
                if(file.contains(".lua")) {
                    copyFile(getAssets().open("script/" + file), outputDir + "/" + file);
                }else{
                    String[] subfiles = getAssets().list("script/"+file);
                    if (subfiles == null) {
                        return;
                    }
                    for(String subf:subfiles) {
                        if (subf.contains(".lua")) {
                            copyFile(getAssets().open("script/"+file+"/" + subf), outputDir + "/" + subf);
                        }
                    }
                }
            }*/
            copyLuaFiles("script",files);
            luaManager.appendLuaDir(L, outputDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyLuaFiles(String root,String[] files) {
        if(files == null){
            return;
        }
        String outputDir = luaManager.getLuaDir();
        try {
            for (String file : files) {
                if (file.contains(".lua")) {
                    copyFile(getAssets().open(root+"/" + file), outputDir + "/" + file);
                } else {
                    String[] subfiles = getAssets().list(root+"/" + file);
                    copyLuaFiles(root+"/" + file,subfiles);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(InputStream inStream, String newPath) throws IOException {
        int len;
        FileOutputStream fs = new FileOutputStream(newPath);
        byte[] buffer = new byte[4096];
        while ((len = inStream.read(buffer)) != -1) {
            fs.write(buffer, 0, len);
        }
        fs.flush();
        fs.close();
        inStream.close();
    }

    @Override
    public void onClick(View v) {
        try {
            mJson.put("name","liuzhibao");
            JSONObject mSubJson = new JSONObject();
            mSubJson.put("year","100");
            mJson.put("old",mSubJson);
            Log.d(TAG,"json : "+mJson.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (L == null) {
                initLuaEnv();
            }
            luaManager.doFile(L, getSDPath()+"/test.lua");
            LogicDatas datas=new LogicDatas();
            datas.setName("this name is show in logic class");
            datas.setCity("shenzhen");
            luaManager.runFunc(L, "extreme",10.2,20.0,250,new Logic(),this,datas);
        } catch (LuaException e) {
            e.printStackTrace();
        }
    }

    public void click(){
        try {
            mJson.put("name","liuzhibao");
            JSONObject mSubJson = new JSONObject();
            mSubJson.put("year","100");
            mJson.put("old",mSubJson);
            Log.d(TAG,"json : "+mJson.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (L == null) {
                initLuaEnv();
            }
            luaManager.doFile(L, getSDPath()+"/test.lua");
            LogicDatas datas=new LogicDatas();
            datas.setName("this name is show in logic class");
            datas.setCity("shenzhen");
            luaManager.runFunc(L, "extreme",10.2,20.0,250,new Logic(),this,datas);
        } catch (LuaException e) {
            e.printStackTrace();
        }
    }

    public void showLuaActivity(){
        Log.d(TAG,"this is show LuaActivity !");
    }

    @Override
    public void onLogicListener(LogicDatas datas) {
        Log.d(TAG,"this is show onLogicListener !");
    }

    public void addActionListener(ILogicListener listener){
        LogicDatas datas=new LogicDatas();
        datas.setName("Lua is good script!");
        listener.onLogicListener(datas);
    }
}
