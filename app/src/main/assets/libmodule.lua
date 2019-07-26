libmodule={}

libmodule.var = 100

function libmodule.max(n1,n2)
  print("libmodule.max :",n1,n2)
  if(n1>n2) then
    return n1;
  else
    return n2;
  end
   
end

return libmodule