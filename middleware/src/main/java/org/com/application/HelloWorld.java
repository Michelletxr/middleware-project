package org.com.application;

import org.com.middleware.annotations.*;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@NoArgsConstructor
@RequestMapping(value = "/hello")
public class HelloWorld {
    @GetMapping("")
    public String helo() {
        return "hello get";
    }

    @PostMapping(value = "/teste")
    public String HelloName(@RequestBody JSONObject hello ) {
        return "helo post" ;
    }
   public record ObjHello(String name){}
}