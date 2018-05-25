package com.hob.lpd.film.taskServices;

import com.dream.common.db.IDBPool;
import com.dream.common.db.dao.BaseDictDao;
import com.dream.common.utils.DateUtils;
import com.dream.common.utils.StringUtils;
import com.dream.common.utils.okhttp.OkHttpUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Service
public class DianyingId implements IService {

    private final static Logger logger = Logger.getLogger(DianyingId.class);
    private IDBPool pool;

    @Autowired
    public DianyingId(IDBPool pool) {
        this.pool = pool;
    }

    @Autowired
    private BaseDictDao dianyingIdDao;

    @Autowired
    private OkHttpUtils okHttpUtils;

    String baseUrl = "http://www.btbtt88.com/forum-index-fid-1-page-";
    String vipbaseUrl = "http://www.btbtt88.com/forum-index-fid-9-page-";
    String suffix = ".htm";
    public void process() {
        for(int i = 1; i <= 276; i++){
            System.out.println("第" + i + "页");
            getDoc(i, baseUrl);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private int getDoc(int index, String baseUrl){
        Document doc = null;
        try {
            String url = baseUrl + index + suffix;
            String content = okHttpUtils.doGetReturnString(url);
            doc = Jsoup.parse(content);
            Elements a = doc.getElementsByClass("icon");
            Iterator it = a.iterator();
            while (it.hasNext()){
                Element element = (Element)it.next();
                String tid =element.attr("tid");
                if(StringUtils.isEmpty(tid)){
                    continue;
                }
                Map<String,String> data = new HashMap<>();
                data.put("film_id",tid);
                data.put("type","0");
                data.put("page_num",index + "");
                data.put("intime", DateUtils.datetimeFormat().format(new Date()));
                boolean isExist = dianyingIdDao.rawQueryForInt("select count(*) from dianying_id where film_id = ?", new String[]{tid}) > 0 ? true: false;
                if(!isExist){
                    int result = dianyingIdDao.insert(data);
                    String isSuccess = result > 0 ? "==成功！！！" : "==失败！！！";
                    logger.info("新增电影id ==" + tid + isSuccess);
                }
                logger.info("电影id ==" + tid + "==存在！！！");
            }
        } catch (Exception e) {
            logger.error("DianyingId 获取电影id 失败！！!" + e.getMessage());
        }
        return 0;
    }


}
