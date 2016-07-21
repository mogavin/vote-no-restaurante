package br.com.mogav.bluesoft.persistencia;

import java.util.HashMap;
import java.util.Map;


public final class PersistenceUnitForTest {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getValues(){
		
		Map addedOrOverridenProperties = new HashMap();
		addedOrOverridenProperties.put("javax.persistence.jdbc.user", "sa");
		addedOrOverridenProperties.put("javax.persistence.jdbc.password", "");
		addedOrOverridenProperties.put("javax.persistence.jdbc.driver", "org.hsqldb.jdbcDriver");
		addedOrOverridenProperties.put("javax.persistence.jdbc.url", "jdbc:hsqldb:mem:.");
		addedOrOverridenProperties.put("hibernate.hbm2ddl.auto", "create-drop");
		addedOrOverridenProperties.put("hibernate.show_sql", true);
		addedOrOverridenProperties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		addedOrOverridenProperties.put("hibernate.connection.shutdown", true);
		addedOrOverridenProperties.put("hibernate.physical_naming_strategy", "br.com.mogav.bluesoft.util.CustomNamingStrategy");
		
		return addedOrOverridenProperties;
	}
}
