<%@page import="java.util.List"%>
<%@page import="entidade.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br" xml:lang="pt-br">
    <head>
        <title>Cadastro de Cliente - Listar</title>
    </head>
    <body>
        <h1>Cadastro de Cliente - Listar</h1>
        <table border="1">    
            <caption>Lista de clientes</caption>
            <tr>
                <th scope="col">ClienteId</th><th scope="col">Nome</th><th scope="col">CPF</th>
            </tr>                  
            <% 	Cliente cliente = new Cliente();
                List<Cliente> clientes = cliente.getLista();
                for (Cliente aux : clientes) {%>                    			
            <tr>                   
                <td><%=aux.getClienteId()%></td><td><%=aux.getNome()%></td><td><%=aux.getCpf()%></td>                   
            </tr>  
            <% }%>
        </table>
        <br>
        <a href="<%=request.getContextPath()%>/menu.jsp"> Menu </a>
    </body>
</html>
