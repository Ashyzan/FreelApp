package com.freelapp.controller;

import org.json.JSONArray;
import org.json.JSONObject;

public class json {

	//We can also create an Array without a String by creating an empty array and adding elements to it

		JSONArray array = new JSONArray();

		//Adding elements with .put()

		array.put("value");
		array.put(5);
		array.put(-23.45e67);
		array.put(true);

		//We convert it to JSONObject providing a label arrray like last time

		JSONArray list = listNumberArray(array.length());
		JSONObject object = array.toJSONObject(list);
		System.out.println("Final JSONOBject: " + object);
	
}
