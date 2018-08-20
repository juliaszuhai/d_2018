package ro.msg.edu.jbugs.userManagement.business.internationalisation;

import javax.ws.rs.GET;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageTranslator {

	public Locale getCurrentLocale(int languageIndexSelected){
		if(languageIndexSelected == 1){
			return new Locale("en","EN");
		}else{
			if(languageIndexSelected == 2){
				return new Locale("ro", "RO");
			}
		}
		return new Locale("en","EN");
	}

	public ResourceBundle getBundle(Locale currentLocale){
		return ResourceBundle.getBundle("MessageBundle", currentLocale);
	}

}
