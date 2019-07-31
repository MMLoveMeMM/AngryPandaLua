local bindClass = luajava.bindClass
local newClass = luajava.new
local newInstance = luajava.newInstance
v1 = "this is value from lua"

table = {}
table["name"] = "Lily"
table["age"] = 18
table["sex"] = "female"

function extreme(a, b, c)

    -- local String = bindClass("java.lang.String")

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