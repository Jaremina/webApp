package zad1;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ServletContext context;
	private Command command; // obiekt klasy dzialania
	private String presentationServlet; // nazwa serwlet prezentacji
	private String getParametersServlet;// nazwa serwletu pobierania parametrów

	@SuppressWarnings("rawtypes")
	public void init() {

		context = getServletContext();

		presentationServlet = context.getInitParameter("presentationServlet");
		getParametersServlet = context.getInitParameter("getParametersServlet");
		String commandClassName = context.getInitParameter("commandClassName");
		String dbName = context.getInitParameter("dbName");

		// Zaladowanie klasy Command i utworzenie jej egzemplarza
		// który bedzie wykonywal pracê

		try {
			Class commandClass = Class.forName(commandClassName);
			command = (Command) commandClass.newInstance();

			// ustalamy baze danych i iniujemy obiekt command
			command.setParameter("dbName", dbName);
			command.init();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void serviceRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		// pobieranie parametrow od servletu parametrow
		RequestDispatcher dispater = context.getRequestDispatcher(getParametersServlet);
		dispater.include(request, response);

		// pobranie bierzacej sesji i zapisanych w niej wartosci atrybutow

		HttpSession session = request.getSession();

		String[] parameterNames = BundleInfo.getCommandParameterNames();

		for (int i = 0; i < parameterNames.length; i++) {
			String parameterValue = (String) session.getAttribute("param_" + parameterNames[i]);
			command.setParameter(parameterNames[i], parameterValue);
		}

		// blokowanie dostêpu do zasobow innym watkom, odblokowanie nastapi po
		// pokazaniu wynikow
		Lock mainLock = new ReentrantLock();

		mainLock.lock();
		command.execute();

		// pobranie wynikow

		@SuppressWarnings("rawtypes")
		List results = (List) command.getResults();

		session.setAttribute("statusCode", new Integer(command.getStatusCode()));
		session.setAttribute("results", results); // zapisujemy wyniik w
													// atrybutach sesji
		session.setAttribute("lock", mainLock); // aby mozna go bylo otworzyc w
												// servlecie prezencji

		// przekazuje zlecenie do servletu prezencji i konczy swoje zadanie
		dispater = context.getRequestDispatcher(presentationServlet);
		dispater.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		serviceRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		serviceRequest(request, response);
	}
}
