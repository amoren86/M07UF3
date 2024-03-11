package cat.institutmarianao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Provides the datasource configured in application-test.properties (a h2 one)
 * to hibernate (loaded thorught {@link cat.institutmarianao.HibernateConfig}
 *
 * Used by DAO Tests
 *
 * @author Toni
 *
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:/hibernate-h2-test.properties" })
@Import(value = { HibernateConfig.class })
public class DaoTestConfig {

	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).build();
		return db;
	}
}