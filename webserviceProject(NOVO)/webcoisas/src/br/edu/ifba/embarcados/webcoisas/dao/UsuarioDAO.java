package br.edu.ifba.embarcados.webcoisas.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.embarcados.webcoisas.bean.Usuario;

import com.mysql.cj.jdbc.PreparedStatement;

public class UsuarioDAO {
	
	private static UsuarioDAO usuario = null;
	
	private UsuarioDAO(){
		
	}
	
	public static UsuarioDAO getInstancia(){
		if(usuario == null){
			usuario = new UsuarioDAO();
		}
		return usuario;
	}
	

	public void adicionarUsuario(Usuario usuario, Connection con) throws SQLException{
		 // cria um preparedStatement
       String sql = "insert into Usuario" +
               " (idUsuario,nomeUsuario,emailUsuario)" +
               " values (?,?,?)";
       PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
       
       
       // preenche os valores
       stmt.setInt(1, usuario.getId());
       stmt.setString(2, usuario.getNome());
       stmt.setString(3, usuario.getEmail());
       // executa
       stmt.execute();
       stmt.close();

       System.out.println("Gravado!");
		
	}

	public Usuario pesquisarUsuario(int id, Connection con) throws SQLException{
		 // cria um preparedStatement
		Usuario usuario = new Usuario();
		
		try{
		PreparedStatement stmt = (PreparedStatement) con.prepareStatement("select * from Usuario where idUsuario = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
					
		while(rs.next()){				
			usuario.setId(rs.getInt("idUsuario"));
			usuario.setNome(rs.getString("nomeUsuario"));
			usuario.setEmail(rs.getString("emailUsuario"));
			
		}		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
      return usuario;
      
	}
	
}
