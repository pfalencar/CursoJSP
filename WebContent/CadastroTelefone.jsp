<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Cadastro de Telefone</title>
<link rel="stylesheet" href="resources/css/cadastro.css">

</head>

<body>
  <a href="AcessoLiberado.jsp">In�cio</a>
  <a href="index.jsp">Sair</a>
  <h1>Cadastro de Telefones</h1>

  <h3 class="msg">${msg}</h3>

  <form action="salvarTelefone" method="post" id="formFone"
    onsubmit="return validarCampos() ? true : false">
    <ul class="form-style-1">
      <table>
        
        <tr>
          <td>Id Usu�rio:</td>
          <td><input type="text" readonly="readonly" id="usuarioId"
            name="usuarioId" value="${userEscolhido.id}" class="field-long"></td>
            
          <td>Usu�rio:</td>
          <td><input type="text" readonly="readonly" id="nome"
            name="nome" value="${userEscolhido.nome}" class="field-long"></td>
        </tr>
        
        <tr>
          <td>C�digo:</td>
          <td><input type="text" readonly="readonly" id="id"
            name="id" value="${fone.id}" class="field-long"></td>
            
          <td>N�mero:</td>
          <td><input type="text" id="numero" name="numero"
            value="${fone.numero}"></td>
            
          <td>Tipo:</td>
          <td><select id="tipo" name="tipo">
            <option>Celular</option>
            <option>Casa</option>
            <option>Trabalho</option>
            <option>Recado</option>
            <option>Outros</option>
          </select></td>
        </tr>

    
        <tr>
          <td></td>
          <td><input type="submit" value="Salvar" /> 
          </td>
        </tr>

      </table>

    </ul>
  </form>
  <table id="customers">
    <caption>
      <h3>Telefones Cadastrados</h3>
    </caption>
    <tr>
      <th>Id Usu�rio</th>
      <th>Usu�rio</th>
      <th>Id Telefone</th>
      <th>N�mero</th>
      <th>Tipo</th>
      <th>Excluir</th>
    </tr>
    
    <!-- "${telefone}" � a lista que vou carregar do banco de dados -->
    <c:forEach items="${telefone}" var="fone">


      <tr>
        <!-- "user.id" s�o os nomes do objeto! -->
        <!-- "user" � a entidade, por isso ".estado", que � o atributo. -->
        <td><c:out value="${userEscolhido.id}"></c:out></td>
        <td><c:out value="${userEscolhido.nome}"></c:out></td>
        <td><c:out value="${fone.id}"></c:out></td>
        <td><c:out value="${fone.numero}"></c:out></td>
        <td><c:out value="${fone.tipo}"></c:out></td>

        <td><a href="salvarTelefone?acao=delete&foneId=${fone.id}"><img
            src="resources/img/lixeira.png" alt="Excluir"
            title="Excluir"></a>
        </td>
      </tr>
    </c:forEach>
  </table>
  <script type="text/javascript">
			function validarCampos() {
				if (document.getElementById("numero").value == '') {
					alert("N�mero n�o pode ser vazio.");
					return false;
				} else tion validarCampos() {
					if (document.getElementById("tipo").value == '') {
						alert("Tipo n�o pode ser vazio.");
						return false;
					}
				return true;
			}
  </script>
</body>
</html>