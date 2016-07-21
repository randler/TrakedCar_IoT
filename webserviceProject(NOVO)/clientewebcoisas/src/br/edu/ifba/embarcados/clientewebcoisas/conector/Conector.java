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

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import br.edu.ifba.embarcados.clientewebcoisas.bean.Carro;
import br.edu.ifba.embarcados.clientewebcoisas.bean.Usuario;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
	
		
	public Integer getId() {
		Integer id = -1;

		String sid = acessar("id");
		if (sid != "") {
			id = Integer.parseInt(sid);
		}

		return id;
	}

	public boolean consultaCarro(int id) {
		boolean saida = false;
		String sid = acessar("consultar/"+id);
		
		if(sid != ""){
			if(sid.equals("true"))
				saida = true;
		}
		
		
		return saida;
	}
	
	public Usuario consultarUsuarioRoubado(int id) {
		String user_id = acessar("consultarRoubo/"+id);
		Usuario user = new Usuario();
		
		Gson gson = new GsonBuilder().create();
		user = gson.fromJson(user_id, Usuario.class); 
		
		return user;
	}


	public boolean notificarRoubo(Integer id) {
		boolean saida = false;
		
		String sid = acessar("notificarRoubo/"+id);
		if(sid != ""){
			if(sid.equals("true"))
				saida = true;
		}
		
		
		return saida;
	}
	

	
	
}
