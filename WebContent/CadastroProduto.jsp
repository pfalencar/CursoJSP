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
    <h1>Cadastro de Produto</h1>
    
    <h3 class="msg">${msg}</h3>
    
    <form action="salvarProduto" method="post" id="formProd">    
      <ul class="form-style-1">
      
        <table>
        
          <tr>
            <td>Código:</td>
            <td><input type="text" readonly="readonly" id="id" name="id" value="${prod.id}" class="field-long"></td>
          </tr>
          
          <tr>
            <td>Nome:</td>
            <td><input type="text" id="nome" name="nome" value="${prod.nome}"></td>
          </tr>
          
          <tr>
            <td>Quantidade:</td>
            <td><input type="text" id="quantidade" name="quantidade" value="${prod.quantidade}"></td>
          </tr>
          
          <tr>
            <td>Valor R$:</td>
            <td><input type="text" id="valor" name="valor" value="${prod.valor}"></td>
          </tr>
          
          <tr>
            <td></td>
            <td>
              <input type="submit" value="Salvar"/>
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
    
  </body>
</html>