package dao.cliente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.logging.Logger;
import java.util.logging.Level;

import static dao.cliente.MYSQLClienteMetaDados.METADADOSINSERT;
import static dao.cliente.MYSQLClienteMetaDados.METADADOSSELECT;
import static dao.cliente.MYSQLClienteMetaDados.PK;
import static dao.cliente.MYSQLClienteMetaDados.TABLE;
import dao.MYSQLDAOFactory;
import dao.DadosBanco;
import entidade.Cliente;

/**
 * Implementa a persistência de cliente utilizando SQLite.
 *
 * @author osmarbraz
 */
public class MYSQLClienteDAO extends MYSQLDAOFactory implements ClienteDAO {

    private static final Logger LOGGER = Logger.getLogger(MYSQLClienteDAO.class.getName());
    private static final String WHERE = " where ";

    public MYSQLClienteDAO() {
        criar();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<Cliente> select(String sql) {
        List<Cliente> lista = new LinkedList<>();
        try {
            try ( Connection con = getConnection();  Statement stmt = con.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setClienteId(rs.getString("clienteid"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setCpf(rs.getString("cpf"));
                    lista.add(cliente);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro no select:{0}", e.toString());
        }
        return lista;
    }

    @Override
    public boolean inserir(Object obj) {
        boolean res = false;
        if (obj != null) {
            Cliente cliente = (Cliente) obj;
            StringBuilder sql = new StringBuilder();
            try {
                try ( Connection con = getConnection();  Statement stmt = con.createStatement()) {
                    sql.append("insert into " + TABLE + "(");
                    sql.append(METADADOSINSERT + " ) ");
                    sql.append("values ('").append(preparaSQL(cliente.getClienteId()));
                    sql.append("','").append(preparaSQL(cliente.getNome()));
                    sql.append("','").append(preparaSQL(cliente.getCpf())).append("')");
                    res = stmt.executeUpdate(sql.toString()) > 0;
                }
                return res;
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Erro no inserir:{0}", e.toString());
            }
        }
        return res;
    }

    @Override
    public int alterar(Object obj) {
        int res = 0;
        if (obj != null) {
            Cliente cliente = (Cliente) obj;
            StringBuilder sql = new StringBuilder();
            try {
                try ( Connection con = getConnection();  Statement stmt = con.createStatement()) {
                    sql.append("update " + TABLE);
                    sql.append(" set nome='").append(cliente.getNome()).append("',");
                    sql.append(" cpf='").append(cliente.getCpf()).append("'");
                    sql.append(WHERE + TABLE + ".").append(PK[0]).append("='").append(preparaSQL(cliente.getClienteId())).append("'");
                    res = stmt.executeUpdate(sql.toString());
                }
                return res;
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Erro no alterar:{0}", e.toString());
            }
        }
        return res;
    }

    @Override
    public int excluir(Object obj) {
        int res = 0;
        if (obj != null) {
            Cliente cliente = (Cliente) obj;
            StringBuilder sql = new StringBuilder();
            try {
                try ( Connection con = getConnection();  Statement stmt = con.createStatement()) {
                    sql.append("delete from " + TABLE + WHERE + TABLE + ".").append(PK[0]).append(" = '").append(preparaSQL(cliente.getClienteId())).append("'");
                    res = stmt.executeUpdate(sql.toString());
                }
                return res;
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Erro no excluir:{0}", e.toString());
            }
        }
        return res;
    }

    @Override
    public List<Cliente> getLista() {
        return select("select " + METADADOSSELECT + " from " + TABLE + " order by " + TABLE + "." + PK[0]);
    }

    @Override
    public List<Cliente> aplicarFiltro(Object obj) {
        if (obj != null) {
            Cliente cliente = (Cliente) obj;

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("select " + METADADOSSELECT + " from " + TABLE);

            List<String> filtros = new ArrayList<>();

            if (!"".equals(cliente.getClienteId())) {
                filtros.add(TABLE + "." + PK[0] + "='" + preparaSQL(cliente.getClienteId()) + "'");
            }

            if (!"".equals(cliente.getNome())) {
                filtros.add(TABLE + ".nome like upper('%" + preparaSQL(cliente.getNome()) + "%')");
            }

            if (!"".equals(cliente.getCpf())) {
                filtros.add(TABLE + ".cpf = '" + preparaSQL(cliente.getCpf()) + "'");
            }

            if (!filtros.isEmpty()) {
                sqlBuilder.append(WHERE).append(implode(" and ", filtros));
            }

            sqlBuilder.append(" order by " + TABLE + ".").append(PK[0]);

            return select(sqlBuilder.toString());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Sequência de criação do database e tabela.
     */
    private void criar() {
        criarBancoDeDados();
        criarTabelas();
    }

    /**
     * Cria o esquema(database) no banco de dados.
     */
    private void criarBancoDeDados() {
        try {
            try (
                     Connection con = getConnectionDB();  Statement stmt = con.createStatement()) {
                //Cria a tabela senão existir
                int retorno = stmt.executeUpdate("create database if not exists " + DadosBanco.DATABASE + ";");
                //System.out.println("Database criado:" + retorno);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro no criar:{0}", e.toString());
        }
    }

    /**
     * Cria as tabelas do banco de dados.
     */
    private void criarTabelas() {
        try {
            try (
                     Connection con = getConnection();  Statement stmt = con.createStatement()) {
                //Cria a tabela senão existir
                int retorno = stmt.executeUpdate("create table if not exists cliente (clienteid integer, nome varchar(100), cpf varchar(11), constraint pk_cliente primary key (clienteid));");
                //System.out.println("Tabela criada:" + retorno);

            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro no criar:{0}", e.toString());
        }
    }
}
