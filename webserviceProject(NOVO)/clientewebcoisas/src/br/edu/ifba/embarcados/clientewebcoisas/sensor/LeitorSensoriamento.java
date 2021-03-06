package br.edu.ifba.embarcados.clientewebcoisas.sensor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LeitorSensoriamento implements Runnable {
	
	private static final int DESLOCAMENTO_RFID = 0;
	
	private static final String ARQUIVO_PIPE = "/home/randler/arquivos_initid/sensoriamento_p";
	private boolean continuar = true;
	
	private RandomAccessFile pipe = null;
	private static Integer sensores = 0;

	public LeitorSensoriamento() {
		try {
			pipe = new RandomAccessFile(ARQUIVO_PIPE, "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		continuar = true;
		while(continuar){
			String s = "";
			try {		
				
				if(((s = pipe.readLine()) != null) && (!s.equals("")) && (!s.equals("-")) && (s.length()>1) ){
					synchronized (sensores) {
						try{
						sensores = Integer.parseInt(s);
						}catch(NumberFormatException e){
							System.out.println("ERRO convertendo: ");
						}
					}
					Thread.sleep(1000);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}			
		}
		try {
			pipe.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void parar(){
		continuar =false;
	}
	
	public static Integer getSensores(){
		synchronized (sensores) {
			return sensores;			
		}
	}
	
	public static int getRFID() {
		int id = getSensores();
		
		id = id >> DESLOCAMENTO_RFID;
		
		return id;
	}
	

}
