package ro.msg.edu.jbugs.internationalization.business.control;

import javax.ejb.Stateless;


@Stateless
public class LanguageTranslator {

	public String getLanguageSelected(int languageSelectedId){
		if(languageSelectedId == 0){
			return "EN";
		}else{
			if(languageSelectedId == 1){
				return "RO";
			}
		}
		return "EN";
	}


}
