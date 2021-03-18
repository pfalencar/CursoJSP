package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import beans.BeanCursoJsp;
import connection.SingleConnection;

public class DaoUsuario {

	private Connection connection;

	public DaoUsuario() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanCursoJsp beanCursoJsp) {
		String sql = "INSERT INTO usuario (login, senha, nome) VALUES (?, ?, ?)";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			if (beanCursoJsp.getLogin() != null) {
				preparedStatement.setString(1, beanCursoJsp.getLogin());
			} else {
				JOptionPane.showMessageDialog(null, "Insira um login v�lido.");
			}
			
			if (beanCursoJsp.getSenha() != null) {
				preparedStatement.setString(2, beanCursoJsp.getSenha());
			} else {
				JOptionPane.showMessageDialog(null, "Insira uma senha v�lida.");
			}
			
			if (beanCursoJsp.getNome() != null) {
				preparedStatement.setString(3, beanCursoJsp.getNome());
			} else {
				JOptionPane.showMessageDialog(null, "Insira um nome v�lido.");
			}
			
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

	public List<BeanCursoJsp> listar() throws Exception {
		List<BeanCursoJsp> lista = new ArrayList<BeanCursoJsp>();

		String sql = "SELECT * FROM usuario";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			BeanCursoJsp beanCursoJsp = new BeanCursoJsp();
			beanCursoJsp.setId(resultSet.getLong("id"));
			beanCursoJsp.setLogin(resultSet.getString("login"));
			beanCursoJsp.setSenha(resultSet.getString("senha"));
			beanCursoJsp.setNome(resultSet.getString("nome"));
			
			lista.add(beanCursoJsp);
		}
		return lista;
	}

	public void delete(String id) {
		try {
//			Long idLong = Long.parseLong(id);
			String sql = "DELETE FROM usuario WHERE id = '" + id + "'";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
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

	public BeanCursoJsp consultar(String id) throws Exception {
//		Long idLong = Long.parseLong(id);
		String sql = "SELECT * FROM usuario WHERE id = '" + id + "'";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			BeanCursoJsp beanCursoJsp = new BeanCursoJsp();
			beanCursoJsp.setId(resultSet.getLong("id"));
			beanCursoJsp.setLogin(resultSet.getString("login"));
			beanCursoJsp.setSenha(resultSet.getString("senha"));
			beanCursoJsp.setNome(resultSet.getString("nome"));
			
			return beanCursoJsp;
		}

		return null;
	}

	public void atualizar(BeanCursoJsp beanCursoJsp) {
		try {
			String sql = "UPDATE usuario SET login = ?, senha = ?, nome = ?  WHERE id = " + beanCursoJsp.getId();

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, beanCursoJsp.getLogin());
			preparedStatement.setString(2, beanCursoJsp.getSenha());
			preparedStatement.setString(3, beanCursoJsp.getNome());
			
			preparedStatement.executeUpdate();

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

}