package dao.cliente;

import java.util.LinkedList;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.RAFDAOFactory;
import entidade.Cliente;

/**
 * Implementa a persitência para cliente utilizando Arquivo de Acesso
 * Aleatório(RandomAcessFile).
 *
 * @author osmarbraz
 */
public class RAFClienteDAO extends RAFDAOFactory implements ClienteDAO {

    private static final Logger LOGGER = Logger.getLogger(RAFClienteDAO.class.getName());

    private RandomAccessFile arquivo;

    private static final String NOMEARQUIVO = "cliente.dat";

    public RAFClienteDAO() {
        abrirArquivo(NOMEARQUIVO);
    }

    public boolean abrirArquivo(String nome) {
        try {
            File nomeArquivo = new File(nome);
            arquivo = new RandomAccessFile(nomeArquivo, "rw");
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Problema em abrir o arquivo!{0}", e.toString());
        }
        return false;
    }

    public void fecharArquivo() throws IOException {    
            arquivo.close();
    }

    @Override
    public boolean inserir(Object obj) {
        if (obj != null) {
            Cliente cliente = (Cliente) obj;
            if (procurarCodigo(cliente.getClienteId() + "") == -1) {
                try {
                    RAFRegistroCliente registro = new RAFRegistroCliente();
                    registro.setClienteId(Integer.parseInt(cliente.getClienteId()));
                    registro.setNome(cliente.getNome());
                    registro.setCpf(cliente.getCpf());
                    arquivo.seek(arquivo.length());
                    registro.escrita(arquivo);
                    return true;
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Problema em inserir o registro!{0}", e.toString());
                }
            }
        }
        return false;
    }

    @Override
    public List<Cliente> getLista() {
        List<Cliente> lista = new LinkedList<>();
        RAFRegistroCliente registro = new RAFRegistroCliente();
        try {
            arquivo.seek(0);

            while (arquivo.getFilePointer() < arquivo.length()) {
                registro.leitura(arquivo);
                Cliente cli = new Cliente();
                cli.setClienteId(registro.getClienteId());
                cli.setNome(registro.getNome());
                cli.setCpf(registro.getCpf());
                lista.add(cli);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Problema de io no arquivo em getLista:{0}", e.toString());
        }
        return lista;
    }

    @Override
    public List<Cliente> aplicarFiltro(Object obj) {
        if (obj != null) {
            Cliente cliente = (Cliente) obj;
            List<Cliente> lista = new LinkedList<>();
            //Filtro para clienteId            
            if (!"".equals(cliente.getClienteId())) {
                lista = aplicarFiltroId(cliente);
            }
            //Filtro para nome            
            if (!"".equals(cliente.getNome())) {
                lista = aplicarFiltroNome(cliente);
            }
            //Filtro para CPF
            if (!"".equals(cliente.getCpf())) {
                lista = aplicarFiltroCpf(cliente);
            }           
            return lista;
        } else {
            return Collections.emptyList();            
        }
    }

    public Cliente gerarRegistro(RAFRegistroCliente registro) {
        Cliente cliente = new Cliente();
        cliente.setClienteId(registro.getClienteId());
        cliente.setNome(registro.getNome());
        cliente.setCpf(registro.getCpf());
        return cliente;
    }

    public List<Cliente> aplicarFiltroId(Cliente cliente) {
        List<Cliente> lista = new LinkedList<>();
        //Filtro para clienteId
        try {
            arquivo.seek(0);
            RAFRegistroCliente registro = new RAFRegistroCliente();
            while (arquivo.getFilePointer() < arquivo.length()) { //Avança enquanto tiver objetos
                registro.leitura(arquivo);
                if (registro.getClienteId().equalsIgnoreCase(cliente.getClienteId())) {
                    lista.add(gerarRegistro(registro));
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Problema de io no arquivo em aplicar filtro no id:{0}", e.toString());
        }
        return lista;
    }

    public List<Cliente> aplicarFiltroNome(Cliente cliente) {
        List<Cliente> lista = new LinkedList<>();
        //Filtro para nome
        try {
            arquivo.seek(0);
            RAFRegistroCliente registro = new RAFRegistroCliente();
            while (arquivo.getFilePointer() < arquivo.length()) { //Avança enquanto tiver objetos
                registro.leitura(arquivo);
                if (registro.getNome().equalsIgnoreCase(cliente.getNome())) {
                    lista.add(gerarRegistro(registro));
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Problema de io no arquivo em aplicar filtro no nome:{0}", e.toString());
        }
        return lista;
    }

    public List<Cliente> aplicarFiltroCpf(Cliente cliente) {
        List<Cliente> lista = new LinkedList<>();
        //Filtro para Cpf
        try {
            arquivo.seek(0);
            RAFRegistroCliente registro = new RAFRegistroCliente();
            while (arquivo.getFilePointer() < arquivo.length()) { //Avança enquanto tiver objetos
                registro.leitura(arquivo);
                if (registro.getCpf().equalsIgnoreCase(cliente.getCpf())) {
                    lista.add(gerarRegistro(registro));
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Problema de io no arquivo em aplicar filtro no cpf:{0}", e.toString());
        }
        return lista;
    }

    @Override
    public int alterar(Object obj) {
        if (obj != null) {
            Cliente cliente = (Cliente) obj;
            String chave = cliente.getClienteId() + "";
            RAFRegistroCliente registro = new RAFRegistroCliente();

            int pos = procurarCodigo(chave);
            if (pos != -1) {
                return alterarRegistro(registro, cliente, pos);
            }
        }
        return 0;
    }

    public int alterarRegistro(RAFRegistroCliente registro, Cliente cliente, int pos) {
        try {
            arquivo.seek(pos * registro.getTamanho());
            registro.setClienteId(cliente.getClienteId());
            registro.setNome(cliente.getNome());
            registro.setCpf(cliente.getCpf());
            registro.escrita(arquivo);
            return 1;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Problema de io no arquivo em alterar:{0}", e.toString());
        }
        return 0;
    }

    private int procurarCodigo(String cod) {
        int pos = -1;
        int cont = 0;
        RAFRegistroCliente registro = new RAFRegistroCliente();
        try {
            arquivo.seek(0);
            while (arquivo.getFilePointer() < arquivo.length()) {
                registro.leitura(arquivo);
                if (registro.getClienteId().equalsIgnoreCase(cod)) {
                    pos = cont;
                }
                cont = cont + 1;
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Problema de io no arquivo em procurar por c\u00f3digo:{0}", e.toString());
        }
        return pos;
    }

    @Override
    public int excluir(Object obj) {
        if (obj != null) {
            Cliente cliente = (Cliente) obj;
            String chave = cliente.getClienteId() + "";
            long pos = -1;
            RAFRegistroCliente registro = new RAFRegistroCliente();
            pos = procurarCodigo(chave);
            if (pos != -1) {
                return excluirRegistro(registro, pos);
            }
        }
        return 0;
    }

    public int excluirRegistro(RAFRegistroCliente registro, long pos) {
        try {
            arquivo.seek(pos * registro.getTamanho());
            registro.setClienteId(-1);
            registro.setNome("");
            registro.setCpf("");
            registro.escrita(arquivo);
            return 1;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Problema de io no arquivo em excluir:{0}", e.toString());
        }
        return 0;
    }
}
