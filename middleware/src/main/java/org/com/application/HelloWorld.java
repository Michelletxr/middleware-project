package org.com.application;

import org.com.middleware.annotations.GetMapping;
import org.com.middleware.annotations.PostMapping;
import org.com.middleware.annotations.RequestBody;
import org.com.middleware.annotations.ResquestMapping;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResquestMapping(value = "/")
public class HelloWorld {
    @GetMapping()
    public String helo() {
        return "hello";
    }

    @PostMapping(value = "/name")
    public String HelloName(@RequestBody ObjHello hello ) {
        return "helo " + hello.name;
    }
    record ObjHello(String name){}
}