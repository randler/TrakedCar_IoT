package br.edu.ifba.embarcados.clientewebcoisas;

import java.awt.Image;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.http.ConnectionClosedException;

import br.edu.ifba.embarcados.clientewebcoisas.bean.Usuario;
import br.edu.ifba.embarcados.clientewebcoisas.conector.Conector;
import br.edu.ifba.embarcados.clientewebcoisas.email.SendEmail;
import br.edu.ifba.embarcados.clientewebcoisas.sensor.LeitorSensoriamento;
import br.edu.ifba.embarcados.clientewebcoisas.webcam.MyWebcam;

public class Executor {

	private static MyWebcam webcam = MyWebcam.getInstancia();
	private static SendEmail email = SendEmail.getInstancia();		
	
	private static final String localSaida = "Saida A";
	private static LeitorSensoriamento leitor = null;
	private static Thread tLeitor = null;
	

	private static GregorianCalendar calendar = new GregorianCalendar();
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException, ConnectionClosedException {
		Conector conector = new Conector();
		webcam.executarWebcam();

		leitor = new LeitorSensoriamento();
		tLeitor = new Thread(leitor);
		tLeitor.start();
		
		while (true) {
				Integer id = leitor.getSensores();
				if((id != -1) && (id != 0) ){
					
					if(conector.consultaCarro(id)){
						
						Usuario usuario = new Usuario();
						usuario = conector.consultarUsuarioRoubado(id);
						conector.notificarRoubo(id);
						Image foto = webcam.tirarFoto();						
						email.sendMail(getHora(), getData(), localSaida, foto, usuario);
					}
				}
				System.out.println("id = " + id);						
				Thread.sleep(1000);
		}
		
	}
	
	
	// ---------------------------------------- Pegar Data ------------------------------
	private static String getHora(){
		return  calendar.get(Calendar.HOUR_OF_DAY) + ":" +  calendar.get(Calendar.MINUTE) + ":" +  calendar.get(Calendar.SECOND);
	}
	
	private static String getData(){
		String saida=  "";
		int dia =  calendar.get(Calendar.DAY_OF_MONTH);
		int mes =  calendar.get(Calendar.MONTH);
		int ano =  calendar.get(Calendar.YEAR);
		
		saida = dia + "/" + (mes+1) + "/" + ano;
		return saida;
	}
	
}
