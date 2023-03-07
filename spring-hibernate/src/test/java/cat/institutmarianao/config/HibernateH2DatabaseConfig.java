package cat.institutmarianao.config;

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
 * to hibernate (loaded thorught
 * {@link cat.institutmarianao.config.HibernateConfiguration}
 *
 * Used by DAO Tests
 *
 * @author Toni
 *
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = { "application-test.properties" })
@Import(value = { HibernateConfiguration.class })
public class HibernateH2DatabaseConfig {

	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).build();
		return db;
	}
}