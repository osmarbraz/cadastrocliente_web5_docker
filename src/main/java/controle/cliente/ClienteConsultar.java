package controle.cliente;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.owasp.encoder.Encode;
import java.util.logging.Level;
import java.util.logging.Logger;

import entidade.Cliente;

public class ClienteConsultar extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ClienteConsultar.class.getName());
    
    private static final String BREAKROW = "<br>"; 

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            response.setContentType("text/html");
            try ( PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html><html lang=\"pt-br\" xml:lang=\"pt-br\"><head><title>Cadastro de Cliente - Consultar</title></head><body>");
                out.println("<h1>Cadastro de Cliente - Consultar</h1>");

                Cliente cliente = new Cliente();
                String encodeClienteId = Encode.forHtml(request.getParameter("ClienteId"));
                cliente.setClienteId(encodeClienteId);
                boolean resultado = cliente.abrir();
                if (resultado) {
                    out.print("<span class='mensagemConsultar'>Cliente encontrado.</span><br><br>");
                    out.print("ClienteId : " + cliente.getClienteId() + BREAKROW);
                    out.print("Nome : " + cliente.getNome() + BREAKROW);
                    out.print("CPF : " + cliente.getCpf() + BREAKROW);
                } else {
                    out.print("<span class='mensagemConsultar'>Cliente n&atilde;o encontrado.</span><br>");
                }
                out.print("<br><a href =\"" + request.getContextPath() + "/FrmClienteConsultar.jsp\"> Consultar </a> - <a href=\"" + request.getContextPath() + "/menu.jsp\"> Menu </a><br>");

                out.println("</body></html>");
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Problema E/S {0}", e.toString());
        }
    }
}
