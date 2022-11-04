package org.com.application;
import org.com.middleware.annotations.*;
import org.json.JSONObject;

@RequestMapping(value = "/calc")
public class Calculadora {
    private int memoria = 0;
    @GetMapping(value = "/add")
    public JSONObject soma(@RequestBody JSONObject jsonObject) throws Throwable {

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

    //Delete method, the attribute "router" is what sets the endpoint route
    @DeleteMapping(value = "/div")
    public JSONObject div(@RequestBody JSONObject jsonObject) throws Throwable {
        //Get the variables from JSON
        float a = jsonObject.getFloat("var1");
        float b = jsonObject.getFloat("var2");
        //Build the return JSON
        JSONObject result = new JSONObject();
        result.put("result", a/b);

        return result;
    }

    @PutMapping(value = "/mult")
    public JSONObject mult(@RequestBody JSONObject jsonObject) throws Throwable {

        float a = jsonObject.getFloat("var1");
        float b = jsonObject.getFloat("var2");
        //Build the return JSON
        JSONObject result = new JSONObject();
        result.put("result", a * b);
        return result;
    }

    @GetMapping(value = "/addMen")
    public JSONObject somaMem(@RequestBody  JSONObject jsonObject) {
        float num2 = jsonObject.getFloat("var1");
        //Build the return JSON
        JSONObject result = new JSONObject();
        result.put("result", memoria + num2);
        return result;
    }

    // @Authorization
    @PostMapping(value = "/subMen")
    public JSONObject subMem(@RequestBody JSONObject jsonObject) {
        float num2 = jsonObject.getFloat("var1");
        JSONObject result = new JSONObject();
        result.put("result", memoria - num2);
        return result;
    }

    // @Authorization
    @DeleteMapping(value = "/divMen")
    public JSONObject divMem(@RequestBody JSONObject jsonObject) {
        float num2 = jsonObject.getFloat("var1");
        JSONObject result = new JSONObject();
        result.put("result", memoria / num2);
        return result;
    }

    //@Authorization
    @PutMapping(value = "/multMen")
    public JSONObject multMem( @RequestBody JSONObject jsonObject) {

        float num2 = jsonObject.getFloat("var1");
        JSONObject result = new JSONObject();
        result.put("result", memoria * num2);
        return result;
    }

}


