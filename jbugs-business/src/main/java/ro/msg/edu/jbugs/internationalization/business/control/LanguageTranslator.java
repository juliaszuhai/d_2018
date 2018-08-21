package ro.msg.edu.jbugs.internationalization.business.control;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageTranslator {

	public Locale getCurrentLocale(int languageIndexSelected){
		if(languageIndexSelected == 0){
			return new Locale("en","EN");
		}else{
			if(languageIndexSelected == 1){
				return new Locale("ro", "RO");
			}
		}
		return new Locale("en","EN");
	}

	public ResourceBundle getBundle(Locale currentLocale){
		return ResourceBundle.getBundle("MessageBundle", currentLocale);
	}

}
