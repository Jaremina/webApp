package zad1;
import java.util.ListResourceBundle;

public class Messages_en extends ListResourceBundle{

	static final Object [][] content = {
			{"header",new String[] {"Books database"}},
			{"param_author","Author: "},
			{"param_title","Title: "},
			{"param_ISBN","ISBN: "},
			{"param_publisher","Publisher: "},
			{"param_yearFrom","Year from: "},
			{"param_yearTo","Year to: "},
			{"param_priceFrom","Price from: "},
			{"param_priceTo","Price to: "},
			{"submit","Submit"},
			{"reset","Reset"},
			{"showAll","Show All"},
			{"charset","ISO-8859-1"},
			{"statusCode",new String []
					{"Result", "Missing database",
					 "SQL error"}
			}			
	};
	
	@Override
	protected Object[][] getContents() {
		return content;
	}

}
