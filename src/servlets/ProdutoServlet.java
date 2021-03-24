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

			if (acao.equalsIgnoreCase("listarTodos")) {

				RequestDispatcher view = request.getRequestDispatcher("CadastroProduto.jsp");
				request.setAttribute("produto", daoProduto.listar());
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("delete")) {

				daoProduto.deletar(idProduto);

				RequestDispatcher view = request.getRequestDispatcher("CadastroProduto.jsp");
				request.setAttribute("produto", daoProduto.listar());
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("editar")) {

				Produto produto = daoProduto.consultar(idProduto);

				RequestDispatcher view = request.getRequestDispatcher("CadastroProduto.jsp");
				request.setAttribute("prod", produto);
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

		try {

			if (acao.equalsIgnoreCase("reset")) {

				RequestDispatcher view = request.getRequestDispatcher("CadastroProduto.jsp");
				request.setAttribute("produto", daoProduto.listar());
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("salvar")) {

				String id = request.getParameter("id");
				String nome = request.getParameter("nome");
				String quantidade = request.getParameter("quantidade");
				String valor = request.getParameter("valor");

				Produto produto = new Produto();

				produto.setId(id != null && !id.isEmpty() ? Long.valueOf(id) : null);
				produto.setNome(nome != null && !nome.isEmpty() ? nome : null);
				produto.setQuantidade(quantidade != null && !quantidade.isEmpty() ? Double.parseDouble(quantidade) : null);
				produto.setValor(valor != null && !valor.isEmpty() ? Double.parseDouble(valor) : null);
				
				try {
					
					if ( id == null || id.isEmpty() ) {
						daoProduto.salvar(produto);
						
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("CadastroProduto.jsp");
						request.setAttribute("produto", daoProduto.listar());
						requestDispatcher.forward(request, response);
						
					} else if ( id != null || !id.isEmpty() ) {
						daoProduto.atualizar(produto);
						
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("CadastroProduto.jsp");
						request.setAttribute("produto", daoProduto.listar());
						requestDispatcher.forward(request, response);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
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