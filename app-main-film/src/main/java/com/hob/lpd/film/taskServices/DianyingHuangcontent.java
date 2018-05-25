package com.hob.lpd.film.taskServices;

import com.dream.common.db.dao.BaseDictDao;
import com.dream.common.utils.okhttp.OkHttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service
public class DianyingHuangcontent implements IService{



    @Autowired
    private BaseDictDao dianyingContentDao;

    @Autowired
    private OkHttpUtils okHttpUtils;

    public void process() {

        for(int i = 1; i <= 35; i++){
            System.out.println("第" + i + "页");
            List<Map<String, String>> list = getList(i);
            for(Map<String, String> temp : list){
                if(temp == null || temp.isEmpty()){
                    continue;
                }
                String d_id = temp.get("d_id");
                getDoc(d_id);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }


    }

    private List<Map<String,String>> getList(int pageNum){
        return dianyingContentDao.rawQuery("SELECT d_id FROM `dianying_id` where type = 1 and page_num = ?", new String[]{pageNum + ""});
    }






    String baseUrl = "http://www.btbtt88.com/thread-index-fid-9-tid-";
    String suffix = ".htm";
    String downBaseUrl = "http://www.btbtt88.com/attach-download-fid-9-aid-";
    String downSuffix = ".htm";





    private int getDoc(String d_id){
        Document doc = null;
        try {
            String url = baseUrl + d_id + suffix;

            String content = okHttpUtils.doGetReturnString(url);
            doc = Jsoup.parse(content);
            Element elementTitle = doc.getElementById("subject_" + d_id);
            if(elementTitle == null){
                System.out.println("subject_" + d_id + "is null");
                return 0;
            }
            String title = elementTitle.text();
            Elements a = doc.getElementsByClass("ajaxdialog");
            Iterator it = a.iterator();
            while (it.hasNext()){
                Element element = (Element)it.next();
                String urlArr = element.attr("href");
                if(urlArr.contains("attach-dialog-fid-9-aid-")){
                    int start = urlArr.indexOf("attach-dialog-fid-9-aid-") +24;
                    int end = urlArr.indexOf("-ajax-1.htm");
                    System.out.println(urlArr.substring(start,end));
                    String realDownUrl = downBaseUrl+ urlArr.substring(start,end) + downSuffix;
                    Map<String,String> data = new HashMap<>();
                    data.put("title",title);
                    data.put("d_id",d_id + "");
                    data.put("type","1");
                    data.put("content",content);
                    data.put("down_url",realDownUrl);
                    System.out.println(dianyingContentDao.insert(data));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static void main(String[] args) throws ParseException {

//        new Dianyingcontent().getDoc(13089);
    }
}
