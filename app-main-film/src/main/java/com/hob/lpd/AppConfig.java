package com.hob.lpd;


import com.dream.common.db.DBPool;
import com.dream.common.db.IDBPool;
import com.dream.common.db.dao.BaseDictDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;

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

    @Autowired
    @Lazy(false)
    public @Bean IDBPool dbPool(PropertyCommonDB db) {
        DBPool pool = new DBPool();
        pool.init(db.driver, db.url, db.user, db.pass);
        return pool;
    }

    public @Bean BaseDictDao testDao(){
        return new BaseDictDao(pool, "test");
    }


}