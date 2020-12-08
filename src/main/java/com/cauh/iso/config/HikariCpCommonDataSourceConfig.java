//package com.cauh.iso.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.env.Environment;
//import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import java.util.HashMap;
//
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        basePackages = {"com.cauh.common.repository", "com.cauh.iso.repository"},
//        entityManagerFactoryRef = "commonEntityManagerFactory",
//        transactionManagerRef = "commonTransactionManager",
//        repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class
//)
//@Slf4j
//@RequiredArgsConstructor
public class HikariCpCommonDataSourceConfig {
//    private final Environment env;
//
//    @Primary
//    @Bean(name = "commonDataSource")
//    @ConfigurationProperties(prefix = "common.datasource")
//    public DataSource commonDataSource(){
//        HikariDataSource hikariDataSource = new HikariDataSource();
//        return hikariDataSource;
//    }
//
//    @Primary
//    @Bean(name = "commonEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean commonEntityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        localContainerEntityManagerFactoryBean.setPersistenceUnitName("common");
//        localContainerEntityManagerFactoryBean.setDataSource(commonDataSource());
//        localContainerEntityManagerFactoryBean.setPackagesToScan(new String[]{"com.cauh.common.entity", "com.cauh.iso.domain"});
//
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
//
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", env.getProperty("common.datasource.hibernate.hbm2ddl.auto"));//none, create, update, validate?
//        properties.put("hibernate.show_sql", env.getProperty("common.datasource.hibernate.show-sql"));
//        properties.put("hibernate.format_sql", env.getProperty("common.datasource.hibernate.format_sql"));
////        properties.put("hibernate.physical_naming_strategy" , env.getProperty("common.datasource.hibernate.naming.physical-strategy"));
////        properties.put("hibernate.implicit_naming_strategy" , env.getProperty("common.datasource.hibernate.naming.implicit-strategy"));
//        properties.put("hibernate.jdbc.batch_size", 500);
//        properties.put("hibernate.enable_lazy_load_no_trans", true);
//        properties.put("hibernate.order_inserts", true);
//        properties.put("hibernate.order_updates", true);
////        properties.put("hibernate.default_schema", "dbo");
////        properties.put("hibernate.default_catalog", "common_test_20201006");
//
//        // Table & Column Naming 전략(카멜케이스 <-> 스네이크 표기법)
//        properties.put("hibernate.physical_naming_strategy" , "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
//        properties.put("hibernate.implicit_naming_strategy" , "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
//
//        //envers option
//        properties.put("org.hibernate.envers.audit_table_suffix", "_audit");
//        properties.put("org.hibernate.envers.revision_field_name", "revision_id");
//        properties.put("org.hibernate.envers.revision_type_field_name", "revision_type");
//        properties.put("org.hibernate.envers.modified_flag_suffix", "_changed");
//        properties.put("org.hibernate.envers.store_data_at_delete", true);  // 삭제시 마지막 데이터 남기기(기본 null)
//        localContainerEntityManagerFactoryBean.setJpaPropertyMap(properties);
//        return localContainerEntityManagerFactoryBean;
//    }
//
//    @Primary
//    @Bean(name = "commonTransactionManager")
//    public PlatformTransactionManager commonTransactionManager(@Qualifier("commonEntityManagerFactory") EntityManagerFactory commonEntityManagerFactory) {
//        return new JpaTransactionManager(commonEntityManagerFactory);
//    }
//
}
