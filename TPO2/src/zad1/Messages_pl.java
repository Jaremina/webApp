package zad1;
import java.util.ListResourceBundle;

public class Messages_pl extends ListResourceBundle{

	static final Object [][] content = {
			{"header",new String[] {"Baza danych ksia�ek"}},
			{"param_author","Autor: "},
			{"param_title","Tytu�: "},
			{"param_ISBN","ISBN: "},
			{"param_publisher","Wydawca: "},
			{"param_yearFrom","Data wydania od: "},
			{"param_yearTo","Data wydania do: "},
			{"param_priceFrom","Cena od: "},
			{"param_priceTo","Cena do: "},
			{"submit","Wy�lij"},
			{"reset","Wyczy��"},
			{"showAll","Poka� wszystkie pozycje"},
			{"charset","ISO-8859-2"},
			{"statusCode",new String []
					{"Wynik", "Brak bazy",
					 "b��d SQL"}
			}			
	};
	
	@Override
	protected Object[][] getContents() {
		return content;
	}

}
