--
-- Created by IntelliJ IDEA.
-- User: 020200184
-- Date: 2019/7/31
-- Time: 19:07
-- To change this template use File | Settings | File Templates.
--
require "shape"
Circle = Shape:new()
-- 派生类方法 new
function Circle:new (o,length,breadth)
    o = o or Shape:new(o)
    setmetatable(o, self)
    self.__index = self
    self.area = length * breadth * 1/2
    return o
end

-- 派生类方法 printArea
function Circle:printArea ()
    print("扇形面积为 ",self.area)
end

function Circle:printName ()
    print("扇形面积名字 ","liuzhibao")
end