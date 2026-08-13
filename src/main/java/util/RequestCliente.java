package util;

import javax.servlet.http.HttpServletRequest;
import org.owasp.encoder.Encode;

import entidade.Cliente;

/**
 * Classe que processa os dados de um request de cliente.
 *
 * @author osmar
 */
public class RequestCliente {

    private RequestCliente() {

    }

    /**
     * Retorna um objeto cliente preenchido dos parâmetros de uma requisição.
     *
     * @param request
     * @return Um cliente preenchido.
     */
    public static Cliente recuperaCliente(HttpServletRequest request) {
        Cliente cliente = new Cliente();
        String encodeClienteId = Encode.forHtml(request.getParameter("ClienteId"));
        cliente.setClienteId(encodeClienteId);
        String encodeNome = org.owasp.encoder.Encode.forHtml(request.getParameter("Nome"));
        cliente.setNome(encodeNome);
        String encodeCPF = org.owasp.encoder.Encode.forHtml(request.getParameter("CPF"));
        cliente.setCpf(encodeCPF);
        return cliente;
    }
}
