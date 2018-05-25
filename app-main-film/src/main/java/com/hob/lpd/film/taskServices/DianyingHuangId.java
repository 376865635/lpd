package com.hob.lpd.film.taskServices;

import com.dream.common.db.dao.BaseDictDao;
import com.dream.common.utils.StringUtils;
import com.dream.common.utils.okhttp.OkHttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Service
public class DianyingHuangId implements IService{



    @Autowired
    private BaseDictDao dianyingIdDao;

    @Autowired
    private OkHttpUtils okHttpUtils;

    public void process() {

        for(int i = 1; i <= 35; i++){
            System.out.println("第" + i + "页");
            getDoc(i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    String baseUrl = "http://www.btbtt88.com/forum-index-fid-9-page-";
    String suffix = ".htm";
    private int getDoc(int index){
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
//                    System.out.println(element);
                    continue;
                }
                Map<String,String> data = new HashMap<>();
                data.put("d_id",tid);
                data.put("type","1");
                data.put("page_num",index + "");
                System.out.println("导入成功： " +dianyingIdDao.insert(data));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

//    public static void main(String[] args) throws ParseException {
//
//        new DianyingHuangId().getDoc(2);
//    }
}
