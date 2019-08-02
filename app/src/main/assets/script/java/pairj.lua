--
-- Created by IntelliJ IDEA.
-- User: 020200184
-- Date: 2019/8/2
-- Time: 8:57
-- To change this template use File | Settings | File Templates.
--

require"imports"
luapair = {}
-- createProxy
LIUZHIBAO = "this is global veriable"
function luapair.createPair()
    print("+++++++++++++++++ pair ++++++++++++++++++++++")
    -- local Pair = bindClass("android.util.Pair")
    -- local pair = newClass(Pair,"name","liuzhibao's pair")
    local pair = newInstance("android.util.Pair","name","liuzhibao's pair")
    print(pair.first)
    print(pair.second)
end

return luapair