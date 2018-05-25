package com.hob.lpd;


import com.hob.lpd.film.taskServices.DianyingId;
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
    public commonTask(DianyingId dianyingId){
        this.dianyingId = dianyingId;
    }

    private DianyingId dianyingId;

	@Scheduled(cron="0 54 14 * * ? ")
    private void getFilmId(){
        logger.info("getFilmId start......");
        dianyingId.process();
        logger.info("getFilmId end......");
    }


}
