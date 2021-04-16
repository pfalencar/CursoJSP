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

	String mensagem = "";

	public TelefoneServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			String acao = request.getParameter("acao");
			String user = request.getParameter("user");
			
			if (acao != null && acao.equalsIgnoreCase("listarTelefones")) {

				BeanCursoJsp usuario = daoUsuario.consultar(user);
				// seta na sess�o no atributo "userEscolhido"(usado dentro do jsp, n�o no html)
				// todo o usu�rio
				request.getSession().setAttribute("userEscolhido", usuario);

				// seta na tela(CadastroTelefone.jsp) no parametro "userEscolhido" todo o objeto
				// usu�rio
				request.setAttribute("userEscolhido", usuario);

				retornarParaCadastroTelefone(usuario, request, response);

			} else if (acao != null && acao.equalsIgnoreCase("delete")) {
				String idTelefone = request.getParameter("foneId");
				daoTelefone.deletar(idTelefone);
				mensagem = "Exclu�do com sucesso!";
				// para pegar o que est� em jsp na tela (userEscolhido), uso o "pegar atributo"
				// (getAttribute)
				BeanCursoJsp beanCursoJsp = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");
				// ou passa o parametro "id do usuario" pela acao do botao excluir telefone.

				retornarParaCadastroTelefone(mensagem, beanCursoJsp, request, response);
			
			} else {				
				BeanCursoJsp beanCursoJsp = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");
				request.getSession().setAttribute("userEscolhido", beanCursoJsp);
				request.setAttribute("userEscolhido", beanCursoJsp);
				retornarParaCadastroTelefone(beanCursoJsp, request, response);
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

		String acao = request.getParameter("acao");

		if (acao == null || (acao != null && !acao.equalsIgnoreCase("voltar"))) {
			Telefone telefone = new Telefone();

			// Voc� est� inicializando a vari�vel "numero" para ser nula; quando voc� tenta
			// acess�-la, ocorre um erro de acesso de ponteiro nulo.
			// Voc� n�o pode chamar .isEmpty() (ou qualquer outro m�todo) em uma vari�vel
			// que � nula.
			if (numero == null || (numero != null && numero.isEmpty())) {
				mensagem = "Insira o n�mero de telefone!";
				retornarParaCadastroTelefone(mensagem, beanCursoJsp, request, response);

			} else {
				telefone.setNumero(numero);
				telefone.setTipo(tipo);
				telefone.setUsuario(usuarioId);
				daoTelefone.salvar(telefone);
				mensagem = "Salvo com sucesso!";

				request.getSession().setAttribute("userEscolhido", beanCursoJsp);
				request.setAttribute("userEscolhido", beanCursoJsp);
				retornarParaCadastroTelefone(mensagem, beanCursoJsp, request, response);
			}
		} else {
			try {
				RequestDispatcher view = request.getRequestDispatcher("CadastroUsuario.jsp");
				request.setAttribute("usuario", daoUsuario.listar());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void retornarParaCadastroTelefone(String mensagem, BeanCursoJsp beanCursoJsp, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("CadastroTelefone.jsp");
		// telefone � a lista que estou carregando do BD e vou colocar em "${telefone}"
		// na p�gina CadastroTelefone.jsp
		// pega a lista e seta no atributo jsp da tela CadastroTelefone.jsp
		request.setAttribute("telefone", daoTelefone.listar(beanCursoJsp.getId()));
		request.setAttribute("msg", mensagem);
		view.forward(request, response);
	}

	private void retornarParaCadastroTelefone(BeanCursoJsp beanCursoJsp, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher view = request.getRequestDispatcher("CadastroTelefone.jsp");
		request.setAttribute("telefone", daoTelefone.listar(beanCursoJsp.getId()));
		view.forward(request, response);
	}
}
