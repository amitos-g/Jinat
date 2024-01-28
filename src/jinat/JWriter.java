package jinat;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class JWriter {
	
	private enum flags{
		ARR("A");
		private String key;
		private flags(String key) {
			this.key = key;
		}
	}
	
	private HashMap<Object, Object> map;
	private StringBuilder result;
	
	public static String WriteFromMap(HashMap<?, ?> map) {
		JWriter writer = new JWriter(map);
		for(Entry<?, ?> entry : map.entrySet()) {
			try {
				writer.write((Entry<Object, Object>) entry);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return writer.end();
	}
	
	public JWriter(HashMap<?,?> m) {
		map = (HashMap<Object, Object>) m;
		result = new StringBuilder();
		result.append("{");

	}

	public String end() {
		result.deleteCharAt(result.length() - 1);
		result.append("\n");
		result.append("}");
		return result.toString();
	}
	public void write(Entry<Object, Object> pair) throws IOException {
		var value = pair.getValue();
		if(value instanceof String v) {
			writeString(pair);
			return;
		}
		if(value instanceof Integer v) {
			writeInt(pair);
			return;
		}
		if(value instanceof Boolean v) {
			writeBool(pair);
			return;
		}
		if(value instanceof ArrayList v) {
			writeArr(new AbstractMap.SimpleEntry(pair.getKey(), v));
			return;
		}
		if(value instanceof HashMap v) {
			writeMap(new AbstractMap.SimpleEntry(pair.getKey(), v));
			return;
		}
		else {
			throw new IOException("Invalid Json Value.");
		}
	}
	public void writeInt(Entry<?, ?> pair) {
		if(pair.getKey() == flags.ARR) {
			result.append(String.format("%s,",pair.getValue()));
		}
		else {
			result.append("\n");
			result.append(String.format("\t%s : %s,", pair.getKey(), pair.getValue()));
		}
	}
	
	public void writeString(Entry<?, ?> pair) {
		if(pair.getKey() == flags.ARR) {
			result.append(String.format("\"%s\",",pair.getValue()));
		}
		else {
		result.append("\n");
		result.append(String.format("\t%s : \"%s\",", pair.getKey(), pair.getValue()));
		}
		}
	public void writeBool(Entry<?, ?> pair) {
		if(pair.getKey() == flags.ARR) {
			result.append(String.format("%s,",pair.getValue()));
		}
		else {
		result.append("\n");
		result.append(String.format("\t%s : %s,", pair.getKey(), pair.getValue()));
	}}
	
	
	public void writeArr(Entry<?, ArrayList> pair) throws IOException {
		if(pair.getKey() == flags.ARR) {
			result.append(" [");
		}
		else {
			result.append("\n");
			result.append(String.format("\t%s : [", pair.getKey()));

		}
		for(Object value : pair.getValue()) {
			write(new AbstractMap.SimpleEntry(flags.ARR, value));
		}
		result.deleteCharAt(result.length() -1);
		result.append("]");
		result.append(',');
		
		
	}
	public void writeMap(Entry<?, HashMap<?, ?>> pair) {
		String r = JWriter.WriteFromMap(pair.getValue());
		if(pair.getKey() == flags.ARR) {
			result.append(String.format("%s,", r));
		}
		else {
			result.append("\n");
			result.append(String.format("\t%s : %s,", pair.getKey(), r));
			
		}
	}

}
