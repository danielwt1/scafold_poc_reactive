package co.com.bancolombia.mysqlr2dbc.config;


import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.MySqlDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.ReactiveTransactionManager;


@Configuration
@EnableR2dbcRepositories(basePackages = "co.com.bancolombia.mysqlr2dbc.repository",entityOperationsRef = "r2dbcEntityOperations")
public class MySQLConnectionPool {
	private final MySqlConnectionProperties pgProperties;

	public MySQLConnectionPool(MySqlConnectionProperties pgProperties) {
		this.pgProperties = pgProperties;
	}
	@Primary
	@Bean(name = "mysqlConnectionFactory")
	//@Qualifier("mysqlConnectionFactory")
	public ConnectionFactory connectionFactory() {
		return ConnectionFactories.get(ConnectionFactoryOptions.builder()
				.option(ConnectionFactoryOptions.DRIVER, "mysql")
				.option(ConnectionFactoryOptions.HOST, pgProperties.getHost())
				.option(ConnectionFactoryOptions.PORT, pgProperties.getPort())
				.option(ConnectionFactoryOptions.USER, pgProperties.getUsername())
				.option(ConnectionFactoryOptions.PASSWORD, pgProperties.getPassword())
				.option(ConnectionFactoryOptions.DATABASE, pgProperties.getDatabase())
				.build());

	}
	@Primary
	@Bean
	public R2dbcEntityOperations r2dbcEntityOperations(@Qualifier("mysqlConnectionFactory") ConnectionFactory connectionFactory) {
		DefaultReactiveDataAccessStrategy strategy = new DefaultReactiveDataAccessStrategy(MySqlDialect.INSTANCE);
		DatabaseClient databaseClient = DatabaseClient.builder()
				.connectionFactory(connectionFactory)
				.build();
		return new R2dbcEntityTemplate(databaseClient, strategy);
	}
	@Primary
	@Bean
	public ReactiveTransactionManager transactionManager(@Qualifier("mysqlConnectionFactory") ConnectionFactory connectionFactory) {
		return new R2dbcTransactionManager(connectionFactory);
	}

}
