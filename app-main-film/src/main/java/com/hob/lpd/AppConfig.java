package com.hob.lpd;


import com.alibaba.druid.pool.DruidDataSource;
import com.dream.common.db.DBPool;
import com.dream.common.db.IDBPool;
import com.dream.common.db.dao.BaseDictDao;
import com.dream.common.utils.okhttp.OkHttpUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;


@EnableAutoConfiguration
@Configuration
@PropertySource({"classpath:config.properties"})
@ComponentScan(basePackages = "com.hob.lpd")
public class AppConfig{
    private final static Logger logger = Logger.getLogger(AppConfig.class);
    public AppConfig() {
    }


    @Autowired
    private IDBPool pool;

    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public @Bean
    DataSource primaryDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Autowired
    public @Bean IDBPool pool(DataSource primaryDataSource, OkHttpUtils okHttpUtils) {
        DBPool pool = new DBPool();
        pool.setDataSource(primaryDataSource);
//        pool.setExceptionLogger( (e,position,sql,param) -> CacheIDBService.bulid(pool, okHttpUtils, springApplicationName, localStatus, e,position,sql,param) );
        return pool;
    }


//    public @Bean BaseDictDao dianyingContentDao(){
//        return new BaseDictDao(pool, "dianying_content");
//    }

    public @Bean BaseDictDao dianyingIdDao(){
        return new BaseDictDao(pool, "dianying_id");
    }

//工具类
    public @Bean OkHttpUtils okHttpUtils(){
        return new OkHttpUtils();
    }


}