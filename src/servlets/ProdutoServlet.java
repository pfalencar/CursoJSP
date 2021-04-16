package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Produto;
import dao.DaoProduto;

@WebServlet("/salvarProduto")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoProduto daoProduto = new DaoProduto();

	// doGet: usado para LISTAR, EDITAR, DELETAR, CONSULTAR
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String acao = request.getParameter("acao");
			String idProduto = request.getParameter("prod");

			if (acao != null && acao.equalsIgnoreCase("listarTodos")) {
				RequestDispatcher view = request.getRequestDispatcher("CadastroProduto.jsp");
				request.setAttribute("produto", daoProduto.listar());
				view.forward(request, response);

			} else if (acao != null && acao.equalsIgnoreCase("delete")) {
				daoProduto.deletar(idProduto);
				request.setAttribute("msg", "Deletado com sucesso!");

				RequestDispatcher view = request.getRequestDispatcher("CadastroProduto.jsp");
				request.setAttribute("produto", daoProduto.listar());
				view.forward(request, response);

			} else if (acao != null && acao.equalsIgnoreCase("editar")) {

				Produto produto = daoProduto.consultar(idProduto);

				RequestDispatcher view = request.getRequestDispatcher("CadastroProduto.jsp");
				request.setAttribute("prod", produto);
				view.forward(request, response);

			} else {

				RequestDispatcher view = request.getRequestDispatcher("CadastroProduto.jsp");
				request.setAttribute("produto", daoProduto.listar());
				view.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// doPost: usado para RESET, SALVAR, ATUALIZAR
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {

			try {
				RequestDispatcher view = request.getRequestDispatcher("CadastroProduto.jsp");
				request.setAttribute("produto", daoProduto.listar());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (acao != null && acao.equalsIgnoreCase("salvar")) {

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String quantidade = request.getParameter("quantidade");
			String valor = request.getParameter("valor");

			Produto produto = new Produto();

			produto.setId((id == null || (id != null && id.isEmpty())) ? null : Long.valueOf(id));
			produto.setNome((nome == null || (nome != null && nome.isEmpty())) ? null : nome);
			produto.setQuantidade(
					quantidade == null || (quantidade != null && quantidade.isEmpty()) ? 0 : Integer.parseInt(quantidade));

			double valorDouble = Double.parseDouble(virgulaParaPonto(valor));
			produto.setValor((valor == null || (valor != null && valor.isEmpty())) ? 0.00 : valorDouble);

			try {
				if (nome == null || (nome != null && nome.isEmpty())) {
					request.setAttribute("msg", "Nome é obrigatório!");
					request.setAttribute("prod", produto);

				} else if (quantidade == null || quantidade.isEmpty() || produto.getQuantidade() == 0.00) {
					request.setAttribute("msg", "Quantidade é obrigatória!");
					request.setAttribute("prod", produto);

				} else if (valor == null || valor.isBlank() || produto.getValor() == 0.00) {
					request.setAttribute("msg", "Valor é obrigatório!");
					request.setAttribute("prod", produto);

				} else if (id == null || id.isEmpty()) {
					if (!daoProduto.isNomeProdutoDuplicado(nome)) {
						daoProduto.salvar(produto);
						request.setAttribute("msg", "Salvo com sucesso!");
					} else {
						request.setAttribute("msg", "Já existe um produto cadastrado com este nome.");
						request.setAttribute("prod", produto);
					}

				} else if (id != null || !id.isEmpty()) {
					if (!daoProduto.isNomeProdutoDuplicadoAtualizar(nome, id)) {
						daoProduto.atualizar(produto);
						request.setAttribute("msg", "Atualizado com sucesso!");
					} else {
						request.setAttribute("msg", "Já existe um produto cadastrado com este nome ao atualizar!");
						request.setAttribute("prod", produto);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("CadastroProduto.jsp");
				request.setAttribute("produto", daoProduto.listar());
				requestDispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // acao=salvar
		else {
			try {
				RequestDispatcher view = request.getRequestDispatcher("CadastroProduto.jsp");
				request.setAttribute("produto", daoProduto.listar());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private static String virgulaParaPonto(String valor) {
		String valor2 = valor.replace(".", "");// 3.953,37
		String valor3 = valor2.replace(",", ".");// 3953,37
		return valor3;
	}

}
