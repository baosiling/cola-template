package com.baosiling.cola.dto;

import java.util.HashMap;
import java.util.Map;

/**
 *This is the object communicate with Client.
 *The clients cloud be view layer or other HSF consumers.
 */
public abstract class ClientObject {

    protected Map<String, Object> extValues = new HashMap<>();

    public Object getExtField(String key){
        if(extValues != null){
            return extValues.get(key);
        }
        return null;
    }

    public void putExtField(String key, Object value){
        this.extValues.put(key, value);
    }

    public Map<String, Object> getExtValues(){
        return extValues;
    }

    public void setExtValues(Map<String, Object> values){
        this.extValues = values;
    }
}
