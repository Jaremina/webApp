package zad1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ResultPresentation
 */
@WebServlet("/ResultPresentationServlet")
public class ResultPresentationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	public void serviceRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext context = getServletContext();

		// W³¹czenie strony generowanej przez serwlet pobierania parametrów
		// (formularz)
		String getParametersServlet = context.getInitParameter("getParametersServlet");
		RequestDispatcher dispater = context.getRequestDispatcher(getParametersServlet);
		dispater.include(request, response);

		// pobranie wynikow zapisanych przez kontroler

		HttpSession ses = request.getSession();
		Lock mainLock = (Lock) ses.getAttribute("lock");
		mainLock.unlock();
		List results = (List) ses.getAttribute("results");
		Integer code = (Integer) ses.getAttribute("statusCode");

		PrintWriter out = response.getWriter();
		out.println("<hr>");

		// Uzyskanie napisu w³aœciwego dla danego "statusCode"
		String msg = BundleInfo.getStatusCode()[code.intValue()];
		out.println("<h2>" + msg + "</h2>");

		// generowanie wynikow
		out.println("<ul>");
		for (Iterator iterator = results.iterator(); iterator.hasNext();) {
			out.println("<li>");

			Object res = iterator.next();
			if (res.getClass().isArray()) { // jezeli element wyniku jest
											// tablica
				Object[] res1 = (Object[]) res;
				int i;
				for (i = 0; i < res1.length; i++) {
					out.print(res1[i] + " ");
				}
			} else { // jezeli element wyniku nie jest tablica
				out.print(res);
			}
			out.println("</li>");
		}
		out.println("</ul>");
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
