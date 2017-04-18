package com.miteno.cloudna.config;

import org.springframework.context.annotation.Configuration;

/**
 * Created by tim on 18/04/2017.
 */
@Configuration
public class DatabaseConfig {

//    @Bean
//    public SpringLiquibase liquibase(DataSource dataSource) {
//        SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setDataSource(dataSource);
//        liquibase.setChangeLog("classpath:config/liquibase/master.xml");
//        liquibase.setContexts("development,test,production");
//        if (env.acceptsProfiles(Constants.SPRING_PROFILE_FAST)) {
//            if ("org.h2.jdbcx.JdbcDataSource".equals(propertyResolver.getProperty("dataSourceClassName"))) {
//                liquibase.setShouldRun(true);
//                log.warn("Using '{}' profile with H2 database in memory is not optimal, you should consider switching to" +
//                        " MySQL or Postgresql to avoid rebuilding your database upon each start.", Constants.SPRING_PROFILE_FAST);
//            } else {
//                liquibase.setShouldRun(false);
//            }
//        } else {
//            log.debug("Configuring Liquibase");
//        }
//        return liquibase;
//    }
}
