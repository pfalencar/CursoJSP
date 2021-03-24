package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import beans.Produto;
import connection.SingleConnection;

public class DaoProduto {
	
	
	private Connection connection;
	
	public DaoProduto() {
		connection = SingleConnection.getConnection();
	}

	public List<Produto> listar() throws Exception {
		List<Produto> listaProdutos = new ArrayList<Produto>();
		
		String sql = "SELECT * FROM produto";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			
			Produto produto = new Produto();
			
			produto.setId( Long.valueOf(rs.getInt("id")) );
			produto.setNome(rs.getString("nome"));
			produto.setQuantidade( rs.getInt("quantidade") );
			produto.setValor( rs.getDouble("valor") );
			
			listaProdutos.add(produto);
		}
		
		return listaProdutos;
		
	}



}
