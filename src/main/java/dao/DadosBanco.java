package dao;

/**
 * Classe que armazena os dados de configuração do banco de dados.
 *
 * @author osmarbraz
 */
public final class DadosBanco {
    
    /** 
     * Evita que a classe seja instânciada.
     */
    private DadosBanco() {
        
    }

    //Altere aqui os dados do seu banco de dados
    public static final String SERVIDOR = "mysql"; //caminho do MySQL no docker
    public static final String DATABASE = "db_cliente";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";    
    public static final String USUARIO = "root";
    public static final String SENHA = "root";
}
