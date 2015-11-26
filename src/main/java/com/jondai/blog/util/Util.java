package com.jondai.blog.util;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Util {
	public static Map<String, Object> listToMapByKey(List<? extends Object> list, String key) {
		if (list == null || list.size() < 1)
			return null;
		@SuppressWarnings("serial")
		List<Map<String, Object>> tmp = (new Gson()).fromJson((new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create().toJson(list),
				new TypeToken<List<Map<String, Object>>>() {
				}.getType());
		Map<String, Object> result = new HashMap<String, Object>();
		for (Map<String, Object> m : tmp) {
			result.put(m.get(key).toString(), m);
		}
		return result;
	}

	public static String revEncrypt(String str) {
		if (str == null || str.equals(""))
			return "";
		try {
			Encrypt ent = new Encrypt();
			ent.init("jondai");
			String sString = null;
			byte[] cleartext = str.getBytes();
			byte[] ciphertext = ent.Encrypt(cleartext);
			sString = new String(Base64.base64Encode(ciphertext));

			return sString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void addAndOrderTask(Map<String, Object> line, List<Map<String, Object>> tasks) {
		int counts = tasks.size();
		for (int i = 0; i < counts; i++) {
			if (((Date) (line.get("taskcreatetime"))).after((Date) tasks.get(i).get("taskcreatetime"))) {
				tasks.add(i, line);
				return;
			} else {
				continue;
			}
		}
		tasks.add(line);
	}

	public static void addAndOrderAsc(Map<String, Object> map, List<Map<String, Object>> list, String byfield) {
		if(map==null) return;
		int counts = list.size();
		for (int i = 0; i < counts; i++) {
			if (map.get(byfield).toString().compareTo(list.get(i).get(byfield).toString()) < 0) {
				list.add(i, map);
				return;
			} else {
				continue;
			}
		}
		list.add(map);
	}

	public static void addAndOrderDesc(Map<String, Object> map, List<Map<String, Object>> list, String byfield) {
		if(map==null) return;
		int counts = list.size();
		for (int i = 0; i < counts; i++) {
			if (map.get(byfield).toString().compareTo(list.get(i).get(byfield).toString()) > 0) {
				list.add(i, map);
				return;
			} else {
				continue;
			}
		}
		list.add(map);
	}

	public static Integer integerAdd(Integer d1, Integer d2) {
		return (d1 == null ? 0 : d1) + (d2 == null ? 0 : d2);
	}

	public static Double doubleAdd(Double d1, Double d2) {
		return (d1 == null ? 0.0 : d1) + (d2 == null ? 0.0 : d2);
	}

	public static String createRandomNumber(int length) {
		String result = "";
		Random rdm = new Random(System.currentTimeMillis());
		for (int i = 0; i < length; i++) {
			result += Math.abs(rdm.nextInt()) % 10;
		}
		return result;
	}



}
