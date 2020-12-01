package utils;

import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Utility {
	
	
	public long parseJsonNGetTemp(String Json) throws ParseException {
		/*String tempValue="";*/
		long tempValue =0;
		Object obj = new JSONParser().parse(Json);
		JSONObject jsObj = (JSONObject) obj;
		Map tmpArr = (Map)jsObj.get("main");
		Iterator<Map.Entry> itr = tmpArr.entrySet().iterator();
		while(itr.hasNext()) {
			Map.Entry kvp = itr.next();
			if(kvp.getKey().equals("temp"))
				tempValue = (long)kvp.getValue();
		}
		return tempValue;
	}

}
