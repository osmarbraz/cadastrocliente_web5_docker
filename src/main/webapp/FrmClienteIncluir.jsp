<!DOCTYPE html>
<html lang="pt-br" xml:lang="pt-br">
    <head>
        <title>Cadastro de Cliente - Incluir</title>
    </head>
    <body>
        <h1>Cadastro de Cliente - Incluir</h1>
        <form name="FrmCliente" method="post" action="servlet/ClienteIncluir">	
            ClienteId: <input type=text name="ClienteId"> <br><br>
            Nome: <input type=text size="100" name="Nome"> <br><br>
            CPF (Somente números): <input type=text name="CPF"> <br><br>
            <input type="reset" value="Limpar">
            <input type="submit" name="Incluir" value="Incluir"> <br>            
        </form>
        <br>
        <a href="<%=request.getContextPath()%>/menu.jsp"> Menu </a>
    </body>
</html>	