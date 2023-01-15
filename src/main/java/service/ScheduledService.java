package service;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class ScheduledService {
    @EJB
    HelloService taskService;

    @Schedule(second="*/10", minute="*",hour="*", persistent=false)
    public void doWork(){
        taskService.checkExpiredAuctions();
    }
}
