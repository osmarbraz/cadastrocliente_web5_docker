package controle.cliente;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

import util.Valida;
import util.RequestCliente;
import entidade.Cliente;
import java.util.logging.Level;

public class ClienteIncluir extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ClienteIncluir.class.getName());

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            response.setContentType("text/html");
            try ( PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html><html lang=\"pt-br\" xml:lang=\"pt-br\"><head><title>Cadastro de Cliente - Incluir</title></head><body>");
                out.println("<h1>Cadastro de Cliente - Incluir</h1>");

                Cliente cliente = RequestCliente.recuperaCliente(request);

                Valida valida = new Valida();
                boolean cpfValido = valida.validaCPF(cliente.getCpf());
                if (cpfValido) {
                    boolean resultado = cliente.inserir();
                    if (resultado) {
                        out.print("<span class='mensagemIncluir'>Inclus&atilde;o realizada com sucesso.</span><br>");
                    } else {
                        out.print("<span class='mensagemIncluir'>Inclus&atilde;o n&atilde;o realizada.</span><br>");
                    }
                } else {
                    out.print("<span class='mensagemIncluir'>CPF Inv&aacute;lido!</span><br>");
                }
                out.print("<br><a href=\"" + request.getContextPath() + "/FrmClienteIncluir.jsp\"> Incluir </a> - <a href=\"" + request.getContextPath() + "/menu.jsp\"> Menu </a> <br>");

                out.println("</body></html>");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Problema E/S {0}", e.toString());
        }
    }
}
