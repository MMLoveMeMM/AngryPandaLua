--
-- Created by IntelliJ IDEA.
-- User: 020200184
-- Date: 2019/7/31
-- Time: 19:43
-- To change this template use File | Settings | File Templates.
--
require "base"
base_type=class()		-- 定义一个基类 base_type

function base_type:ctor(x)	-- 定义 base_type 的构造函数
    print("base_type ctor")
    self.x=x
end

function base_type:print_x()	-- 定义一个成员函数 base_type:print_x
    print(self.x)
end

function base_type:print_show(info)	-- 定义一个成员函数 base_type:print_x
    print(info)
end

function base_type:hello()	-- 定义另一个成员函数 base_type:hello
    print("hello base_type")
end

