package test;
import java.util.*;

import jinat.JWriter;
public class Main {

	public static void main(String[] args) {

		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("is-x", true);
		map.put("age", 18);
		map.put("name", "amitos");
		map.put("is-good", new ArrayList(List.of(2,2,2,2,"amit",new ArrayList(List.of(2,2,2)),16)));
		HashMap<String, Object> indented = new HashMap<String, Object>();
		indented.put("am", 1);
		indented.put("arr", new ArrayList(List.of(2,2,2,2)));
		map.put("ind", indented);
		System.out.println(JWriter.WriteFromMap(map));
	}

}
