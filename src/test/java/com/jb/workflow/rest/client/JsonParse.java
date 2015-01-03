package com.jb.workflow.rest.client;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.util.json.JSONStringer;
import org.junit.Test;

public class JsonParse {
	
	@Test
	public void testJsonParse() {
		
		JSONObject params = new JSONObject();
		params.put("action", "complete");
		
		JSONArray arr = new JSONArray();
		
		//arr.put(new JSONObject("{\"name\" : \"" + action + "\"}"));
		JSONObject obj = new JSONObject();
		obj.put("name", "errrric");
		obj.put("value", "ddddd");
		arr.put(obj);
		
		obj = new JSONObject();
		obj.put("name", "dadad");
		obj.put("value", "ddwaegggrrddd");
		arr.put(obj);
		
		//arr.put(new JSONObject());
		
		params.put("variables", new JSONArray(arr.toString()));
		params.put("variables2", arr.toString());
		params.put("variables3", new JSONArray("[]"));
		
		//JSONStringer jsStr = new JSONStringer();
		System.out.println(params);
	}
}
