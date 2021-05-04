<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Cadastro de Usuário</title>
<link rel="stylesheet" href="resources/css/cadastro.css">


<!-- Adicionando JQuery -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
  integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
  crossorigin="anonymous"></script>
</head>

<body>
  <a href="AcessoLiberado.jsp">Início</a>
  <a href="index.jsp">Sair</a>
  <h1>Cadastro de Usuário</h1>

  <h3 class="msg">${msg}</h3>

  <form action="salvarUsuario" method="post" id="formUser"
    onsubmit="return validarCampos() ? true : false;" enctype="multipart/form-data" >
    <ul class="form-style-1">
      <table>
        
        <tr>
          <td >Código:</td>
          <td><input type="text" readonly="readonly" id="id" name="id" value="${user.id}" style="width:178px;" class="field-long"></td>
          <td>Estado:</td>
          <td><input type="text" id="uf" name="uf"
            value="${user.estado}" maxlength="2"></td>
        </tr>

        <tr>
          <td>Login:</td>
          <td><input type="text" id="login" name="login" value="${user.login}" placeholder="Insira um login" maxlength="10"></td>
          <td>Rua:</td>
          <td><input type="text" id="rua" name="rua" value="${user.rua}" maxlength="50"></td>
        </tr>

        <tr>
          <td>Senha:</td>
          <td><input type="password" id="senha" name="senha"value="${user.senha}"  style="width:178px;" class="field-long" placeholder="Digite uma senha" maxlength="10"></td>
            <td>Bairro:</td>
          <td><input type="text" id="bairro" name="bairro" value="${user.bairro}" maxlength="50"></td>
        </tr>

        <tr>
          <td>Nome:</td>
          <td><input type="text" id="nome" name="nome" value="${user.nome}" placeholder="Digite o seu nome" maxlength="50"></td>
            <td>Cidade:</td>
          <td><input type="text" id="cidade" name="cidade" value="${user.cidade}" maxlength="50"></td>
        </tr>

        <tr>           
          <td>Cep:</td>
          <td><input type="text" id="cep" name="cep"
            value="${user.cep}" onblur="consultarCep()" placeholder="Digite um cep válido" maxlength="9"></td> 
          <td>IBGE:</td>
          <td><input type="text" id="ibge" name="ibge" value="${user.ibge}" maxlength="20"></td>
        </tr>
        
        <tr>
          <td>Foto: </td>
          <td>
            <input type="file" name="foto" />
              <input type="text" class="hide" name="fotoTemp" readonly="readonly" value="${user.foto}"/>
              <input type="text" class="hide" name="contentTypeFotoTemp" readonly="readonly" value="${user.contentType}"/>
          </td>
        </tr>
        
        <tr>
          <td>Currículo: </td>
          <td>
            <input type="file" name="curriculo"/>
              <input type="text" class="hide" name="curriculoTemp" readonly="readonly" value="${user.curriculoBase64}"/> 
              <input type="text" class="hide" name="curriculoContentType" readonly="readonly" value="${user.contentTypeCurriculo}"/> 
          </td>
        </tr>
        
        <tr  class="hide" >
          <td>Foto miniatura: </td>
          <td>
            <input type="file" name="miniaturaFoto"/>
              <input type="text"  name="fotoMiniaturaTemp" readonly="readonly" value="${user.miniaturaFoto}"/> 
          </td>
        </tr>
        
        <tr>
          <td></td>
          <td><input type="submit" value="Salvar"  style="width:178px;"/></td>
          <td></td>
          <td><input type="submit" value="Cancelar" style="width:178px;" onclick="document.getElementById('formUser').action='salvarUsuario?acao=reset'"/></td>
        </tr>

      </table>

    </ul>
  </form>
  
  <table id="customers">
    <caption><h3>Usuários Cadastrados</h3></caption>
    <tr>
      <th>Id</th>
      <th>Login</th>
      <th>Foto</th>
      <th>Currículo</th>
      <th>Nome</th>
      <th>Cep</th>
      <th>Rua</th>
      <th>Bairro</th>
      <th>Cidade</th>
      <th>Estado</th>
      <th>IBGE</th>
      <th>Fones</th>
      <th>Editar</th>
      <th>Deletar</th>
    </tr>
    
    <c:forEach items="${usuario}" var="user">    
      <tr>
      
        <!-- "user.id" são os nomes do objeto! -->
        <td><c:out value="${user.id}"></c:out></td>        
        <td><c:out value="${user.login}"></c:out></td>
        
        
        <c:if test="${user.miniaturaFoto == null}">
          <td>
            <img src="resources/img/user_icon_empty.png" title="Sem Imagem" width="32px" height="32px" onclick="alert('Não possui imagem!')"/>
          </td>
        </c:if>
        
        <c:if test="${user.miniaturaFoto.isEmpty() == false}">
          <td>
            <a href="salvarUsuario?acao=download&tipo=imagem&user=${user.id}">
              <img src='<c:out value="${user.miniaturaFoto}" />' alt="Imagem User" title="Imagem User" name="minifoto" width="32px" height="32px"/>
            </a>
          </td>
        </c:if>
        
        
        <c:if test="${user.curriculoBase64.isEmpty() == false}">        
          <td>
            <a href="salvarUsuario?acao=download&tipo=curriculo&user=${user.id}">
              <img src="resources/img/pdf_icon.PNG" alt="Curriculo" title="Curriculo" width="32px" height="32px" />
            </a>
          </td>
        </c:if>
        
        <c:if test="${user.curriculoBase64.isEmpty() == true}">
          <td>
            <img src="resources/img/file_blank.png" title="Sem Curriculo" width="32px" height="32px" onclick="alert('Não possui currículo!')"/>
          </td>
        </c:if>
        
        <td><c:out value="${user.nome}"></c:out></td>
        <td><c:out value="${user.cep}"></c:out></td>
        <td><c:out value="${user.rua}"></c:out></td>
        <td><c:out value="${user.bairro}"></c:out></td>
        <td><c:out value="${user.cidade}"></c:out></td>
        <td><c:out value="${user.estado}"></c:out></td> <!-- "user" é a entidade, por isso ".estado", que é o atributo. -->
        <td><c:out value="${user.ibge}"></c:out></td>

        <td>
            <a href="salvarTelefone?acao=listarTelefones&user=${user.id}">
               <img src="resources/img/phone20px.png" alt="Telefones" title="Telefones" />
            </a>
        </td>
        <td>
            <a href="salvarUsuario?acao=editar&user=${user.id}">
              <img src="resources/img/editar.png" alt="Editar" title="Editar" />
            </a>
        </td>
        <td>
            <a href="salvarUsuario?acao=delete&user=${user.id}">
              <img src="resources/img/lixeira.png" alt="Excluir" title="Excluir">
            </a>
        </td>
        
        
        
      </tr>      
    </c:forEach>
    
  </table>
  
  <script type="text/javascript">
			
			function validarCampos() {
				if (document.getElementById("login").value == '') {
					alert("Login não pode ser vazio.");
					return false;
				} else if (document.getElementById("senha").value == '') {
					alert("Senha não pode ser vazia.");
					return false;
				} else if (document.getElementById("nome").value == '') {
					alert("Nome não pode ser vazio.");
					return false;
				}
				return true;
			}
			
			
			function consultarCep() {
				var cep = $("#cep").val(); //"#" é o id do cep	

				//Consulta o webservice viacep.com.br/
				$.getJSON("https://viacep.com.br/ws/" + cep
						+ "/json/?callback=?", function(dados) {

					if (!("erro" in dados)) {
						//Atualiza os campos com os valores da consulta.
						$("#rua").val(dados.logradouro);
						$("#bairro").val(dados.bairro);
						$("#cidade").val(dados.localidade);
						$("#uf").val(dados.uf);
						$("#ibge").val(dados.ibge);

					} else {
						$("#cep").val('');
						$("#rua").val('');
						$("#bairro").val('');
						$("#cidade").val('');
						$("#uf").val('');
						$("#ibge").val('');
						alert("CEP não encontrado.");
					}
				});

			}
		</script>
</body>
</html>