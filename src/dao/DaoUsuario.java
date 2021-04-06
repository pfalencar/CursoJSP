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
		String sql = "INSERT INTO usuario (login, senha, nome, cep, rua, bairro, cidade, uf, ibge, fotobase64, contenttype) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			if (beanCursoJsp.getLogin() != null) {
				preparedStatement.setString(1, beanCursoJsp.getLogin());
			} else {
				JOptionPane.showMessageDialog(null, "Insira um login válido.");
			}

			if (beanCursoJsp.getSenha() != null) {
				preparedStatement.setString(2, beanCursoJsp.getSenha());
			} else {
				JOptionPane.showMessageDialog(null, "Insira uma senha válida.");
			}

			if (beanCursoJsp.getNome() != null) {
				preparedStatement.setString(3, beanCursoJsp.getNome());
			} else {
				JOptionPane.showMessageDialog(null, "Insira um nome válido.");
			}

			if (beanCursoJsp.getCep() != null) {
				preparedStatement.setString(4, beanCursoJsp.getCep());
			} else {
				JOptionPane.showMessageDialog(null, "Insira um cep válido.");
			}

			if (beanCursoJsp.getRua() != null) {
				preparedStatement.setString(5, beanCursoJsp.getRua());
			} else {
				JOptionPane.showMessageDialog(null, "Insira uma rua válida.");
			}

			if (beanCursoJsp.getBairro() != null) {
				preparedStatement.setString(6, beanCursoJsp.getBairro());
			} else {
				JOptionPane.showMessageDialog(null, "Insira um bairro válido.");
			}

			if (beanCursoJsp.getCidade() != null) {
				preparedStatement.setString(7, beanCursoJsp.getCidade());
			} else {
				JOptionPane.showMessageDialog(null, "Insira uma cidade válida.");
			}

			if (beanCursoJsp.getEstado() != null) {
				preparedStatement.setString(8, beanCursoJsp.getEstado());
			} else {
				JOptionPane.showMessageDialog(null, "Insira um estado válido.");
			}

			if (beanCursoJsp.getIbge() != null) {
				preparedStatement.setString(9, beanCursoJsp.getIbge());
			} else {
				JOptionPane.showMessageDialog(null, "Insira um IBGE válido.");
			}
			
			if (beanCursoJsp.getFoto() != null) {
				preparedStatement.setString(10, beanCursoJsp.getFoto());
			} else {
				JOptionPane.showMessageDialog(null, "Insira a foto.");
			}
			
			if (beanCursoJsp.getContentType() != null) {
				preparedStatement.setString(11, beanCursoJsp.getContentType());
			} else {
				JOptionPane.showMessageDialog(null, "Insira o tipo de dado.");
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
			beanCursoJsp.setCep(resultSet.getString("cep"));
			beanCursoJsp.setRua(resultSet.getString("rua"));
			beanCursoJsp.setBairro(resultSet.getString("bairro"));
			beanCursoJsp.setCidade(resultSet.getString("cidade"));
			beanCursoJsp.setEstado(resultSet.getString("uf"));
			beanCursoJsp.setIbge(resultSet.getString("ibge"));
			beanCursoJsp.setFoto(resultSet.getString("fotobase64"));
			beanCursoJsp.setContentType(resultSet.getString("contentType"));
			
			lista.add(beanCursoJsp);
		}
		return lista;
	}

	public void deletar(String id) {
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

	public BeanCursoJsp consultar(String id) {
//		Long idLong = Long.parseLong(id);
		String sql = "SELECT * FROM usuario WHERE id = '" + id + "'";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				BeanCursoJsp beanCursoJsp = new BeanCursoJsp();

				beanCursoJsp.setId(resultSet.getLong("id"));
				beanCursoJsp.setLogin(resultSet.getString("login"));
				beanCursoJsp.setSenha(resultSet.getString("senha"));
				beanCursoJsp.setNome(resultSet.getString("nome"));
				beanCursoJsp.setCep(resultSet.getString("cep"));
				beanCursoJsp.setRua(resultSet.getString("rua"));
				beanCursoJsp.setBairro(resultSet.getString("bairro"));
				beanCursoJsp.setCidade(resultSet.getString("cidade"));
				beanCursoJsp.setEstado(resultSet.getString("uf"));
				beanCursoJsp.setIbge(resultSet.getString("ibge"));
				beanCursoJsp.setFoto(resultSet.getString("fotobase64"));
				beanCursoJsp.setContentType(resultSet.getString("contentType"));
				
				return beanCursoJsp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public boolean isLoginDuplicado(String login) throws Exception {
		String sql = "SELECT count(1) AS qtd FROM usuario WHERE login = '" + login + "'";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			int res = resultSet.getInt("qtd");
			return res != 0; // retorna true
		} else {
			return false;
		}
	}

	public boolean isSenhaDuplicada(String senha) throws Exception {
		String sql = "SELECT count(1) AS qtd FROM usuario WHERE senha = '" + senha + "'";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			int res = resultSet.getInt("qtd");
			return res != 0; // retorna true
		} else {
			return false;
		}
	}

	public boolean isLoginDuplicadoAtualizar(String login, String id) throws Exception {
		String sql = "SELECT count(1) AS qtd FROM usuario WHERE login = '" + login + "' AND id <> " + id;

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			int res = resultSet.getInt("qtd");
			return res > 0; // retorna true
		} else {
			return false;
		}
	}

	public boolean isSenhaDuplicadaAtualizar(String senha, String id) throws Exception {
		String sql = "SELECT count(1) AS qtd FROM usuario WHERE senha = '" + senha + "' AND id <> " + id;

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			int res = resultSet.getInt("qtd");
			return res > 0; // retorna true
		} else {
			return false;
		}
	}

	public void atualizar(BeanCursoJsp beanCursoJsp) {
		try {
			String sql = "UPDATE usuario SET login = ?, senha = ?, nome = ?, cep = ?, rua = ?, bairro = ?, cidade = ?, uf = ?, ibge = ?, fotobase64 = ?, contenttype = ? WHERE id = "
					+ beanCursoJsp.getId();

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, beanCursoJsp.getLogin());
			preparedStatement.setString(2, beanCursoJsp.getSenha());
			preparedStatement.setString(3, beanCursoJsp.getNome());
			preparedStatement.setString(4, beanCursoJsp.getCep());
			preparedStatement.setString(5, beanCursoJsp.getRua());
			preparedStatement.setString(6, beanCursoJsp.getBairro());
			preparedStatement.setString(7, beanCursoJsp.getCidade());
			preparedStatement.setString(8, beanCursoJsp.getEstado());
			preparedStatement.setString(9, beanCursoJsp.getIbge());
			preparedStatement.setString(10, beanCursoJsp.getFoto());
			preparedStatement.setString(11, beanCursoJsp.getContentType());

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
