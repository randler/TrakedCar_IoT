package br.edu.ifba.embarcados.webcoisas.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static String timeZone_SSL = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
	private static String url = "jdbc:mysql://localhost:3306/TrackedCar"+ timeZone_SSL; //Nome da base de dados
	private static String user = "root"; //nome do usuário do MySQL
	private static String password = "123456"; //senha do MySQL
	
	private static ConnectionFactory conection = null;
	
	
	private ConnectionFactory() {
		}
	public static ConnectionFactory getInstancia(){
		if(conection == null)
			conection = new ConnectionFactory();
		return conection;
	}
	
	
	public static Connection getConnection(){
		try {			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			return DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	

}
