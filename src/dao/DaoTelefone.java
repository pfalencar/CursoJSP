package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Telefone;
import connection.SingleConnection;

public class DaoTelefone {

	private Connection connection;

	public DaoTelefone() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(Telefone telefone) {
		String sql = "INSERT INTO telefone (numero, tipo, usuario) VALUES (?, ?, ?)";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, telefone.getNumero());
			preparedStatement.setString(2, telefone.getTipo());
			preparedStatement.setLong(3, telefone.getUsuario());

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

	public List<Telefone> listar() {
		String sql = "SELECT * FROM telefone";

		List<Telefone> listaTelefones = new ArrayList<Telefone>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Telefone telefone = new Telefone();

				telefone.setId(resultSet.getLong("id"));
				telefone.setNumero(resultSet.getString("numero"));
				telefone.setTipo(resultSet.getString("tipo"));
				telefone.setUsuario(resultSet.getLong("usuario"));

				listaTelefones.add(telefone);
			}
			return listaTelefones;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deletar(String id) {
		String sql = "DELETE FROM telefone WHERE id = ?" + id;

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public Telefone consultar(String idTelefone) {
		String sql = "SELECT * FROM telefone WHERE id = " + idTelefone;
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				Telefone telefone = new Telefone();
				
				telefone.setId( resultSet.getLong("id") );
				telefone.setNumero( resultSet.getString("numero") );
				telefone.setTipo( resultSet.getString("tipo") );
				telefone.setUsuario( resultSet.getLong("usuario") );
				
				return telefone;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void atualizar (Telefone telefone) {
		String sql = "UPDATE telefone SET numero = ?, tipo = ?, usuario = ? WHERE id = " + telefone.getId();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString( 1, telefone.getNumero() );
			preparedStatement.setString( 2, telefone.getTipo() );
			preparedStatement.setLong( 3, telefone.getUsuario() );
			
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

}
