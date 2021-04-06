package servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeanCursoJsp;
import dao.DaoUsuario;

@WebServlet("/salvarUsuario")
@MultipartConfig
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
			String user = request.getParameter("user"); // O "user" recebe o id do usuário. Só para deletar e editar, pois têm
																									// id.

			if (acao.equalsIgnoreCase("delete")) {

				daoUsuario.deletar(user);
				request.setAttribute("msg", "Deletado com sucesso!");

				// depois que deletou eu carrego os usuários e volto para a mesma página
				RequestDispatcher view = request.getRequestDispatcher("CadastroUsuario.jsp");
				//"usuario" é a tabela da página, onde serão listados os usuários que estão no BD
				request.setAttribute("usuario", daoUsuario.listar());
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("editar")) {

				BeanCursoJsp beanCursoJsp = daoUsuario.consultar(user);

				RequestDispatcher view = request.getRequestDispatcher("CadastroUsuario.jsp");
				//"user" é 1 usuário que está nos campos da tela.
				request.setAttribute("user", beanCursoJsp);
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("listarTodos")) {

				RequestDispatcher view = request.getRequestDispatcher("CadastroUsuario.jsp");
				request.setAttribute("usuario", daoUsuario.listar());
				view.forward(request, response);
			
			} else if (acao.equalsIgnoreCase("download")) {
				BeanCursoJsp beanCursoJsp = daoUsuario.consultar(user);
				
				if (beanCursoJsp != null) {
					
					//usando regex para pegar a extensão que está depois da "/" no contentType. O split coloca em um array. Escolho a segunda posição: [1] para pegar o que vem depois da "/"
					String extensaoArquivo = beanCursoJsp.getContentType().split("\\/")[1];
					
					//vou fazer o movimento de download sem abrir uma nova tela.
					response.setHeader("Content-Disposition", "attachment;filename=arquivo." + extensaoArquivo);
					
					//tem que colocar a foto num array de bytes. Converte a base64 da imagem do banco para byte[]
					byte[] imageFotoBytes = new Base64().decodeBase64(beanCursoJsp.getFoto());
					
					//colocar os bytes em um objeto de entrada (um fluxo de entrada) para processar. O InputStream é usado para receber os bytes.
					InputStream inputStream = new ByteArrayInputStream(imageFotoBytes);
					
					//escrever na resposta
					/* INÍCIO DA RESPOSTA PARA O NAVEGADOR */
					int read = 0;
					byte[] bytes = new byte[1024];
					OutputStream outputStream = response.getOutputStream();
					
					//enquanto a minha variável de controle "read" estiver lendo os bytes, é porque têm dados nele, ou seja é != -1
					while ( (read = inputStream.read(bytes)) != -1 ) {
						outputStream.write(bytes, 0, read);
					}
					outputStream.flush();
					outputStream.close();
					
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String estado = request.getParameter("uf");
			String ibge = request.getParameter("ibge");

			BeanCursoJsp beanCursoJsp = new BeanCursoJsp();

			try {
				beanCursoJsp.setId(!id.isEmpty() && !id.isBlank() && id != null ? Long.parseLong(id) : null);
				beanCursoJsp.setLogin(!login.isEmpty() || !login.isBlank() ? login : null);
				beanCursoJsp.setSenha(!senha.isEmpty() || !senha.isBlank() ? senha : null);
				beanCursoJsp.setNome(!nome.isEmpty() || !nome.isBlank() ? nome : null);
				beanCursoJsp.setCep(!cep.isEmpty() || !cep.isBlank() ? cep : null);
				beanCursoJsp.setRua(!rua.isEmpty() || !rua.isBlank() ? rua : null);
				beanCursoJsp.setBairro(!bairro.isEmpty() || !bairro.isBlank() ? bairro : null);
				beanCursoJsp.setCidade(!cidade.isEmpty() || !cidade.isBlank() ? cidade : null);
				beanCursoJsp.setEstado(!estado.isEmpty() || !estado.isBlank() ? estado : null);
				beanCursoJsp.setIbge( !ibge.isEmpty() || !ibge.isBlank() ? ibge : null);
				
			} catch (Exception f) {
				f.printStackTrace();
			}

			try {
				
				/* INICIO - File upload de imagens e pdf */
				
				//converto uma inputStream para um array de bytes e depois converter ele para base64, passar para o objeto e continuar o fluxo para salvar.
				
				if (ServletFileUpload.isMultipartContent(request)) { //valida se esse é um formulário de Upload
					
					Part imagemFoto = request.getPart("foto");
					
					InputStream inputStreamFoto = imagemFoto.getInputStream();
					byte[] fotoEmByte = converterStreamToByte(inputStreamFoto);
					String fotoBase64 = new Base64().encodeBase64String(fotoEmByte);
					
					beanCursoJsp.setFoto(fotoBase64);
					beanCursoJsp.setContentType(imagemFoto.getContentType());
				}
				
				 /* FIM - File upload de imagens e pdf */

				if ( login == null || login.isEmpty() ) {
					request.setAttribute("msg", "Login é obrigatório!");
					request.setAttribute("user", beanCursoJsp);
					
				} else if ( senha == null || senha.isEmpty() ) {
					request.setAttribute("msg", "Senha é obrigatória!");
					request.setAttribute("user", beanCursoJsp);
					
				} else if ( nome == null || nome.isEmpty() ) {
					request.setAttribute("msg", "Nome é obrigatório!");
					request.setAttribute("user", beanCursoJsp);
					
				} else if ( cep == null || cep.isEmpty() ) {
					request.setAttribute("msg", "Cep é obrigatório!");
					request.setAttribute("user", beanCursoJsp);
										
				} else if (id == null || id.isEmpty()) {

					if (!daoUsuario.isLoginDuplicado(login)) {

						if (!daoUsuario.isSenhaDuplicada(senha)) {

							daoUsuario.salvar(beanCursoJsp);
							request.setAttribute("msg", "Salvo com sucesso!");

						} else {
							request.setAttribute("msg", "A senha já existe para outro usuário!");
							request.setAttribute("user", beanCursoJsp);
						}

					} else {

						request.setAttribute("msg", "O login já existe para outro usuário!");// "msg" é a variável jsp que está em
																																									// <h3> no CadastroUsuario.jsp
						request.setAttribute("user", beanCursoJsp);
					}

				} else if (id != null && !id.isEmpty()) {

					if (!daoUsuario.isLoginDuplicadoAtualizar(login, id)) {

						if (!daoUsuario.isSenhaDuplicadaAtualizar(senha, id)) {

							daoUsuario.atualizar(beanCursoJsp);
							request.setAttribute("msg", "Atualizado com sucesso!");

						} else {
							request.setAttribute("msg", "A senha já existe para outro usuário ao atualizar!");
							request.setAttribute("user", beanCursoJsp);
						}

					} else {
						request.setAttribute("msg", "Login já existe para outro usuário ao atualizar!");
						request.setAttribute("user", beanCursoJsp);
					}

				} 

			} catch (Exception e1) {
				e1.printStackTrace();
			}

			// para ficar na mesma página após cadastrar novo usuário
			try {
				RequestDispatcher view = request.getRequestDispatcher("CadastroUsuario.jsp");
				request.setAttribute("usuario", daoUsuario.listar()); // "usuario" é a variável jsp do foreach no
																															// CadastroUsuario.jsp: (<c:forEach items="${usuario}"
																															// var="user">)
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	// Converte a entrada de fluxo de dados da imagem para um array de bytes
	private static byte[] converterStreamToByte (InputStream imagem) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		int reads = imagem.read();
		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}		
		return baos.toByteArray();		
	}
	
}
