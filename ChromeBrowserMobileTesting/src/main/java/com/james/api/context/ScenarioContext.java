package com.james.api.context;

import java.util.HashMap;
import java.util.Map;

import enums.Context;

public class ScenarioContext {

	//This is a HasMap object which stores the information in the Key-Value pair. Key type is String and Value can be of any Object Type.
	private  Map<String, Object> scenarioContext;

    public ScenarioContext(){
        scenarioContext = new HashMap<>();
    }

    //This method takes two parameters,  key as String and value as object. Key is nothing but a Context enum.
    public void setContext(Context key, Object value) {
        scenarioContext.put(key.toString(), value);
    }

    //This method takes the key as a parameter and returned the object which matches the key.
    public Object getContext(Context key){
        return scenarioContext.get(key.toString());
    }

    //This method performs a check on the complete Map that if it contains the key or not.
    public Boolean isContains(Context key){
        return scenarioContext.containsKey(key.toString());
    }
    
    public void clearContext(Context key) {
    	scenarioContext.remove(key.toString());
    }
}
