package com.hob.lpd;


import com.hob.lpd.film.taskServices.DianyingId;
import com.hob.lpd.film.taskServices.Dianyingcontent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
public class commonTask {
    private final static Logger logger = Logger.getLogger(commonTask.class);


    @Autowired
    public commonTask(DianyingId dianyingId,
                      Dianyingcontent dianyingcontent){
        this.dianyingId = dianyingId;
        this.dianyingcontent = dianyingcontent;
    }

    private DianyingId dianyingId;
    private Dianyingcontent dianyingcontent;


	@Scheduled(cron="0/10 * * * * ? ")
    private void getFilmId(){
        logger.info("getFilmId start......");
        dianyingId.process();
        logger.info("getFilmId end......");

        logger.info("getFilmContent start......");
        dianyingcontent.process();
        logger.info("getFilmContent end......");
    }


}
