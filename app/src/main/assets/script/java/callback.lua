--
-- Created by IntelliJ IDEA.
-- User: 020200184
-- Date: 2019/8/2
-- Time: 16:32
-- To change this template use File | Settings | File Templates.
--

require"imports"
luacallback = {}

logic_cb = { onLogicListener =
function (ev)
    print("+++++++ lua callback onLogicListener ! +++++++")
    print(ev:getName())
end
}

function luacallback.createCallBack(activity)

    local logic_jproxy = createProxy("pumpkin.org.angrypandalua.utils.ILogicListener" ,logic_cb)
    activity:addActionListener(logic_jproxy)

end

return luacallback


