--
-- Created by IntelliJ IDEA.
-- User: 020200184
-- Date: 2019/7/31
-- Time: 19:43
-- To change this template use File | Settings | File Templates.
--
require "base_type"
test=class(base_type)	-- 定义一个类 test 继承于 base_type

function test:ctor()	-- 定义 test 的构造函数
    print("test ctor")
end

function test:hello()	-- 重载 base_type:hello 为 test:hello
    self:print_show("super method")
    print("hello test")
end

