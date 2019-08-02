--
-- Created by IntelliJ IDEA.
-- User: 020200184
-- Date: 2019/8/1
-- Time: 15:57
-- To change this template use File | Settings | File Templates.
--
require"imports"
luajson = {}

function luajson.createJson()

    gLogic.mControl:send()
    print("++++++++++++++++++++++++++++++++++++++++++++++")
    local JSONObject = bindClass("org.json.JSONObject")
    local jSONObject = newClass(JSONObject)
    local jSubSONObject = newClass(JSONObject)
    jSONObject:put("name","liuzhibao")
    jSubSONObject:put("name","liuzhibao")
    jSONObject:put("old",jSubSONObject);
    print(jSONObject:toString())
    return jSONObject:toString()
end

return luajson


