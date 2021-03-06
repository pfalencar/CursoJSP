<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1">
    <title>Cadastro de Produto</title>
    <link rel="stylesheet" href="resources/css/produto.css">
  </head>
  
  <body>
    <a href="AcessoLiberado.jsp">In?cio</a>
    <a href="index.jsp">Sair</a>
    <h1>Cadastro de Produto</h1>
    
    <h3 class="msg">${msg}</h3>
    
    <form action="salvarProduto" method="post" id="formProd" onsubmit="return validarCampos() ? true : false">    
      <ul class="form-style-1">
      
        <table>
        
          <tr>
            <td>C?digo:</td>
            <td><input type="text" readonly="readonly" id="id" name="id" value="${prod.id}" class="field-long"></td>
          </tr>
          
          <tr>
            <td>Nome:</td>
            <td><input type="text" id="nome" name="nome" value="${prod.nome}" placeholder="Nome do produto"></td>
          </tr>
          
          <tr>
            <td>Quantidade:</td>
            <td><input type="text" id="quantidade" name="quantidade" value="${prod.quantidade}" placeholder="Quantidade em unidade"></td>
          </tr>
          
          <tr>
            <td>Valor R$:</td>
            <td><input type="text" id="valor" name="valor" value="${prod.valor}" placeholder="Valor unit?rio"></td>
          </tr>
          
          <tr>
            <td></td>
            <td>
              <input type="submit" value="Salvar"
              onclick="document.getElementById('formProd').action='salvarProduto?acao=salvar'" />
              <input type="submit" value="Cancelar" 
              onclick="document.getElementById('formProd').action='salvarProduto?acao=reset'" />
            </td>
          </tr>
          
        </table>
        
      </ul>    
    </form>
    
    <table id="customers">
      <caption><h3>Produtos Cadastrados</h3></caption>
      <tr>
        <th>Id</th>
        <th>Nome</th>
        <th>Quantidade</th>
        <th>Valor R$</th>
        <th>Deletar</th>
        <th>Editar</th>
      </tr>
    
      <c:forEach items="${produto}" var="prod">
        <tr>
          <td><c:out value="${prod.id}"></c:out></td>
          <td><c:out value="${prod.nome}"></c:out></td>
          <td><c:out value="${prod.quantidade}"></c:out></td>
          <td><c:out value="${prod.valor}"></c:out></td>
          
          <td>
            <a href="salvarProduto?acao=delete&prod=${prod.id}">
              <img src="resources/img/lixeira.png" title="Excluir"/>
            </a>
          </td>
          <td>
            <a href="salvarProduto?acao=editar&prod=${prod.id}">
              <img src="resources/img/editar.png" title="Editar"/>
            </a>
          </td>
        </tr>      
      </c:forEach>
    
    </table>
    <script type="text/javascript">
    	function validarCampos() {
    		if ( document.getElementById('nome').value == '' ) {
        		alert('Nome n?o pode ser vazio.');
        		return false;  
    		} else if ( document.getElementById('quantidade').value == '' ) {
    			alert('Quantidade n?o pode ser vazia.');
    			return false;
    		} else if ( document.getElementById('valor').value == '' ) {
    			alert('Valor n?o pode vazio.');
    			return false;
    		}
    		return true;
    	}
    
    </script>
  </body>
</html>