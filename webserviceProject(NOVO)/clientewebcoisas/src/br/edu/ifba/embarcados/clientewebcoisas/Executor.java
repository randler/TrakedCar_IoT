package br.edu.ifba.embarcados.clientewebcoisas;

import org.apache.http.ConnectionClosedException;

import br.edu.ifba.embarcados.clientewebcoisas.conector.Conector;

public class Executor {

	public static void main(String[] args) throws InterruptedException, ConnectionClosedException {
		Conector conector = new Conector();

		while (true) {
			Integer id = conector.getId();
			System.out.println("Sensores...");
			System.out.println("id = " + id);			
			System.out.println("Final de leitura!");
			System.out.println("...");
			
			
			Thread.sleep(1000);
		}
	}
}
