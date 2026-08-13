package controle.cliente;

import java.util.logging.Logger;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Valida;
import util.RequestCliente;
import entidade.Cliente;
import java.util.logging.Level;

public class ClienteAlterar extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ClienteAlterar.class.getName());

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            response.setContentType("text/html");
            try ( PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html><html lang=\"pt-br\" xml:lang=\"pt-br\"><head><title>Cadastro de Cliente - Alterar</title></head><body>");
                out.println("<h1>Cadastro de Cliente - Alterar</h1>");

                Cliente cliente = RequestCliente.recuperaCliente(request);
                Valida valida = new Valida();
                boolean cpfValido = valida.validaCPF(cliente.getCpf());

                if (cpfValido) {
                    int resultado = cliente.alterar();
                    if (resultado != 0) {
                        out.print("<span class='mensagemAlterar'>Altera&ccedil;&atilde;o realizada com sucesso.</span><br>");
                    } else {
                        out.print("<span class='mensagemAlterar'>Altera&ccedil;&atilde;o n&atilde;o realizada.</span><br>");
                    }
                } else {                    
                    out.print("<span class='mensagemIncluir'>CPF Inv&aacute;lido!</span><br>");
                }
                out.print("<br><a href=\"" + request.getContextPath() + "/FrmClienteAlterar.jsp\"> Alterar </a> - <a href=\"" + request.getContextPath() + "/menu.jsp\"> Menu </a><br>");

                out.println("</body></html>");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Problema E/S {0}", e.toString());
        }
    }
}