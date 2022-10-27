package org.com.application;

import lombok.NoArgsConstructor;
import org.com.middleware.annotations.*;
import org.json.JSONObject;

@NoArgsConstructor
@RequestMapping(value = "/hello2")
public class HelloWorld2 {

    @Authorization
    @PutMapping("/put")
    public String helo() {
        return "hello2 get";
    }

    @DeleteMapping("/delete")
    public String HelloName(@RequestBody JSONObject hello ) {
        return "helo post" ;
    }
    public record ObjHello(String name){}
}
