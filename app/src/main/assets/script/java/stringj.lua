--
-- Created by IntelliJ IDEA.
-- User: 020200184
-- Date: 2019/8/1
-- Time: 17:04
-- To change this template use File | Settings | File Templates.
--

require"imports"
luastring = {}

function luastring.createString()
    print("+++++++++++++++++-------++++++++++++++++++++++")
    ---local String = bindClass("java.lang.String")
    ---local info = newClass(String,"123.251.254.485")

    print(LIUZHIBAO)

    local javaString = bindClass("pumpkin.org.angrypandalua.utils.JavaString")
    local list = javaString:split("123-251-254-485","-")

    --local arr ={}
    --arr = info:split(".");
    --print(string.format("arr : %s",arr[1]))
    --if(info:contains(".")) then
    --    print(tostring("hello this is tostring !"))
    -- end
    luastring.showList(list)
end

function luastring.showList(list)
    -- 遍历整个List
    local it = list:iterator();
    while(it:hasNext()) do
        print(it:next())
    end
end

return luastring