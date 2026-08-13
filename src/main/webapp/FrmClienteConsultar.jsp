<!DOCTYPE html>
<html lang="pt-br" xml:lang="pt-br">
    <head>
        <title>Cadastro de Cliente - Consultar</title>
    </head>
    <body>
        <h1>Cadastro de Cliente - Consultar</h1>
        <form name="FrmCliente" method="post" action="servlet/ClienteConsultar">
            ClienteId : <input type=text name="ClienteId"> <br><br>
            <input type="reset" value="Limpar">
            <input type="submit" name="Consultar" value="Consultar"><br>
        </form>
        <br>
        <a href="<%=request.getContextPath()%>/menu.jsp"> Menu </a>
    </body> 
</html>	