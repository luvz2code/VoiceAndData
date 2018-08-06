package com.example.easynotes;

import com.coxautodev.graphql.tools.SchemaParser;
import com.example.easynotes.graphql.Mutation;
import com.example.easynotes.graphql.Query;
import graphql.execution.ExecutionStrategy;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLServlet;
import graphql.servlet.SimpleGraphQLServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EasyNotesApplication {

	public static void main(String[] args) {

		SpringApplication.run(EasyNotesApplication.class, args);
	}

/*	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		GraphQLSchema schema  = SchemaParser.newParser().resolvers(new Query(),new Mutation()).file("schema.graphqls").build().makeExecutableSchema();
		ExecutionStrategy executionStrategy = new AsyncExecutionStrategy();
		GraphQLServlet servlet = new SimpleGraphQLServlet(schema, executionStrategy);
		ServletRegistrationBean bean = new ServletRegistrationBean(servlet, "/graphql");
		return bean;
	}*/
}
