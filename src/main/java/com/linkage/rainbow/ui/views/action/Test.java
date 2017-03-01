package com.linkage.rainbow.ui.views.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JSONArray jsonArray = JSONArray.fromObject(test());
		System.out.println(jsonArray);

	}

	public static List<Map<String, String>> test() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("productid", "FI-SW-01");
		map.put("unitcost", "10.00");
		map.put("status", "P");
		map.put("listprice", "36.50");
		map.put("attr1", "Large");
		map.put("itemid", "EST-1");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("productid", "K9-DL-01");
		map.put("unitcost", "12.00");
		map.put("status", "P");
		map.put("listprice", "18.50");
		map.put("attr1", "Spotted Adult Female");
		map.put("itemid", "EST-10");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("productid", "RP-SN-01");
		map.put("unitcost", "12.00");
		map.put("status", "P");
		map.put("listprice", "28.50");
		map.put("attr1", "Venomless");
		map.put("itemid", "EST-11");
		list.add(map);
		return list;
	}

}
