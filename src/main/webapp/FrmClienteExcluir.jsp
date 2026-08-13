<!DOCTYPE html>
<html lang="pt-br" xml:lang="pt-br">
    <head>
        <title>Cadastro de Cliente - Excluir</title>
    </head>
    <body>
        <h1>Cadastro de Cliente - Excluir</h1>
        <form name="FrmCliente" method="post" action="servlet/ClienteExcluir">
            ClienteId : <input type=text name="ClienteId"> <br><br>
            <input type="reset" value="Limpar">
            <input type="submit" name="Excluir" value="Excluir"> <br>
        </form>
        <br>
        <a href="<%=request.getContextPath()%>/menu.jsp"> Menu </a>
    </body>
</html>	