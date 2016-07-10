package br.edu.ifba.embarcados.clientewebcoisas.conector;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import br.edu.ifba.embarcados.clientewebcoisas.bean.Carro;

import com.google.gson.Gson;

@SuppressWarnings("deprecation")
public class Conector {

	private static final String ENDERECO_WS = "http://localhost:8080/webcoisas/v1/servico/";
	private static ArrayList<Carro> carro = new ArrayList<Carro>();
	
	private String acessar(String urlFuncao){
		String resultado = "";		
		
		@SuppressWarnings("resource")
		HttpClient cliente = new DefaultHttpClient();
		HttpGet get = new HttpGet(ENDERECO_WS + urlFuncao);
		HttpResponse resposta;
		
		try {
			resposta = cliente.execute(get);
			BufferedReader br = new BufferedReader(new InputStreamReader(resposta.getEntity().getContent()));

			resultado = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultado;
		
		
	}
	
	private void adicionar(String url, String message) throws IOException, IllegalStateException, UnsupportedEncodingException, RuntimeException{
	
		try{
		URL urlC = new URL(ENDERECO_WS+url);
		HttpURLConnection conn = (HttpURLConnection) urlC.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		
		
		OutputStream os = conn.getOutputStream();
		os.write(message.getBytes());
		os.flush();

//		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
//			throw new RuntimeException("Failed : HTTP error code : "
//				+ conn.getResponseCode());
//		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
		
		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();

	} catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	 }

	
	}
	
	
	public Integer getId() {
		Integer id = -1;

		String sid = acessar("id");
		if (sid != "") {
			id = Integer.parseInt(sid);
		}

		return id;
	}

	public boolean consultaCarro() {
		boolean saida = false;
		String sid = acessar("consultar");
		
		if(sid != ""){
			System.out.println(sid);
			saida = true;
		}
		
		return saida;
	}
	
	public boolean adicionar() {
		boolean saida = false;
		
	    String message = "[{\"idUsuario\":\"1\",\"nomeUsuario\":\"Joao\",\"email\":\"Joao@joao\",\"idCarro\":\"123456789\",\"nomeCarro\":\"Uno\",\"marcaCarro\":\"Fiat\",\"anoCarro\":\"1\",\"Usuario_idUsuario\":\"1\"}]";
	    
	    try {
	    	
	  	   	adicionar("adicionar", message);
			saida = true;
			
	    } catch (IOException | RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
		
		return saida;
	}
	
	
}
