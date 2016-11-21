package zad1;
import java.util.*;
import java.io.*;

public class CommandImplementation implements Serializable, Command {

	
	private static final long serialVersionUID = -9156132076603579912L;
	
	@SuppressWarnings("rawtypes")
	private Map parameterMap = new HashMap();
	@SuppressWarnings("rawtypes")
	private List resultList = new ArrayList();

	private int statusCode;

	public void init() {
	}
	
	public void execute() {
	}

	@SuppressWarnings("unchecked")
	public void setParameter(String name, Object value) {
		parameterMap.put(name, value);
	}

	public Object getParameter(String name) {
		return parameterMap.get(name);
	}

	@SuppressWarnings("rawtypes")
	public List getResults() {
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public void addResult(Object o) {
		resultList.add(o);
	}

	public void addResult(String s) {
		addResult(new Object[] { s });
	}

	public void clearResult() {
		resultList.clear();
	}

	public void setStatusCode(int code) {
		statusCode = code;
	}

	public int getStatusCode() {
		return statusCode;
	}

}