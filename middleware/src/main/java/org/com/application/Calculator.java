package org.com.application;

import lombok.NoArgsConstructor;
import org.com.middleware.annotations.*;
import org.json.JSONObject;

@NoArgsConstructor

//RequestMap annotation, the attribute "router" is what sets the class route
@RequestMapping(value = "/calc")
public class Calculator {

    //Get method, the attribute "router" is what sets the endpoint route
    @GetMapping(value = "/add")
    public JSONObject add(@RequestBody JSONObject jsonObject) throws Throwable {

        //Get the variables from JSON
        float a = jsonObject.getFloat("var1");
        float b = jsonObject.getFloat("var2");

        //Build the return JSON
        JSONObject result = new JSONObject();
        result.put("result", a+b);

        return result;
    }

    //Post method, the attribute "router" is what sets the endpoint route
    @PostMapping(value = "/sub")
    public JSONObject sub(@RequestBody JSONObject jsonObject) throws Throwable {
        //Get the variables from JSON
        float a = jsonObject.getFloat("var1");
        float b = jsonObject.getFloat("var2");
        //Build the return JSON
        JSONObject result = new JSONObject();
        result.put("result", a-b);

        return result;
    }

    //Put method, the attribute "router" is what sets the endpoint route
    @PutMapping(value= "/mul")
    public JSONObject mul(JSONObject jsonObject) throws Throwable {
        //Get the variables from JSON
        float a = jsonObject.getFloat("var1");
        float b = jsonObject.getFloat("var2");
        //Build the return JSON
        JSONObject result = new JSONObject();
        result.put("result", a*b);

        return result;
    }

    //Delete method, the attribute "router" is what sets the endpoint route
    @DeleteMapping(value = "/div")
    public JSONObject div(JSONObject jsonObject) throws Throwable {
        //Get the variables from JSON
        float a = jsonObject.getFloat("var1");
        float b = jsonObject.getFloat("var2");
        //Build the return JSON
        JSONObject result = new JSONObject();
        result.put("result", a/b);

        return result;
    }
}
