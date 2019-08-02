--
-- Created by IntelliJ IDEA.
-- User: 020200184
-- Date: 2019/8/1
-- Time: 19:09
-- To change this template use File | Settings | File Templates.
--

require"imports"
luamap = {}

function luamap.createMap()
    print("+++++++++++++++++***++++++++++++++++++++++")
    local Map = bindClass("java.util.HashMap")
    local map = newClass(Map)
    map:put("1","liuzhibao")
    map:put("2","lilei")
    print(string.format("map : %s",map:get("1")))
    luamap.showMap(map)
end

function luamap.showMap(map)
    print("+++++++++++++++++ show map ++++++++++++++++++++++")
    -- print(string.format("map : %s",map:get("2")))
    local Set = bindClass("java.util.HashMap.EntrySet")
    local set = newClass(Set)
    set = map:entrySet()
    --local itera = map:entrySet():iterator()
    local itera = set:iterator()
    while (itera:hasNext()) do
        print("key-value &&&&&&&&")
        local entry = itera:next()
        local key = entry:getKey()
        local value = entry:getValue()
        print("key : "+key+" value : "+value)
    end

end

return luamap