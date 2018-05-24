package com.hob.lpd.film.services;

import com.dream.common.db.dao.BaseDictDao;
import com.dream.common.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestService {


    @Autowired
    private BaseDictDao testDao;
    public void search() {
       List<Map<String, String>> list =  testDao.rawQuery("select * from test");
        System.out.println(JSONUtils.toJson(list));
    }
}
