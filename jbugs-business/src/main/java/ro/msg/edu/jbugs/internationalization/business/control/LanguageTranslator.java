package ro.msg.edu.jbugs.internationalization.business.control;

import javax.ejb.Stateless;
import java.util.*;

@Stateless
public class LanguageTranslator {

	public Locale getCurrentLocale(int languageIndexSelected){
		if(languageIndexSelected == 0){
			return new Locale("en","US");
		}else{
			if(languageIndexSelected == 1){
				return new Locale("ro", "RO");
			}
		}
		return new Locale("en","US");
	}

	public ResourceBundle getBundle(int id){
		return ResourceBundle.getBundle("Lng" , getCurrentLocale(id));
	}

	public Map<String, String> convertResourceBundleToMap(ResourceBundle resource) {
		Map<String, String> map = new HashMap<String, String>();

		Enumeration<String> keys = resource.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			map.put(key, resource.getString(key));
		}

		return map;
	}


}
