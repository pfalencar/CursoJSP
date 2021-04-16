<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1">
    <title>Cadastro de Produto</title>
    <link rel="stylesheet" href="resources/css/produto.css">
    <script src="resources/javascript/jquery.min.js" type="text/javascript"></script>
    <script src="resources/javascript/jquery.maskMoney.js" type="text/javascript"></script>
  </head>
  
  <body>
    <a href="AcessoLiberado.jsp">Início</a>
    <a href="index.jsp">Sair</a>
    <h1>Cadastro de Produto</h1>
    
    <h3 class="msg">${msg}</h3>
    
    <form action="salvarProduto" method="post" id="formProd" onsubmit="return validarCampos() ? true : false">    
      <ul class="form-style-1">
      
        <table>
        
          <tr>
            <td>Código:</td>
            <td><input type="text" readonly="readonly" id="id" name="id" value="${prod.id}" style="width:50px; class="field-long"></td>
          </tr>
          
          <tr>
            <td>Nome:</td>
            <td><input type="text" id="nome" name="nome" value="${prod.nome}" placeholder="Nome do produto" maxlength="50px" style="width:400px;"></td>
          </tr>
          
          <tr>
            <td>Quantidade Un.:</td>
            <td><input type="number" id="quantidade" name="quantidade" value="${prod.quantidade}" placeholder="Quantidade em unidade" maxlength="5"></td>
          </tr>
          
          <tr>
            <td>Valor R$:</td>
            <td><input type="text" id="valor" name="valor" data-thousands="." data-decimal=","
            value="${prod.valorEmTexto}" placeholder="Valor unitário" maxlength="12"></td>
            <!-- ou
              <td><input type="text" id="valor" name="valor" data-thousands="." data-decimal=","
            value="<fmt:formatNumber value="${prod.valor}"></fmt:formatNumber>" placeholder="Valor unitário" maxlength="12"></td>
             -->
          </tr>
          
          <tr>
            <td></td>
            <td>
              <input type="submit" value="Salvar" style="width:85px;"
              onclick="document.getElementById('formProd').action='salvarProduto?acao=salvar'" />
              <input type="submit" value="Cancelar" style="width:88px;"
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
        <th>Editar</th>
        <th>Deletar</th>
      </tr>
    
      <c:forEach items="${produto}" var="prod">
        <tr>
          <td><c:out value="${prod.id}"></c:out></td>
          <td><c:out value="${prod.nome}"></c:out></td>
          <td><c:out value="${prod.quantidade}"></c:out></td>
          <td>
            <fmt:formatNumber type="number" maxFractionDigits="2" value="${prod.valor}"></fmt:formatNumber>
          </td>
          
          <td>
            <a href="salvarProduto?acao=editar&prod=${prod.id}">
              <img src="resources/img/editar.png" title="Editar"/>
            </a>
          </td>
          <td>
            <a href="salvarProduto?acao=delete&prod=${prod.id}">
              <img src="resources/img/lixeira.png" title="Excluir"/>
            </a>
          </td>
        </tr>      
      </c:forEach>
    
    </table>
    <script type="text/javascript">
    	function validarCampos() {
    		if ( document.getElementById('nome').value == '' ) {
        		alert('Nome não pode ser vazio.');
        		return false;  
    		} else if ( document.getElementById('quantidade').value == '' ) {
    			alert('Quantidade não pode ser vazia.');
    			return false;
    		} else if ( document.getElementById('valor').value == '' ) {
    			alert('Valor não pode vazio.');
    			return false;
    		}
    		return true;
    	}
    
    </script>
  </body>
  <script>
    $(function() {
      $('#valor').maskMoney();
    })
</script>

</html>