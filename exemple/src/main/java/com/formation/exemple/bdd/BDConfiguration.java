package com.formation.exemple.bdd;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.formation.exemple.model.InputProduct;

import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Configuration
public class BDConfiguration {

    @Autowired
    private DataSource dataSource;

    // @Autowired
    // private Environment env;

    // @Primary
    // @Bean
    // public DataSource batchDataSource() {
    // DriverManagerDataSource dataSource = new DriverManagerDataSource();
    // dataSource.setUrl(env.getProperty("spring.datasource.url"));
    // dataSource.setUsername(env.getProperty("spring.datasource.username"));
    // dataSource.setPassword(env.getProperty("spring.datasource.password"));
    // return dataSource;
    // }

    // @Bean
    // public DataSource inputProductDataSource() {
    // DriverManagerDataSource dataSource = new DriverManagerDataSource();
    // dataSource.setUrl(env.getProperty("appli.input"));
    // dataSource.setUsername(env.getProperty("spring.datasource.username"));
    // dataSource.setPassword(env.getProperty("spring.datasource.password"));
    // return dataSource;
    // }

    @Bean
    public JdbcPagingItemReader<InputProduct> bddProductReader() throws Exception {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("fournisseurId", 1);
        return new JdbcPagingItemReaderBuilder<InputProduct>().name("bddProductReader").dataSource(dataSource)
                .queryProvider(queryProvider()).parameterValues(parameterValues).rowMapper(inputProductMapper())
                .pageSize(1000).build();
    }

    @Bean
    public PagingQueryProvider queryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean provider = new SqlPagingQueryProviderFactoryBean();
        provider.setDataSource(dataSource);
        provider.setSelectClause("select id, hauteur, largeur, longueur, nom, reference");
        provider.setFromClause("from produit");
        provider.setWhereClause("where fournisseur_id=:fournisseurId");
        provider.setSortKey("id");
        return provider.getObject();
    }

    @Bean
    public org.springframework.jdbc.core.RowMapper<InputProduct> inputProductMapper() {
        BeanPropertyRowMapper<InputProduct> rowMapper = new BeanPropertyRowMapper<>();
        rowMapper.setMappedClass(InputProduct.class);
        return rowMapper;
    }

}
