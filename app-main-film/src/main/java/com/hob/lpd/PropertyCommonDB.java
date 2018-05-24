package com.hob.lpd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource({"classpath:db.properties"})
@Component
public class PropertyCommonDB {
    @Value("${jdbc.driverClassName}")
    public String driver;
    @Value("${jdbc.url}")
    public String url;
    @Value("${jdbc.user}")
    public String user;
    @Value("${jdbc.pass}")
    public String pass;

    public PropertyCommonDB() {

    }
}