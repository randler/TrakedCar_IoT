package br.edu.ifba.embarcados.webcoisas.sensoriamento;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LeitorSensoriamento implements Runnable {
	
	private static final int DESLOCAMENTO_RFID = 26;
	private static final int DESLOCAMENTO_UMID = 18;
	private static final int DESLOCAMENTO_TEMP = 10;
	
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
				
				if(((s = pipe.readLine()) != null) && (!s.equals("")) ){
					synchronized (sensores) {
						sensores = Integer.parseInt(s);						
					}
					Thread.sleep(1000);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				
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
	
	public static int getTemperatura() {
		int temp = getSensores();
		
		temp = (temp & 261120) >> DESLOCAMENTO_TEMP;
		
		return temp;
	}
	
	public static int getUmidade() {
		int umid = getSensores();
		
		umid = (umid & 66846720) >> DESLOCAMENTO_UMID;
		
		return umid;
	}
	
	public static int getLuminosidade() {
		int lumi = getSensores();
		
		lumi = (lumi & 1023);
		
		return lumi;
	}

}
