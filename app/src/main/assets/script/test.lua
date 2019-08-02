--local bindClass = luajava.bindClass
--local newClass = luajava.new
--local newInstance = luajava.newInstance
v1 = "this is value from lua"

table = {}
table["name"] = "Lily"
table["age"] = 18
table["sex"] = "female"
require "imports"
require "shape"
require "circle"
require "testbase"
require "constants"
require "json"
require "stringj"
require "mapj"
require "listj"
require "pairj"
require "doublej"

function extreme(a, b, c,logic,activity)

    -- luapair.createPair()

    -- lualist.createList()

    -- luamap.createMap()

    luastring.createString()

    -- local mLogic =  bindClass("pumpkin.org.angrypandalua.utils.Logic")
    -- local hlogic = newClass(mLogic)
    -- local hlogic = newInstance("pumpkin.org.angrypandalua.utils.Logic")
    -- print(hlogic.params)

    luadouble.createDouble()
    print(logic.params)
    logic:helloBase();
    logic.mControl:send();
    logic:hello();

    logic:showArgs("hello1","hello2");

    local datas = logic:getLogicDatas();
    print(datas:getName());
    logic:addListener(activity);

    gLogic = logic

    activity:showLuaActivity();

    local hel="zhibao.liu"
    if("zhibao.liu"==hel) then
        print("string equal !")
    end

    luajson.createJson()
    -- local String = bindClass("java.lang.String")
    local info = newClass(String,"I am string !")
    print(info)
    print(string.format("EVENT_PLAY_TTS : %d",EVENT_PLAY_TTS))
    print("-----------------------------------------------------")
    local print = bindClass("pumpkin.org.angrypandalua.utils.Print")
    print:debug()
    print:show('yes this is OK')
    --local log = print:show
    -- log("hello world !")
    local utils = bindClass("pumpkin.org.angrypandalua.utils.Utils")
    utils:getVersion()
    local utilsobj = newClass(utils)
    utilsobj:getName()

    local util = newInstance("pumpkin.org.angrypandalua.utils.Utils")
    util:getName()
    -- local utilsobj = newClass(utils)
    -- utilsobj:getName()

    local testStatic = bindClass("pumpkin.org.angrypandalua.utils.LuaJavaFuncTest");
    -- new 通过class对象返回对应类的实例
    local testNew = newClass(testStatic);
    print:show(testNew:hello())
    -- print:show(testNew:testPrivate())
    print:show(testNew:testString("hello liuzhibao"))

    -- 获取List所有的参数
    local list = bindClass("java.util.ArrayList");
    local listObj = newClass(list)
    listObj = testNew:testList()
    print:show(listObj:get(0))
    print:show(listObj:get(1))
    --local len = listObj:size()
    --print:show("list len : "+len)

    -- 获取Map所有的参数
    local map = bindClass("java.util.HashMap");
    local mapObj = newClass(map)
    mapObj = testNew:testMap()

    if(mapObj:containsKey("A"))
        then
        print:show(mapObj:get("A"))
    end
    if(mapObj:containsKey("W"))
    then
        print:show(mapObj:get("W"))
    else
        print:show("can not find the value !")
    end

    print:show("++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
    myshape = Shape:new(nil,10)
    myshape:printArea()

    mysquare = Square:new(nil,10)
    mysquare:printArea()

    mycircle = Circle:new(nil,10,12)
    -- mycircle:printArea()
    mycircle:printName()
    -- 无法调用基类的方法,主要原因是子类创建的时候不会构建基类,mycircle:basePrint()

    -- 下面解决调用基类的方法
    a=test.new(1)	-- 输出两行，base_type ctor 和 test ctor 。这个对象被正确的构造了。
    -- a:print_x()	-- 输出 1 ，这个是基类 base_type 中的成员函数。
    usObj(a)
    a:print_show("liuzhibao using base class")
    a:print_show("liuzhibao using base class 1","liuzhibao using base class 2")
    a:hello()

    local max = a
    local min = a
    if(b>max) then
        max = b
    elseif(b<min) then
        min = b
    end
    if(c>max) then
        max = c
    elseif(c<min) then
        min = c
    end

    return max, min
end

function usObj(fuc)
    fuc:print_x()
end

function luaCallback(tv)
    -- http 函数是由 java `AsyncJavaFunction` 类注入的
    -- http function was injected by java `AsyncJavaFunction`
    http(function(result, time)
            tv:setText(string.format("result: %s\ntime: %dms", result, time));
        end
    )
end

function luaCallByObject(datas)
    local print = bindClass("pumpkin.org.angrypandalua.utils.Print")
    print:show('luaCallByObject ... ...')
    print:show(string.format("luaCallByObject Name = %s",datas:getName()))
    print:show('luaCallByObject end ! ')
end

function luaArray(arr)
    local print = bindClass("pumpkin.org.angrypandalua.utils.Print")
    print:show('luaArray ... ...')
    print:show(string.format("luaArray Name = %s",arr[1]))
    print:show('luaArray end ! ')
end