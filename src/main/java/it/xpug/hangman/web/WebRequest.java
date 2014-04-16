package it.xpug.hangman.web;

import it.xpug.hangman.domain.*;

public interface WebRequest {

	String getPath();

	String getMethod();

	String getParameter(String name);

	UserId getUserId();

	String getPrisonerId();

	boolean isGet();

	boolean isPost();

}
