package org.com.application;

import org.com.middleware.annotations.*;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@NoArgsConstructor
@ResquestMapping(value = "/")
public class HelloWorld {
    @GetMapping("/")
    public String helo() {
        System.out.println("chamando metodo");
        return "hellsos";
    }

    @PostMapping(value = "/name")
    public String HelloName(@RequestBody ObjHello hello ) {
        System.out.println("chamando metodo" + hello);
        return "helo " ;
    }

   public record ObjHello(String name){}
}