package zad1;

import java.util.List;

/*
 * Zacznijmy od trudnego (wydawa³oby siê) zadania uniezaleznienia kontrolera od rodzaju wykonywanych dzia³añ.
 * W tym celu wykorzystamy znany z literatury wzorzec projektowy Command (m.in. przedstawiany przez Bruce'a 
 * Tate w ciekawej ksi¹¿ce "Bitter Java"; 
 * tutaj zdecydowanie jednak rozbudujemy zawarte tam sugestie). Mianowicie, wprowadzimy interfejs Command, 
 * który opisuje funkcjonalnoœæ szerokiej klasy (ró¿norodnych) dzia³añ.
 */

public interface Command {

	void init();

	void setParameter(String name, Object value);

	Object getParameter(String name);

	void execute();

	@SuppressWarnings("rawtypes")
	List getResults();

	void setStatusCode(int code);

	int getStatusCode();

}
