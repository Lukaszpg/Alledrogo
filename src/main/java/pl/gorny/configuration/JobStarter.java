package pl.gorny.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.gorny.job.service.AuctionEnderJobService;

@Component
public class JobStarter {

    @Autowired
    private AuctionEnderJobService auctionEnderJobService;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        auctionEnderJobService.startAuctionEnderJon();
    }
}