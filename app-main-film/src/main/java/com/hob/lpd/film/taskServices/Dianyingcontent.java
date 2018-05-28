package com.hob.lpd.film.taskServices;

import com.dream.common.db.dao.BaseDictDao;
import com.dream.common.utils.okhttp.OkHttpUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service
public class Dianyingcontent implements IService {

    private final static Logger logger = Logger.getLogger(Dianyingcontent.class);

    @Autowired
    private BaseDictDao dianyingContentDao;

    @Autowired
    private OkHttpUtils okHttpUtils;

    public void process() {
        for(int i = 1; i <= 276; i++){
            System.out.println("第" + i + "页");
            List<Map<String, String>> list = getList(i);
            for(Map<String, String> temp : list){
                if(temp == null || temp.isEmpty()){
                    continue;
                }
                String film_id = temp.get("film_id");
                getContentDoc(film_id);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<Map<String,String>> getList(int pageNum){
        return dianyingContentDao.rawQuery("SELECT film_id from dianying_id where page_num = ?", new String[]{pageNum + ""});
    }






    String baseUrl = "http://www.btbtt88.com/thread-index-fid-1-tid-";
    String suffix = ".htm";
    String downBaseUrl = "http://www.btbtt88.com/attach-download-fid-1-aid-";
    String downSuffix = suffix;





    private int getContentDoc(String film_id){
        Document doc = null;
        try {
            String url = baseUrl + film_id + suffix;
            String content = okHttpUtils.doGetReturnString(url);
            doc = Jsoup.parse(content);
            Element elementTitle = doc.getElementById("subject_" + film_id);
            if(elementTitle == null){
                System.out.println("subject_" + film_id + "is null");
                return 0;
            }
            String title = elementTitle.text();
            Elements a = doc.getElementsByClass("ajaxdialog");
            Iterator it = a.iterator();
            while (it.hasNext()){
                Element element = (Element)it.next();
                String urlArr = element.attr("href");
                if(urlArr.contains("attach-dialog-fid-1-aid-")){
                    int start = urlArr.indexOf("attach-dialog-fid-1-aid-") +24;
                    int end = urlArr.indexOf("-ajax-1.htm");
                    String realDownUrl = downBaseUrl+ urlArr.substring(start,end) + downSuffix;
                    Map<String,String> data = new HashMap<>();
                    data.put("title",title);
                    data.put("film_id",film_id + "");
                    data.put("content",content);
                    data.put("down_url",realDownUrl);
                    boolean isExist = dianyingContentDao.rawQueryForInt("select count(*) from dianying_content where film_id = ?", new String[]{film_id}) > 0 ? true : false;
                    if(!isExist){
                        int result = dianyingContentDao.insert(data);
                        logger.info("新增电影content!!! film_id ==" + film_id);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

}
