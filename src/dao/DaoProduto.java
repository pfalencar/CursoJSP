package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			produto.setQuantidade( rs.getDouble("quantidade") );
			produto.setValor( rs.getDouble("valor") );
			
			listaProdutos.add(produto);
		}		
		return listaProdutos;		
	}

	public void deletar(String id) {
		try {
		String sql = "DELETE FROM produto WHERE id = " + id;
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.execute();
		
		connection.commit();
		
		} catch(Exception e) {
			e.printStackTrace();
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}

	public Produto consultar(String idProduto) throws Exception {
		String sql = "SELECT * FROM produto WHERE id = " + idProduto;
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.next()) {
			
			Produto produto = new Produto();
			
			produto.setId( Long.valueOf(resultSet.getInt("id")) );
			produto.setNome( resultSet.getString("nome") );
			produto.setQuantidade( resultSet.getDouble("quantidade") );
			produto.setValor( resultSet.getDouble("valor") );
			
			return produto;
		}
		
		return null;
	}

	public void salvar(Produto produto) {
		String sql = "INSERT INTO produto (nome, quantidade, valor) VALUES (?, ?, ?)";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString( 1, produto.getNome() );
			preparedStatement.setDouble( 2, produto.getQuantidade() );
			preparedStatement.setDouble( 3, produto.getValor() );
			
			preparedStatement.execute();
			connection.commit();	
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void atualizar(Produto produto) {
		String sql = "UPDATE produto SET nome = ?, quantidade = ?, valor = ? WHERE id = " + produto.getId();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString( 1, produto.getNome() );
			preparedStatement.setDouble( 2, produto.getQuantidade() );
			preparedStatement.setDouble( 3, produto.getValor() );
			
			preparedStatement.executeUpdate();
			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	//CONTINUAR NA QUINTA!!!
	public boolean isNomeProdutoDuplicado(String nome) {
		String sql = "SELECT COUNT(1) AS qtd FROM produto WHERE nome = '" + nome + "'";
		
		
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
