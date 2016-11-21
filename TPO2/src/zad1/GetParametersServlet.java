package zad1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//DONE

/**
 * Servlet implementation class GetParametersServlet
 */
@WebServlet("/GetParametersServlet")
public class GetParametersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ServletContext context;
	private String resourceBundleServlet; // nazwa serwletu przygotowuj¹cego
											// informacje

	// Inicjacja
	public void init() {
		context = getServletContext();
		resourceBundleServlet = context.getInitParameter("resourceBundleServlet");
	}

	public void serviceRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// wlaczenie servletu przygotowujacego informacje
		RequestDispatcher dispatcher = context.getRequestDispatcher(resourceBundleServlet);
		dispatcher.include(request, response);

		// pobranie przygotowanych informacji do formularza
		String[] commandParameterNames = BundleInfo.getCommandParameterNames();
		String[] commandParameterDescriptions = BundleInfo.getCommandParameterDescriptions();
		String[] header = BundleInfo.getHeader();
		String submit = BundleInfo.getSubmit();
		String reset = BundleInfo.getReset();
		String showAll = BundleInfo.getShowAll();
		String charset = BundleInfo.getCharset();

		// Ustalenie kodowania
		request.setCharacterEncoding(charset);

		// Pobranie aktualnej sesji w ktorej atrybutach bêd¹ przechowywane
		// wartosci parametrow

		HttpSession session = request.getSession();

		// tworzenie strony

		response.setCharacterEncoding(charset);
		PrintWriter out = response.getWriter();

		// naglowek
		out.println("<center><h2>");
		for (int i = 0; i < header.length; i++)
			out.println(header[i]);
		out.println("</center></h2><hr>");

		// formularz

		out.println("<form method=\"put\">");
		for (int i = 0; i < commandParameterNames.length; i++) {

			out.print(commandParameterDescriptions[i]);
			out.print("<input type=\"text\" size=\"30\" name=\"" + commandParameterNames[i] + "\"");

			// Jezeli s¹ ju¿ wartoœci parametrów - poka¿emy je w formularzu
			String parameterValue = (String) session.getAttribute("param_" + commandParameterNames[i]);
			if (parameterValue != null)
				out.print(" value=\"" + parameterValue + "\"");
			out.println("><br>");
		}
		out.println("<br><input type=\"submit\" value=\"" + submit + "\">");
		out.println("<br><input type=\"reset\" value=\"" + reset + "\">");
		out.println("</form>");

		// Show all

		out.println("<form method=\"get\">");
		out.println("<br><input type=\"submit\" value=\"" + showAll + "\">");
		out.println("</form>");

		// pobieranie parametrow z formularza

		for (int i = 0; i < commandParameterNames.length; i++) {
			String parameterValue = request.getParameter(commandParameterNames[i]);
			session.setAttribute("param_" + commandParameterNames[i], parameterValue);
		}
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
