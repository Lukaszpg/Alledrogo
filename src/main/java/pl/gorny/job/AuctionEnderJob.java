package pl.gorny.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;
import pl.gorny.dao.AuctionDao;
import pl.gorny.model.Auction;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AuctionEnderJob implements Job {

    private AuctionDao auctionDao;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        prepareAuctionDao();
        getAndIterateAuctions();
    }

    private void getAndIterateAuctions() {
        List<Auction> auctions = auctionDao.findAll();

        for(Auction auction : auctions) {
            if(shouldAuctionEnd(auction)) {
                endAuction(auction);
            }
        }
    }

    private void prepareAuctionDao() {
        ApplicationContext springContext =
                WebApplicationContextUtils.getWebApplicationContext(
                        ContextLoaderListener.getCurrentWebApplicationContext().getServletContext()
                );
        auctionDao = (AuctionDao) springContext.getBean("AuctionDao");
    }

    private boolean shouldAuctionEnd(Auction auction) {
        return LocalDateTime.now().isAfter(auction.getEndDate());
    }

    private void endAuction(Auction auction) {
        auctionDao.endAuction(auction.getId());
    }
}
