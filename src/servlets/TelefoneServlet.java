package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import beans.Telefone;
import dao.DaoTelefone;
import dao.DaoUsuario;

@WebServlet("/salvarTelefone")
public class TelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoTelefone daoTelefone = new DaoTelefone();
	private DaoUsuario daoUsuario = new DaoUsuario();

	public TelefoneServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String user = request.getParameter("user");
			String acao = request.getParameter("acao");
			String idTelefone = request.getParameter("fone");
			

			if (acao.equalsIgnoreCase("listarTodos")) {
				
				BeanCursoJsp usuario = daoUsuario.consultar(user);
				
				request.getSession().setAttribute("userEscolhido", usuario);
				request.setAttribute("userEscolhido", usuario);
				
				RequestDispatcher view = request.getRequestDispatcher("CadastroTelefone.jsp");
				request.setAttribute("telefone", daoTelefone.listar());
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("delete")) {
				daoTelefone.deletar(idTelefone);
				
//			} else {
//
//				RequestDispatcher view = request.getRequestDispatcher("CadastroTelefone.jsp");
//
//				// telefone é a lista que estou carregando do BD e vou colocar em "${telefone}"
//				// na página CadastroTelefone.jsp
//				request.setAttribute("telefone", daoTelefone.listar());
//				view.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		BeanCursoJsp beanCursoJsp = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");

		String numero = request.getParameter("numero");
		String tipo = request.getParameter("tipo");
		Long usuarioId = beanCursoJsp.getId();

		Telefone telefone = new Telefone();
		telefone.setNumero(numero);
		telefone.setTipo(tipo);
		telefone.setUsuario(usuarioId);

		daoTelefone.salvar(telefone);

		RequestDispatcher view = request.getRequestDispatcher("CadastroTelefone.jsp");

		// telefone é a lista que estou carregando do BD e vou colocar em "${telefone}"
		// na página CadastroTelefone.jsp
		request.setAttribute("telefone", daoTelefone.listar());
		request.setAttribute("msg", "Salvo com sucesso!");
		view.forward(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPut(request, response);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doDelete(request, response);
	}
}
