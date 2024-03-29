package co.com.bancolombia.r2dbc.config;

import java.time.Duration;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.MySqlDialect;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.ReactiveTransactionManager;

@Configuration
@EnableR2dbcRepositories(
		basePackages = "co.com.bancolombia.r2dbc.repository",
		entityOperationsRef = "secondaryR2dbcEntityOperations"

)
public class PostgreSQLConnectionPool {

	private final PostgresqlConnectionProperties pgProperties;

	public PostgreSQLConnectionPool(PostgresqlConnectionProperties pgProperties) {
		this.pgProperties = pgProperties;
	}

	@Bean(name = "secondaryConnectionFactory")
	public ConnectionFactory secondaryConnectionFactory() {
		return ConnectionFactories.get(ConnectionFactoryOptions.builder()
				.option(ConnectionFactoryOptions.DRIVER, "postgresql")
				.option(ConnectionFactoryOptions.HOST, pgProperties.getHost())
				.option(ConnectionFactoryOptions.PORT, pgProperties.getPort())
				.option(ConnectionFactoryOptions.USER, pgProperties.getUsername())
				.option(ConnectionFactoryOptions.PASSWORD, pgProperties.getPassword())
				.option(ConnectionFactoryOptions.DATABASE, pgProperties.getDatabase())
				.build());
	}
	@Bean
	public R2dbcEntityOperations secondaryR2dbcEntityOperations(@Qualifier("secondaryConnectionFactory") ConnectionFactory connectionFactory) {
		DefaultReactiveDataAccessStrategy strategy = new DefaultReactiveDataAccessStrategy(PostgresDialect.INSTANCE);
		DatabaseClient databaseClient = DatabaseClient.builder()
				.connectionFactory(connectionFactory)
				.build();
		return new R2dbcEntityTemplate(databaseClient, strategy);
	}
	@Bean
	public ReactiveTransactionManager secondTransactionManager(@Qualifier("secondaryConnectionFactory") ConnectionFactory connectionFactory) {
		return new R2dbcTransactionManager(connectionFactory);
	}
}
