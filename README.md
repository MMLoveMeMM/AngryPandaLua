这个工程主要介绍Android中java和lua基本的交互操作:
<1> : java和lua变量参数互用;
<2> : java和lua对象的互用;
<3> : java和lua方法函数之间的互调;
<4> : 多个lua文件加载,以及过个依赖lua相互调用.

注意:
多个lua文件的时候,在这个demo中,将assets中lua目录下lua文件拷贝到设备的sdcard目录下即可使用.

多lua文件或者so加载和lua脚本程序加载:
<1> : 多文件或者so加载是通过LdoFile()方法,并且还需要设置环境变量:
private void loadLua(){
    // 直接加载lua源码,这种方式适合加载少量的或者启动代码
    // lua.LdoString(readAssetsTxt(this, "test.lua"));
    // 这种可以批量加载代码
    int ret = lua.LdoFile("/sdcard/libmodule.lua");
    Log.d("LdoFile","ret : "+ret);
    lua.LdoFile("/sdcard/test.lua");
    initLuaPath(lua);
}
// 下面还要设置加载的环境变量
private void initLuaPath(LuaState L) {
    L.getGlobal("package");
    L.pushString("/sdcard/libmodule.lua;");
    L.setField(-2, "path");
    /*L.pushString(getLuaCpath());
    L.setField(-2, "cpath");*/
}

完整的加载多脚本方式可以参考luaDevAndroid工程.

<2> : 仅仅是加载lua脚本程序,则使用LdoString,不过在生产环境下,仅仅加载脚本的方式应该非常少,使用
也非常简单.


整个lua使用的超级重点对象:
LuaState

工程有一个严重的bug修复:
参考:https://github.com/jasonsantos/luajava/issues/10
jni库中luajava.c中:
int javaNew( lua_State * L )函数需要修改,否则luajava.new无法使用:
修改的程序段如下:
if ( /*clazz*/luajava_api_class == NULL || method == NULL )
   {
      lua_pushstring( L , "Invalid method org.keplerproject.luajava.LuaJavaAPI.javaNew." );
      lua_error( L );
   }

   ret = ( *javaEnv )->CallStaticIntMethod( javaEnv , luajava_api_class/*clazz*/ , method , (jint)stateIndex , classInstance );

将其中的class -> luajava_api_class