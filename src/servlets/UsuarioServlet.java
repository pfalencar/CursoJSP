package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String acao = request.getParameter("acao");
			String user = request.getParameter("user");

			if (acao.equalsIgnoreCase("delete")) {

				daoUsuario.delete(user);

				request.setAttribute("msg", "Deletado com sucesso!");
				
				// depois que deletou eu carrego os usuários e volto para a mesma página
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// request é a requisição, que é o envio do formulário
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

			BeanCursoJsp beanCursoJsp = new BeanCursoJsp();

			beanCursoJsp.setId(!id.isEmpty() ? Long.parseLong(id) : 0);
			beanCursoJsp.setLogin(!login.isEmpty() || !login.isBlank() ? login : null);
			beanCursoJsp.setSenha(!senha.isEmpty() || !senha.isBlank() ? senha : null);
			beanCursoJsp.setNome(!nome.isEmpty() || !nome.isBlank() ? nome : null);
			beanCursoJsp.setFone(!fone.isEmpty() || !fone.isBlank() ? fone : null);

			try {
				
				if ( id == null || id.isEmpty() ) {
					
					if ( !daoUsuario.isLoginDuplicado(login) ) {
						
						daoUsuario.salvar(beanCursoJsp);
						request.setAttribute("msg", "Salvo com sucesso!");
						
					} else {
						
						request.setAttribute("msg", "Este login já existe! Escolha outro login.");//"msg" é a variável jsp que está em <h3> no CadastroUsuario.jsp
					}
					
				} else if ( id != null && !id.isEmpty() && daoUsuario.isLoginDuplicadoAtualizar(login, id)) {

					daoUsuario.atualizar(beanCursoJsp);
					
				} else {
					request.setAttribute("msg", "Login já existe para outro usuário ao atualizar!");
				}
				
//				if (id == null || id.isEmpty() && daoUsuario.isLoginDuplicado(login)) {
//					
//					request.setAttribute("msg", "Este login já existe! Escolha outro login."); //"msg" é a variável jsp que está em <h3> no CadastroUsuario.jsp
//					
//				} else if (id == null || id.isEmpty() && !daoUsuario.isLoginDuplicado(login)) {
//
//					daoUsuario.salvar(beanCursoJsp);
//					request.setAttribute("msg", "Salvo com sucesso!");
//
//				} else if (id != null && !id.isEmpty() && daoUsuario.isLoginDuplicadoAtualizar(login)) {
//
//					daoUsuario.atualizar(beanCursoJsp);
//					
//				} 

			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// para ficar na mesma página após cadastrar novo usuário
			try {
				RequestDispatcher view = request.getRequestDispatcher("CadastroUsuario.jsp");
				request.setAttribute("usuario", daoUsuario.listar()); //"usuario" é a variável jsp do foreach no CadastroUsuario.jsp: (<c:forEach items="${usuario}" var="user">)
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
