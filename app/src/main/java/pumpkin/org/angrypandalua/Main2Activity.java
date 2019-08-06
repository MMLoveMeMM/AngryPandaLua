package pumpkin.org.angrypandalua;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaManager;
import org.keplerproject.luajava.LuaState;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import pumpkin.org.angrypandalua.utils.Logic;
import pumpkin.org.angrypandalua.utils.LogicDatas;

public class Main2Activity extends AppCompatActivity {
    private LuaState L;
    private LuaManager luaManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

       LuaManagerEx.getInstance().init(this);
       LuaManagerEx.getInstance().runFucc("extreme",1.0,12.3,354.0);
        // initLuaEnv();

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

}
