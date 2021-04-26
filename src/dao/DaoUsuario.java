package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanCursoJsp;
import connection.SingleConnection;

public class DaoUsuario {

	private Connection connection;

	public DaoUsuario() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanCursoJsp beanCursoJsp) {
		String sql = "INSERT INTO usuario (login, senha, nome, cep, rua, bairro, cidade, uf, ibge, fotobase64, contenttype, curriculobase64, contenttypecurriculo, fotominiatura) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
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
			preparedStatement.setString(12, beanCursoJsp.getCurriculoBase64());
			preparedStatement.setString(13, beanCursoJsp.getContentTypeCurriculo());
			preparedStatement.setString(14, beanCursoJsp.getMiniaturaFoto());

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
//			beanCursoJsp.setFoto(resultSet.getString("fotobase64"));
			beanCursoJsp.setContentType(resultSet.getString("contenttype"));
			beanCursoJsp.setCurriculoBase64(resultSet.getString("curriculobase64"));
			beanCursoJsp.setContentTypeCurriculo(resultSet.getString("contenttypecurriculo"));
			beanCursoJsp.setMiniaturaFoto(resultSet.getString("fotominiatura"));

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
				beanCursoJsp.setContentType(resultSet.getString("contenttype"));
				beanCursoJsp.setCurriculoBase64(resultSet.getString("curriculobase64"));
				beanCursoJsp.setContentTypeCurriculo(resultSet.getString("contenttypecurriculo"));
				beanCursoJsp.setMiniaturaFoto(resultSet.getString("fotominiatura"));

				return beanCursoJsp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/*Responsável por validar se há 2 logins iguais (não pode existir 1 mesmo login para 2 usuários diferentes)*/
	public boolean isLoginDuplicado(String login) throws Exception {
		String sql = "SELECT count(1) AS qtd FROM usuario WHERE login = '" + login + "'";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			int res = resultSet.getInt("qtd");
			return res != 0; // se esse retorno for != 0, então é true, e já tem um usuário com esse login no BD.
		} else {
			return false; // quando o resultado é igual a zero, ou seja, não tem nenhum usuário com esse login no BD.
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
			return res != 0; // retorna true: já tem um id no BD com esse login. Tem esse login para um id diferente do que estou pesquisando.
		} else {
			return false; //se res=0, então não tem esse login para um id diferente do que estou pesquisando.
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
			StringBuilder sqlBuilder = new StringBuilder("UPDATE usuario SET login = ?, senha = ?, nome = ?, cep = ?, rua = ?,"
					+ " bairro = ?, cidade = ?, uf = ?, ibge = ?, fotobase64 = ?, contenttype = ?, curriculobase64 = ?, "
					+ "contenttypecurriculo = ?");
			
			boolean isMiniaturaFoto = beanCursoJsp.getMiniaturaFoto() != null && !beanCursoJsp.getMiniaturaFoto().isEmpty();			
			if (isMiniaturaFoto) { 
				sqlBuilder.append(", fotominiatura = ?"); 
			}
			
			sqlBuilder.append("  WHERE id = ").append(beanCursoJsp.getId());
			String sql = sqlBuilder.toString();

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
			preparedStatement.setString(12, beanCursoJsp.getCurriculoBase64());
			preparedStatement.setString(13, beanCursoJsp.getContentTypeCurriculo());
			if (isMiniaturaFoto) { 
				preparedStatement.setString(14, beanCursoJsp.getMiniaturaFoto());
			}
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
