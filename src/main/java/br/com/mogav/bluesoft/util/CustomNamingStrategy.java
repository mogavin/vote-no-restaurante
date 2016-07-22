package br.com.mogav.bluesoft.util;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Customiza a geração do sql de criação das tabelas (DDL). 
 * 
 * @author andre
 * @see <a href="http://stackoverflow.com/questions/32165694/spring-hibernate-5-naming-strategy-configuration#answer-32166571">hibernate 5 naming strategy configuration</a>
 */
public class CustomNamingStrategy extends PhysicalNamingStrategyStandardImpl {

	private static final long serialVersionUID = 2582476291722326422L;
	private String currentTableName;
   
	/**
	 * Nomeia a coluna 'id' das tabelas inserindo na frente o nome da tabela.
	 * Ex: A coluna 'id' da tabela 'pessoa' seria nomeada como 'pessoa_id'. 
	 */
    @Override
    public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnv) {
    	if (identifier.getText().equalsIgnoreCase("id")) {
            return Identifier.toIdentifier(currentTableName + "_id");
        }else {
            return identifier;
        }
    }

    @Override
    public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnv) {        
    	currentTableName = identifier.getText();    	
    	return identifier;
    }
}
