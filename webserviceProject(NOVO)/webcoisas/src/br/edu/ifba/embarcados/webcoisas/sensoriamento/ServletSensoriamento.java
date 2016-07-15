package br.edu.ifba.embarcados.webcoisas.sensoriamento;



import javax.servlet.ServletException;

import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.sun.jersey.spi.container.servlet.WebConfig;

@SuppressWarnings("serial")
public class ServletSensoriamento extends ServletContainer {
	
	@Override
	protected void init(WebConfig webConfig) throws ServletException {
		System.out.println("Iniciando servico web...");
		super.init(webConfig);
	}

	@Override
	public void destroy() {
		System.out.println("Finalizando servico web...");
		super.destroy();
	}
}
