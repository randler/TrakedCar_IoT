package br.ifba.edu.iot.sensoriamento;

import javax.servlet.ServletException;

import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.sun.jersey.spi.container.servlet.WebConfig;

@SuppressWarnings("serial")
public class ServletSensoriamento extends ServletContainer {

	private LeitorSensoriamento leitor = null;
	private Thread tLeitor = null;
	
	@Override
	protected void init(WebConfig webConfig) throws ServletException {
		System.out.println("Iniciando servico web...");
		
		leitor = new LeitorSensoriamento();
		tLeitor = new Thread(leitor);
		tLeitor.start();
		
		super.init(webConfig);
	}

	@Override
	public void destroy() {
		System.out.println("Finalizando servico web...");
		
		leitor.parar();
		
		try {
			tLeitor.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		super.destroy();
	}
}
