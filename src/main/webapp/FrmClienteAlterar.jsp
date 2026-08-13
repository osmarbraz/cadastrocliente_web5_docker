<!DOCTYPE html>
<html lang="pt-br" xml:lang="pt-br">
    <head>
        <title>Cadastro de Cliente - Alterar</title>
    </head>
    <body>
        <h1>Cadastro de Cliente - Alterar</h1>
        <form name="FrmCliente" method="post" action="servlet/ClienteAlterar">
            ClienteId: <input type=text name="ClienteId"> <br><br>
            Nome: <input type=text size="100" name="Nome"> <br><br>
            CPF (Somente números): <input type=text name="CPF"> <br><br>
            <input type="reset" value="Limpar">
            <input type="submit" name="Alterar" value="Alterar"> <br>
        </form>
        <br>
        <a href="<%=request.getContextPath()%>/menu.jsp"> Menu </a>
    </body>
</html>	