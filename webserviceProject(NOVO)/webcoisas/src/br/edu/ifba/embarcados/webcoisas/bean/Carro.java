package br.edu.ifba.embarcados.webcoisas.bean;


public class Carro {

	private int id;
	private String nome;
	private boolean status;
	private String marca;
	private String ano;
	private int usuarioCarro;
	
	
	public Carro(){
		
	}
	
	public Carro(int id, String nome, String marca, String ano,
			int usuarioCarro) {
		super();
		this.id = id;
		this.nome = nome;
		this.marca = marca;
		this.status = false;
		this.ano = ano;
		this.usuarioCarro = usuarioCarro;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public int getUsuarioCarro() {
		return usuarioCarro;
	}
	public void setUsuarioCarro(int usuarioCarro) {
		this.usuarioCarro = usuarioCarro;
	}
	
	
	
	
}
