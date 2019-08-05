--
-- Created by IntelliJ IDEA.
-- User: 020200184
-- Date: 2019/8/2
-- Time: 20:26
-- To change this template use File | Settings | File Templates.
--

require"imports"
luainteger = {}

function luainteger.createInteger()


    local Integer = newInstance("java.lang.Integer")
    local re = Integer:parseInt(15.0/4.9)
    print(string.format("integer result : %d",re));

end

return luainteger