--
-- Created by IntelliJ IDEA.
-- User: 020200184
-- Date: 2019/8/5
-- Time: 11:25
-- To change this template use File | Settings | File Templates.
--

require "shape"
require "circle"
Triangle = Shape:new()
-- 派生类方法 new
function Triangle:new (o)
    o = o or Shape:new(o)
    setmetatable(o, self)
    self.__index = self
    -- self.area = length * breadth * 1/2
    mycircle = Circle:new(nil,10,12)
    mycircle:printName()

    return o
end

