package zad1;

import java.util.List;

/*
 * Zacznijmy od trudnego (wydawa�oby si�) zadania uniezaleznienia kontrolera od rodzaju wykonywanych dzia�a�.
 * W tym celu wykorzystamy znany z literatury wzorzec projektowy Command (m.in. przedstawiany przez Bruce'a 
 * Tate w ciekawej ksi��ce "Bitter Java"; 
 * tutaj zdecydowanie jednak rozbudujemy zawarte tam sugestie). Mianowicie, wprowadzimy interfejs Command, 
 * kt�ry opisuje funkcjonalno�� szerokiej klasy (r�norodnych) dzia�a�.
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
