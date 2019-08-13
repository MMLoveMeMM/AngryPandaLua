--
-- Created by IntelliJ IDEA.
-- User: 020200184
-- Date: 2019/8/1
-- Time: 11:11
-- To change this template use File | Settings | File Templates.
--
bindClass = luajava.bindClass
newClass = luajava.new
newInstance = luajava.newInstance
createProxy = luajava.createProxy

String = bindClass("java.lang.String")
android_R = bindClass("android.R")

gLogic=nil

javaUtils = bindClass("pumpkin.org.angrypandalua.utils.Utils")