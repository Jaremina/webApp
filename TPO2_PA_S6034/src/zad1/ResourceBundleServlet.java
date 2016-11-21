package zad1;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// DONE

/**
 * Servlet implementation class ResourceBundleServlet
 */
@WebServlet("/ResourceBundleServlet")
public class ResourceBundleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String resourceBundleName;

	public void init() {
		resourceBundleName = getServletContext().getInitParameter("resourceBundleName");
	}

	public void serviceRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		ResourceBundle parametersResource = (ResourceBundle) session.getAttribute("resourceBundleName");

		// W tej sesji jeszcze nie odczytaliœmy zasobow lokalizacyjnych
		if (parametersResource == null) {
			Locale location = request.getLocale();
			parametersResource = ResourceBundle.getBundle(resourceBundleName, location);
			session.setAttribute("resourceBundleName", parametersResource);

			// Przygotowanie zasobow w wygodnej do odczytu formie
			BundleInfo.generateInfo(parametersResource);
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

class BundleInfo {

	static private String[] commandParameterNames;
	static private String[] commandParameterDescriptions;
	static private String[] header;
	static private String submit;
	static private String reset;
	static private String showAll;
	static private String[] statusCode;
	static private String charset;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static void generateInfo(ResourceBundle rb) {

		synchronized (BundleInfo.class) {

			List parameterNames = new ArrayList();
			List parametersValue = new ArrayList();
			Enumeration keys = rb.getKeys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				if (key.startsWith("param_")) {
					parameterNames.add(key.substring(6));
					parametersValue.add(rb.getString(key));
				} else if (key.equals("header"))
					setHeader(rb.getStringArray(key));
				else if (key.equals("submit"))
					setSubmit(rb.getString(key));
				else if (key.equals("reset"))
					setReset(rb.getString(key));
				else if (key.equals("showAll"))
					setShowAll(rb.getString(key));
				else if (key.equals("statusCode"))
					setStatusCode(rb.getStringArray(key));
				else if (key.equals("charset"))
					setCharset(rb.getString(key));
			}
			setCommandParameterNames((String[]) parameterNames.toArray(new String[0]));
			setCommandParameterDescriptions((String[]) parametersValue.toArray(new String[0]));
		}
	}

	public static String[] getCommandParameterNames() {
		return commandParameterNames;
	}

	public static void setCommandParameterNames(String[] commandParameterNames) {
		BundleInfo.commandParameterNames = commandParameterNames;
	}

	public static String[] getCommandParameterDescriptions() {
		return commandParameterDescriptions;
	}

	public static void setCommandParameterDescriptions(String[] commandParameterDescriptions) {
		BundleInfo.commandParameterDescriptions = commandParameterDescriptions;
	}

	public static String[] getHeader() {
		return header;
	}

	public static void setHeader(String[] header) {
		BundleInfo.header = header;
	}

	public static String getSubmit() {
		return submit;
	}

	public static void setSubmit(String submit) {
		BundleInfo.submit = submit;
	}

	public static String getReset() {
		return reset;
	}

	public static void setReset(String reset) {
		BundleInfo.reset = reset;
	}

	public static String getShowAll() {
		return showAll;
	}

	public static void setShowAll(String showAll) {
		BundleInfo.showAll = showAll;
	}

	public static String getCharset() {
		return charset;
	}

	public static void setCharset(String charset) {
		BundleInfo.charset = charset;
	}

	public static String[] getStatusCode() {
		return statusCode;
	}

	public static void setStatusCode(String[] resultCode) {
		BundleInfo.statusCode = resultCode;
	}

}