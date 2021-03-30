package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import dao.DaoUsuario;

@WebServlet("/salvarUsuario")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoUsuario daoUsuario = new DaoUsuario();

	public UsuarioServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String acao = request.getParameter("acao");
			String user = request.getParameter("user"); // O "user" recebe o id do usu�rio. S� para deletar e editar, pois t�m
																									// id.

			if (acao.equalsIgnoreCase("delete")) {

				daoUsuario.deletar(user);

				request.setAttribute("msg", "Deletado com sucesso!");

				// depois que deletou eu carrego os usu�rios e volto para a mesma p�gina
				RequestDispatcher view = request.getRequestDispatcher("CadastroUsuario.jsp");

				request.setAttribute("usuario", daoUsuario.listar());
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("editar")) {

				BeanCursoJsp beanCursoJsp = daoUsuario.consultar(user);

				RequestDispatcher view = request.getRequestDispatcher("CadastroUsuario.jsp");
				request.setAttribute("user", beanCursoJsp);
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("listarTodos")) {

				RequestDispatcher view = request.getRequestDispatcher("CadastroUsuario.jsp");
				request.setAttribute("usuario", daoUsuario.listar());
				view.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// request � a requisi��o, que � o envio do formul�rio
		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher view = request.getRequestDispatcher("CadastroUsuario.jsp");
				request.setAttribute("usuario", daoUsuario.listar());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			String id = request.getParameter("id");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String nome = request.getParameter("nome");
			String fone = request.getParameter("fone");			
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String estado = request.getParameter("uf");
			String ibge = request.getParameter("ibge");

			BeanCursoJsp beanCursoJsp = new BeanCursoJsp();

			beanCursoJsp.setId(!id.isEmpty() ? Long.parseLong(id) : null);
			beanCursoJsp.setLogin(!login.isEmpty() || !login.isBlank() ? login : null);
			beanCursoJsp.setSenha(!senha.isEmpty() || !senha.isBlank() ? senha : null);
			beanCursoJsp.setNome(!nome.isEmpty() || !nome.isBlank() ? nome : null);
			beanCursoJsp.setFone(!fone.isEmpty() || !fone.isBlank() ? fone : null);
			beanCursoJsp.setCep(!cep.isEmpty() || !cep.isBlank() ? cep : null);
			beanCursoJsp.setRua(!rua.isEmpty() || !rua.isBlank() ? rua : null);
			beanCursoJsp.setBairro(!bairro.isEmpty() || !bairro.isBlank() ? bairro : null);
			beanCursoJsp.setCidade(!cidade.isEmpty() || !cidade.isBlank() ? cidade : null);
			beanCursoJsp.setEstado(!estado.isEmpty() || !estado.isBlank() ? estado : null);
			beanCursoJsp.setIbge( !ibge.isEmpty() || !ibge.isBlank() ? ibge : null);

			try {

				if ( login == null || login.isEmpty() ) {
					request.setAttribute("msg", "Login � obrigat�rio!");
					request.setAttribute("user", beanCursoJsp);
					
				} else if ( senha == null || senha.isEmpty() ) {
					request.setAttribute("msg", "Senha � obrigat�ria!");
					request.setAttribute("user", beanCursoJsp);
					
				} else if ( nome == null || nome.isEmpty() ) {
					request.setAttribute("msg", "Nome � obrigat�rio!");
					request.setAttribute("user", beanCursoJsp);
					
				} else if ( fone == null || fone.isEmpty() ) {
					request.setAttribute("msg", "Telefone � obrigat�rio!");
					request.setAttribute("user", beanCursoJsp);
					
				} else if ( cep == null || cep.isEmpty() ) {
					request.setAttribute("msg", "Cep � obrigat�rio!");
					request.setAttribute("user", beanCursoJsp);
										
				} else if (id == null || id.isEmpty()) {

					if (!daoUsuario.isLoginDuplicado(login)) {

						if (!daoUsuario.isSenhaDuplicada(senha)) {

							daoUsuario.salvar(beanCursoJsp);
							request.setAttribute("msg", "Salvo com sucesso!");

						} else {
							request.setAttribute("msg", "A senha j� existe para outro usu�rio!");
							request.setAttribute("user", beanCursoJsp);
						}

					} else {

						request.setAttribute("msg", "O login j� existe para outro usu�rio!");// "msg" � a vari�vel jsp que est� em
																																									// <h3> no CadastroUsuario.jsp
						request.setAttribute("user", beanCursoJsp);
					}

				} else if (id != null && !id.isEmpty()) {

					if (!daoUsuario.isLoginDuplicadoAtualizar(login, id)) {

						if (!daoUsuario.isSenhaDuplicadaAtualizar(senha, id)) {

							daoUsuario.atualizar(beanCursoJsp);
							request.setAttribute("msg", "Atualizado com sucesso!");

						} else {
							request.setAttribute("msg", "A senha j� existe para outro usu�rio ao atualizar!");
							request.setAttribute("user", beanCursoJsp);
						}

					} else {
						request.setAttribute("msg", "Login j� existe para outro usu�rio ao atualizar!");
						request.setAttribute("user", beanCursoJsp);
					}

				} 

			} catch (Exception e1) {
				e1.printStackTrace();
			}

			// para ficar na mesma p�gina ap�s cadastrar novo usu�rio
			try {
				RequestDispatcher view = request.getRequestDispatcher("CadastroUsuario.jsp");
				request.setAttribute("usuario", daoUsuario.listar()); // "usuario" � a vari�vel jsp do foreach no
																															// CadastroUsuario.jsp: (<c:forEach items="${usuario}"
																															// var="user">)
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPut(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doDelete(req, resp);
	}

}
