package org.finmate.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableScheduling
@PropertySource({"classpath:/application.properties"})
@MapperScan(basePackages = {
        "org.finmate.member.mapper",
        "org.finmate.product.mapper",
        "org.finmate.portfolio.mapper",
        "org.finmate.quiz.mapper",
        "org.finmate.attendance.mapper",
        "org.finmate.character.mapper",
        "org.finmate.email.mapper",
        "org.finmate.mypage.mapper"
})
@ComponentScan(basePackages={
        "org.finmate.assessment.service",
        "org.finmate.product.service",
        "org.finmate.member.service",
        "org.finmate.adapter",
        "org.finmate.attendance.service",
        "org.finmate.portfolio.service",
        "org.finmate.character.service",
        "org.finmate.email.service",
        "org.finmate.mypage.service",
        "org.finmate.security.handler",
        "org.finmate.quiz.service",
        "org.finmate.security.util"
})

public class RootConfig {
    @Value("${jdbc.driver}")
    String driver;
    @Value("${jdbc.url}")
    String url;
    @Value("${jdbc.username}")
    String username;
    @Value("${jdbc.password}")
    String password;
    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        config.setMaximumPoolSize(5);    // 동시에 유지할 최대 커넥션 수 줄이기
        config.setMinimumIdle(1);        // 최소 idle 커넥션 줄이기
        config.setIdleTimeout(300000);   // 5분 동안 사용 안 하면 정리
        config.setMaxLifetime(900000);   // 커넥션 최대 생존 시간 15분
        config.setConnectionTimeout(30000); // 커넥션 요청 최대 대기 시간 30초

        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:/mybatis-config.xml"));
        sqlSessionFactory.setDataSource(dataSource());

        return sqlSessionFactory.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager manager = new DataSourceTransactionManager(dataSource());

        return manager;
    }

    @Configuration
    public class JacksonConfig {
        @Bean
        public ObjectMapper objectMapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper;
        }
    }
}
