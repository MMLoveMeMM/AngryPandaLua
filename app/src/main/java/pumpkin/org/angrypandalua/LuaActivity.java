package pumpkin.org.angrypandalua;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaManager;
import org.keplerproject.luajava.LuaState;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LuaActivity extends AppCompatActivity {

    private static final String TAG = LuaActivity.class.getName();

    private LuaState L;
    private LuaManager luaManager;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lua);

        if (!isCheckPermission()) {
            requestPermission();
        }

        mBtn=(Button)findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (L == null) {
                        initLuaEnv();
                    }
                    luaManager.doFile(L, getSDPath()+"/test.lua");
                    luaManager.runFunc(L, "extreme",10.2,20.0,250);
                } catch (LuaException e) {
                    e.printStackTrace();
                }
            }
        });

        initLuaEnv();

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
            for (String file : files) {
                copyFile(getAssets().open("script/" + file), outputDir + "/" + file);
            }
            luaManager.appendLuaDir(L, outputDir);
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
