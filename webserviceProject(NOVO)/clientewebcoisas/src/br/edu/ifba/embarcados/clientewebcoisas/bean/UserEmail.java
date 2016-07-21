package br.edu.ifba.embarcados.clientewebcoisas.bean;

public final class UserEmail {
	private final static String USER_EMAIL_NAME  = "iotTrackedcar@gmail.com";
	private final static String USER_EMAIL_PASSWORD = "iotsenhaemail";
	
	private UserEmail() {
		
	}

	public static String getUSER_EMAIL_NAME() {
		return USER_EMAIL_NAME;
	}

	public static String getUSER_EMAIL_PASSWORD() {
		return USER_EMAIL_PASSWORD;
	}
	
		
}
