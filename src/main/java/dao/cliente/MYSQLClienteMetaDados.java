package dao.cliente;

/**
 * Armazena os metadados para a implementação em SQLite.
 *
 * @author osmarbraz
 */
public final class MYSQLClienteMetaDados {
    
    /** 
     * Evita que a classe seja instânciada.
     */
    private MYSQLClienteMetaDados() {
        
    }

    /**
     * string com o nome da tabela usada no banco
     */
    public static final String TABLE = "CLIENTE";

    /**
     * vetor de string com as chaves da tabela
     */
    protected static final String[] PK = {"CLIENTEID"};

    /**
     * string com os campos para serem utilizados com insert
     */
    public static final String METADADOSINSERT = "CLIENTEID, NOME, CPF ";

    /**
     * Retorna uma string com os campos para serem utilizados com select
     */
    public static final String METADADOSSELECT
            = TABLE + ".CLIENTEID, "
            + TABLE + ".NOME, "
            + TABLE + ".CPF";
}
