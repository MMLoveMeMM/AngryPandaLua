package pumpkin.org.angrypandalua;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaManager;
import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import common.LuaPrint;
import common.LuaUtil;
import dalvik.system.DexClassLoader;
import pumpkin.org.angrypandalua.utils.Logic;
import pumpkin.org.angrypandalua.utils.LogicDatas;

/**
 * @ProjectName:
 * @ClassName: LuaApplication
 * @Author: 刘志保
 * @CreateDate: 2019/7/31 14:55
 * @Description: Lua 统一对外管理类
 */
public class LuaManagerEx {

    private static final String TAG = LuaManagerEx.class.getName();
    private static LuaManagerEx instance;
    private Context context;
    private String odexDir;
    // 外部 so 文件路径
    private String libDir;
    // 内部 lua 文件路径
    private String luaDir;
    // 外部 lua 文件路径
    private String luaExtDir;
    // 相当于 LUA_CPATH
    private String luaCpath;
    // 相当于 LUA_PATH
    private String luaLpath;
    private String defJsonPath;
    private boolean debugable = true;

    private LuaState L;

    private LuaManagerEx() {

    }

    public static LuaManagerEx getInstance() {
        if (instance == null) {
            synchronized (LuaManagerEx.class) {
                if (instance == null) {
                    instance = new LuaManagerEx();
                }
            }
        }
        return instance;
    }

    /**
     * 获取def配置文件的路径
     * @return
     */
    public String getDefConfigDirection(){
        if (!TextUtils.isEmpty(defJsonPath)){
            return defJsonPath;
        }
        /**
         * 否则从assert去取配置文件
         */
        return null;
    }

    /**
     * 从工程assert文件路径下导入所有的lua脚本
     *
     * @param context
     */
    public void init(Context context) {

        LuaManager.getInstance();
        this.context = context;
        //初始化AndroLua工作目录
        luaExtDir = LuaUtil.getAndroLuaDir();
        /* 如果是放在assert里面,就使用下面的路径 */
        /*
        odexDir = context.getDir("odex", Context.MODE_PRIVATE).getAbsolutePath();
        libDir = context.getDir("lib", Context.MODE_PRIVATE).getAbsolutePath();
        luaDir = context.getDir("lua", Context.MODE_PRIVATE).getAbsolutePath();
        */
        /* 默认都放到/sdcard目录下 */
        odexDir = getSDPath();
        libDir = getSDPath();
        luaDir = getSDPath();
        defJsonPath = getSDPath();
        /* Lua 调用c库路径 */
        luaCpath = context.getApplicationInfo().nativeLibraryDir + "/lib?.so" + ";" + libDir + "/lib?.so";
        /* Lua 调用其他Lua文件路径 */
        luaLpath = luaDir + "/?.lua;" + luaDir + "/lua/?.lua;" + luaDir + "/?/initEnv.lua;" + luaExtDir + "/?.lua;";
        Log.d(TAG, "luaLpath : " + luaLpath);

        initLuaEnv(context);
    }

    /**
     * 初始化LuaManager管理器
     */
    private void initLuaEnv(Context context) {

        if (L == null) {
            L = initLua(context);
        }

        try {
            String outputDir = getLuaDir();
            if (BuildConfig.DEBUG) {
                /**
                 * 如果是debug版本
                 * 脚本直接从assets路径下加载,防止每次修改脚本都需要push到指定的目录下
                 * 先默认拷贝到sdcard根目录下
                 */
                String[] files = context.getAssets().list("lua");
                copyLuaFiles("lua",files);
            } else {
                /**
                 * release 版本脚本位置
                 */
            }
            appendLuaDir(L, outputDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拷贝root下面所有的文件到指定位置
     * @param root 被拷贝文件的路径
     * @param files 当前目录文件名
     */
    private void copyLuaFiles(String root,String[] files) {
        if(files == null){
            return;
        }
        String outputDir = getLuaDir();
        try {
            for (String file : files) {
                if (file.contains(".lua")) {
                    copyFile(context.getAssets().open(root+"/" + file), outputDir + "/" + file);
                } else if (file.contains(".json")){
                    /**
                     * 拷贝配置文件路径:/sdcard/def
                     */
                    defJsonPath = outputDir + "/def";
                    copyFile(context.getAssets().open(root+"/" + file), outputDir + "/def" + file);
                }else {
                    String[] subfiles = context.getAssets().list(root+"/" + file);
                    copyLuaFiles(root+"/" + file,subfiles);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyFile(InputStream inStream, String newPath) throws IOException {
        /**
         * 如果目录不存在,则新建目录
         */
        File file = new File(newPath);
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

    private String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }else{
            return null;
        }

        File file=new File(sdDir.toString()/*+"/speech"*/);
        if(!file.exists()){
            file.mkdir();
        }
        return sdDir.toString()/*+"/speech"*/;
    }

    public boolean isDebugable() {
        return debugable;
    }

    public LuaManagerEx setDebugable(boolean debugable) {
        this.debugable = debugable;
        return this;
    }

    /**
     * 加载Lua运行脚本
     *
     * @param L
     * @param filePath
     * @return
     * @throws LuaException
     */
    public Object doFile(LuaState L, String filePath) throws LuaException {
        return doFile(L, filePath, new Object[0]);
    }

    public Object doFile(LuaState L, String filePath, Object[] args) throws LuaException {
        appendLuaDir(L, filePath);
        int ok = 0;
        L.setTop(0);
        ok = L.LloadFile(filePath);
        if (ok == 0) {
            L.getGlobal("debug");
            L.getField(-1, "traceback");
            L.remove(-2);
            L.insert(-2);
            int l = args.length;
            for (Object arg : args) {
                L.pushObjectValue(arg);
            }
            ok = L.pcall(l, 1, -2 - l);
            if (ok == 0) {
                return L.toJavaObject(-1);
            }
        }
        throw new LuaException(errorReason(ok) + ": " + L.toString(-1));
    }


    /**
     * 提供给Java调用Lua脚本方法的统一调用接口
     *
     * @param L
     * @param funcName lua中的方法名
     * @param args     lua方法传递的参数
     * @return 如果有返回值, 则获取返回值
     */
    public Object runFunc(LuaState L, String funcName, Object... args) {
        try {
            L.setTop(0);
            L.getGlobal(funcName);
            if (L.isFunction(-1)) {
                L.getGlobal("debug");
                L.getField(-1, "traceback");
                L.remove(-2);
                L.insert(-2);
                int argsLength = args.length;
                for (Object arg : args) {
                    L.pushObjectValue(arg);
                }
                int ok = L.pcall(argsLength, 1, -2 - argsLength);
                if (ok == 0) {
                    return L.toJavaObject(-1);
                }
                throw new LuaException(errorReason(ok) + ": " + L.toString(-1));
            }
        } catch (LuaException e) {
            // LuaLog.e(e);
        }
        return null;
    }

    public Object runFunc(String funcName, Object... args) {
        try {
            L.setTop(0);
            L.getGlobal(funcName);
            if (L.isFunction(-1)) {
                L.getGlobal("debug");
                L.getField(-1, "traceback");
                L.remove(-2);
                L.insert(-2);
                int argsLength = args.length;
                for (Object arg : args) {
                    L.pushObjectValue(arg);
                }
                int ok = L.pcall(argsLength, 1, -2 - argsLength);
                if (ok == 0) {
                    return L.toJavaObject(-1);
                }
                throw new LuaException(errorReason(ok) + ": " + L.toString(-1));
            }
        } catch (LuaException e) {
            // LuaLog.e(e);
        }
        return null;
    }

    /**
     * 直接运行lua脚本的
     *
     * @param L
     * @param funcSrc
     * @param args
     * @return
     * @throws LuaException
     */
    public Object doString(LuaState L, String funcSrc, Object... args) throws LuaException {
        L.setTop(0);
        int ok = L.LloadString(funcSrc);
        if (ok == 0) {
            L.getGlobal("debug");
            L.getField(-1, "traceback");
            L.remove(-2);
            L.insert(-2);
            int l = args.length;
            for (Object arg : args) {
                L.pushObjectValue(arg);
            }
            ok = L.pcall(l, 1, -2 - l);
            if (ok == 0) {
                return L.toJavaObject(-1);
            }
        }
        throw new LuaException(errorReason(ok) + ": " + L.toString(-1));
    }


    public DexClassLoader loadDex(ClassLoader parent, String path) throws LuaException {
        if (path.charAt(0) != '/') {
            path = getLuaDir() + "/" + path;
        }
        if (!new File(path).exists()) {
            if (new File(path + ".dex").exists()) {
                path += ".dex";
            } else if (new File(path + ".jar").exists()) {
                path += ".jar";
            } else {
                throw new LuaException(path + " not found");
            }
        }
        return new DexClassLoader(path, odexDir, getContext().getApplicationInfo().nativeLibraryDir, parent);
    }

    public Object loadLib(LuaState L, String soPath) throws LuaException {
        if (!soPath.startsWith("/")) {
            soPath = libDir + "/" + soPath;
        }
        File soFile = new File(soPath);
        if (!soFile.exists()) {
            throw new LuaException("can not find lib " + soFile.getAbsolutePath());
        }
        if (!libDir.equals(soFile.getParent())) {
            LuaUtil.copyFile(soFile.getAbsolutePath(), libDir + "/lib" + soFile.getName() + ".so");
        }
        LuaObject require = L.getLuaObject("require");
        return require.call(new String[]{soFile.getName()});
    }

    //生成错误信息
    private String errorReason(int error) {
        switch (error) {
            case 6:
                return "error error";
            case 5:
                return "GC error";
            case 4:
                return "Out of memory";
            case 3:
                return "Syntax error";
            case 2:
                return "Runtime error";
            case 1:
                return "Yield error";
        }
        return "Unknown error " + error;
    }

    /**
     * 增加so脚本路径
     *
     * @param dir
     */
    public void appendSoDir(String dir) {
        if (!dir.startsWith("/")) {
            dir = getLibDir() + "/" + dir;
        }
        if (dir.endsWith(".so")) {
            dir = dir.substring(0, dir.lastIndexOf('/'));
        }
        String newPath = String.format(";%s/?.so", dir);
        if (luaCpath.contains(newPath)) {
            return;
        }
        luaCpath += newPath;
    }

    /**
     * 增加Lua脚本文件路径
     *
     * @param L
     * @param dir
     */
    public void appendLuaDir(LuaState L, String dir) {
        if (!dir.startsWith("/")) {
            dir = getLuaExtDir() + "/" + dir;
        }
        if (dir.endsWith(".lua")) {
            dir = dir.substring(0, dir.lastIndexOf('/'));
        }
        String newPath = String.format(";%s/?.lua", dir);
        if (luaLpath.contains(newPath)) {
            return;
        }
        luaLpath += newPath;
        initLuaPath(L);
    }

    public Context getContext() {
        return context;
    }

    public String getOdexDir() {
        return odexDir;
    }

    public String getLibDir() {
        return libDir;
    }

    public String getLuaDir() {
        return luaDir;
    }

    public String getLuaExtDir() {
        return luaExtDir;
    }

    public String getLuaCpath() {
        return luaCpath;
    }

    public String getLuaLpath() {
        return luaLpath;
    }


    public LuaState initLua() {
        return initLua(null);
    }

    /**
     * 初始化环境变量
     * 初始化Lua全局变量
     *
     * @param context
     * @return
     */
    public LuaState initLua(Context context) {
        try {
            L = LuaStateFactory.newLuaState();
            L.openLibs();
            if (context != null) {
                // push 一个 context
                L.pushJavaObject(getContext());
                // pop 并赋值给 activity
                L.setGlobal("activity");

                // 把全局变量 activity 的值 push 进栈
                L.getGlobal("activity");
                // pop 并赋值给 this
                L.setGlobal("this");

                L.pushJavaObject(this);
                L.getGlobal("luajava");

                L.pushString(getLuaExtDir());
                L.setField(-2, "luaextdir");

                L.pushString(getLuaDir());
                L.setField(-2, "luadir");


                L.pushString(getLuaLpath());
                L.setField(-2, "luapath");

                // 彈出一个元素
                L.pop(1);

                /**
                 * 注册全局打印日志函数
                 * 调用: log("hello world")
                 */
                JavaFunction print = new LuaPrint(L);
                print.register("log");
            }

            initLuaPath(L);
            L.pop(1);
            return L;
        } catch (LuaException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 这个是设置环境变量
     * 不需要在lua使用其他lua脚本时去指定lua所在的环境变量
     *
     * @param L
     */
    private void initLuaPath(LuaState L) {
        L.getGlobal("package");
        L.pushString(getLuaLpath());
        L.setField(-2, "path");
        L.pushString(getLuaCpath());
        L.setField(-2, "cpath");
    }

    public void runFucc(String funcName, Object... args){
        try {
            if (L == null) {
                initLuaEnv(context);
            }
            doFile(L, getSDPath()+"/test.lua");
            runFunc(L, "extreme",10.2,20.0,250);
        } catch (LuaException e) {
            e.printStackTrace();
        }
    }
}



