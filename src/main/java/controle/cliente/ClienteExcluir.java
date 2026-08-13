package controle.cliente;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.encoder.Encode;

import entidade.Cliente;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteExcluir extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ClienteExcluir.class.getName());

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html");
            try ( PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html><html lang=\"pt-br\" xml:lang=\"pt-br\"><head><title>Cadastro de Cliente - Excluir</title></head><body>");
                out.println("<h1>Cadastro de Cliente - Excluir</h1>");

                Cliente cliente = new Cliente();
                String encodeClienteId = Encode.forHtml(request.getParameter("ClienteId"));
                cliente.setClienteId(encodeClienteId);
                int resultado = cliente.excluir();
                if (resultado != 0) {
                    out.print("<span class='mensagemExcluir'>Exclus&atilde;o realizada com sucesso.</span><br>");
                } else {
                    out.print("<span class='mensagemExcluir'>Exclus&atilde;o n&atilde;o realizada.</span><br>");
                }
                out.print("<br><a href=\"" + request.getContextPath() + "/FrmClienteExcluir.jsp\"> Excluir </a> - <a href=\"" + request.getContextPath() + "/menu.jsp\"> Menu </a><br>");

                out.println("</body></html>");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Problema E/S {0}", e.toString());
        }
    }
}
