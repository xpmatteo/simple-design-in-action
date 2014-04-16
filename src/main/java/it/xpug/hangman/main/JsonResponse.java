package it.xpug.hangman.main;

import static javax.servlet.http.HttpServletResponse.*;
import it.xpug.hangman.web.*;

import java.util.*;

import javax.servlet.http.*;

import org.json.*;
import org.mortbay.util.ajax.*;

public class JsonResponse implements WebResponse {

	private HttpServletResponse httpServletResponse;
	private Map<String, Object> objects = new HashMap<String, Object>();

	public JsonResponse(HttpServletResponse response) {
		this.httpServletResponse = response;

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
	}

	@Override
	public void methodNotAllowed(String description) {
		objects.put("description", description);
		setStatus(SC_METHOD_NOT_ALLOWED, "Method not allowed");
	}

	@Override
	public void created(String path) {
		objects.put("location", path);
		httpServletResponse.setHeader("Location", path);
		setStatus(SC_CREATED, "Created");
	}

	@Override
	public void forbidden(String description) {
		objects.put("description", description);
		setStatus(SC_FORBIDDEN, "Forbidden");
	}

	@Override
	public void put(String name, Object value) {
		objects.put(name, value);
	}

	@Override
	public String toString() {
		String string = JSON.toString(objects);
		return new JSONObject(string).toString(2);
	}

	@Override
	public void validationError(String message) {
		setStatus(400, "Bad Request");
		objects.put("description", message);
	}

	private void setStatus(int statusCode, String statusDescription) {
		objects.put("status", statusDescription);
		objects.put("status_code", statusCode);
		httpServletResponse.setStatus(statusCode);
	}
}
