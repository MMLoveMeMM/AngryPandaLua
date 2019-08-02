--
-- Created by IntelliJ IDEA.
-- User: 020200184
-- Date: 2019/8/1
-- Time: 19:36
-- To change this template use File | Settings | File Templates.
--

require"imports"
lualist = {}

function lualist.createList()
    print("+++++++++++++++++ *** ++++++++++++++++++++++")
    local List = bindClass("java.util.ArrayList")
    local list = newClass(List)
    list:add("liuzhibao")
    list:add("lilei")
    print(string.format("list : %s",list:get(0)))
    lualist.createList(list)
end

function lualist.createList(list)
    -- 遍历整个List
    local it = list:iterator();
    while(it:hasNext()) do
        print(it:next())
    end
end

return lualist

