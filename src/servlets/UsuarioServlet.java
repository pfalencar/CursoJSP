package servlets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

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
	private String mensagem = "";

	public UsuarioServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String acao = request.getParameter("acao");
			String user = request.getParameter("user"); // O "user" recebe o id do usuário. Só para deletar e editar, pois têm
																									// id.

			if (acao != null && acao.equalsIgnoreCase("delete") && user != null) {
				daoUsuario.deletar(user);
				mensagem = "Deletado com sucesso!";
				retornarParaCadastroUsuario(mensagem, request, response);// depois que deletou eu carrego os usuários e volto
																																	// para a mesma página

			} else if (acao != null && acao.equalsIgnoreCase("editar")) {
				BeanCursoJsp beanCursoJsp = daoUsuario.consultar(user);
				setarCamposNoFormulario(beanCursoJsp, request, response);

			} else if (acao != null && acao.equalsIgnoreCase("listarTodos")) {
				retornarParaCadastroUsuario(request, response);

			} else if (acao != null && acao.equalsIgnoreCase("download")) {
				BeanCursoJsp beanCursoJsp = daoUsuario.consultar(user);

				if (beanCursoJsp != null) {
					String conteudoTipo = "";
					byte[] fileBytes = null;
//					InputStream arquivoEmInputStream = null;

					String tipo = request.getParameter("tipo");

					if (tipo.equalsIgnoreCase("imagem")) {
						conteudoTipo = beanCursoJsp.getContentType();
						fileBytes = new Base64().decodeBase64(beanCursoJsp.getFoto());

					} else if (tipo.equalsIgnoreCase("curriculo")) {
						conteudoTipo = beanCursoJsp.getContentTypeCurriculo();
						fileBytes = new Base64().decodeBase64(beanCursoJsp.getCurriculoBase64());
					}

					response.setHeader("Content-Disposition", "attachment;filename=arquivo." + conteudoTipo.split("\\/")[1]);

					// colocar os bytes em um objeto de entrada (um fluxo de entrada) p processar. O
					// InputStream é usado para receber os bytes.
					InputStream is = new ByteArrayInputStream(fileBytes);

					// escrever na resposta
					/* INÍCIO DA RESPOSTA PARA O NAVEGADOR */
					int read = 0;
					byte[] bytes = new byte[1024];
					OutputStream os = response.getOutputStream();

					// enquanto a minha variável de controle "read" estiver lendo os bytes, é porque
					// têm dados nele, ou seja é != -1
					while ((read = is.read(bytes)) != -1) {
						os.write(bytes, 0, read);
					}
					os.flush();
					os.close();
				}

			} else {
				retornarParaCadastroUsuario(request, response);
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
				retornarParaCadastroUsuario(request, response);
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
				beanCursoJsp.setId(id != null && !id.isEmpty() && !id.isBlank() ? Long.parseLong(id) : null);
				beanCursoJsp.setLogin(login != null && !login.isEmpty() && !login.isBlank() ? login : null);
				beanCursoJsp.setSenha(senha != null && !senha.isEmpty() && !senha.isBlank() ? senha : null);
				beanCursoJsp.setNome(nome != null && !nome.isEmpty() && !nome.isBlank() ? nome : null);
				beanCursoJsp.setCep(cep != null && !cep.isEmpty() && !cep.isBlank() ? cep : null);
				beanCursoJsp.setRua(rua != null && !rua.isEmpty() && !rua.isBlank() ? rua : null);
				beanCursoJsp.setBairro(bairro != null && !bairro.isEmpty() && !bairro.isBlank() ? bairro : null);
				beanCursoJsp.setCidade(cidade != null && !cidade.isEmpty() && !cidade.isBlank() ? cidade : null);
				beanCursoJsp.setEstado(estado != null && !estado.isEmpty() && !estado.isBlank() ? estado : null);
				beanCursoJsp.setIbge(ibge != null && !ibge.isEmpty() && !ibge.isBlank() ? ibge : null);

			} catch (Exception f) {
				f.printStackTrace();
			}

			try {

				/* INICIO - File upload de imagens e pdf */

				// converto uma inputStream para um array de bytes e depois converter ele para
				// base64, passar para o objeto e continuar o fluxo para salvar.

				if (ServletFileUpload.isMultipartContent(request)) { // valida se esse é um formulário de Upload

					Part imagemFoto = request.getPart("foto");

					if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {

						InputStream inputStreamFoto = imagemFoto.getInputStream();
						byte[] fotoEmByte = converterStreamToByte(inputStreamFoto);
						String fotoBase64 = new Base64().encodeBase64String(fotoEmByte);

						beanCursoJsp.setFoto(fotoBase64);
						beanCursoJsp.setContentType(imagemFoto.getContentType());

						/* INÍCIO MINIATURA IMAGEM */

						/* Transforma em um bufferedImage */
						byte[] imageByteDecode = new Base64().decodeBase64(fotoBase64);
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));

						/* Pega o tipo da imagem */
						int typeImage = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

						/* Cria imagem em miniatura */
						BufferedImage resizedImage = new BufferedImage(100, 100, typeImage);
						Graphics2D g = resizedImage.createGraphics();
						g.drawImage(bufferedImage, 0, 0, 100, 100, null); /* Transforma a miniatura em uma imagem novamente */
						g.dispose();

						/* A imagem está em fluxo de dados, preciso escrever ela novamente: */
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(resizedImage, "png", baos);

						String miniaturaBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());

//						System.out.println("miniaturaBase64: " + miniaturaBase64);

						beanCursoJsp.setMiniaturaFoto(miniaturaBase64);

						/* FIM MINIATURA IMAGEM */

					} else {
						// se não tiver nada no campo de foto, pega o que tem nos parâmetros "fotoTemp"
						// e "contentTypeFotoTemp" e coloca no objeto
						beanCursoJsp.setFoto(request.getParameter("fotoTemp"));
						beanCursoJsp.setContentType(request.getParameter("contentTypeFotoTemp"));
					}

					/* Processa pdf */
					Part arquivoCurriculo = request.getPart("curriculo");

					if (arquivoCurriculo != null && arquivoCurriculo.getInputStream().available() > 0) {
						String curriculoBase64 = new Base64()
								.encodeBase64String(converterStreamToByte(arquivoCurriculo.getInputStream()));

						beanCursoJsp.setCurriculoBase64(curriculoBase64);
						beanCursoJsp.setContentTypeCurriculo(arquivoCurriculo.getContentType());

					} else {
						// se não tiver nada no campo de curriculo, pega o que tem nos parâmetros
						// "curriculoTemp" e "curriculoContentType" e coloca no objeto
						beanCursoJsp.setCurriculoBase64(request.getParameter("curriculoTemp"));
						beanCursoJsp.setContentTypeCurriculo(request.getParameter("curriculoContentType"));
					}
				}

				/* FIM - File upload de imagens e pdf */

				if (login == null || login.isEmpty()) {
					request.setAttribute("msg", "Login é obrigatório!");
					request.setAttribute("user", beanCursoJsp);

				} else if (senha == null || senha.isEmpty()) {
					request.setAttribute("msg", "Senha é obrigatória!");
					request.setAttribute("user", beanCursoJsp);

				} else if (nome == null || nome.isEmpty()) {
					request.setAttribute("msg", "Nome é obrigatório!");
					request.setAttribute("user", beanCursoJsp);

				} else if (cep == null || cep.isEmpty()) {
					request.setAttribute("msg", "Cep é obrigatório!");
					request.setAttribute("user", beanCursoJsp);

				} else if (id == null || id.isEmpty() && daoUsuario.isLoginDuplicado(login)) {
					request.setAttribute("msg", "O login já existe para outro usuário!");// "msg" é a variável jsp que está em
																																								// <h3> no CadastroUsuario.jsp
					request.setAttribute("user", beanCursoJsp);

				} else if (id == null || id.isEmpty() && daoUsuario.isSenhaDuplicada(senha)) {
					request.setAttribute("msg", "A senha já existe para outro usuário!");
					request.setAttribute("user", beanCursoJsp);

				} else if (id == null || id.isEmpty() && !daoUsuario.isLoginDuplicado(login) && !daoUsuario.isSenhaDuplicada(senha)) {
					daoUsuario.salvar(beanCursoJsp);
					request.setAttribute("msg", "Salvo com sucesso!");

				} else if (id != null && !id.isEmpty()) {
					
					if (daoUsuario.isLoginDuplicadoAtualizar(login, id)) {
						request.setAttribute("msg", "Login já existe para outro usuário ao atualizar!");
						request.setAttribute("user", beanCursoJsp);
						
					} else if (daoUsuario.isSenhaDuplicadaAtualizar(senha, id)) {
						//A senha já existe para outro usuário ao atualizar!
						request.setAttribute("msg", "Senha inválida! Digite outra senha para atualizar.");
						request.setAttribute("user", beanCursoJsp);
						
					} else {
						daoUsuario.atualizar(beanCursoJsp);
						request.setAttribute("msg", "Atualizado com sucesso!");
					}
				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}

			// para ficar na mesma página após cadastrar novo usuário
			try {
				retornarParaCadastroUsuario(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	private void setarCamposNoFormulario(BeanCursoJsp beanCursoJsp, HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {
		RequestDispatcher view = request.getRequestDispatcher("CadastroUsuario.jsp");
		// "user" é 1 usuário que está nos campos da tela.
		request.setAttribute("user", beanCursoJsp);
		view.forward(request, response);
	}

	private void retornarParaCadastroUsuario(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDispatcher view = request.getRequestDispatcher("CadastroUsuario.jsp");
		request.setAttribute("usuario", daoUsuario.listar());// "usuario" é a tabela da página, onde serão listados os
																													// usuários que estão no BD
		view.forward(request, response);
	}

	private void retornarParaCadastroUsuario(String mensagem, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RequestDispatcher view = request.getRequestDispatcher("CadastroUsuario.jsp");
		// "usuario" é a variável jsp do foreach no CadastroUsuario.jsp: (<c:forEach
		// items="${usuario}" var="user">)
		request.setAttribute("usuario", daoUsuario.listar());
		request.setAttribute("msg", mensagem);
		view.forward(request, response);
	}

	// Converte a entrada de fluxo de dados da imagem para um array de bytes
	private static byte[] converterStreamToByte(InputStream imagem) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();

		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}

		return baos.toByteArray();
	}

}
