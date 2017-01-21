package pl.gorny.action;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.gorny.dao.AuctionDao;
import pl.gorny.dao.BidDto;
import pl.gorny.model.Auction;
import pl.gorny.model.Bid;
import pl.gorny.model.User;
import pl.gorny.service.AuctionService;
import pl.gorny.service.SecurityService;

@Component
public class BidAction extends AbstractAction {

    private BidDto bidDto;

    private Bid bidToPersist;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private SecurityService securityService;

    @Override
    public void execute() {
        parseJsonToObject();
        prepareBidObjectFromDto();
        setLastBidWinningStateToFalseByAuctionId();
        saveBid();
    }

    private void parseJsonToObject() {
        Gson gson = new Gson();
        bidDto = gson.fromJson(json, BidDto.class);
    }

    private void prepareBidObjectFromDto() {
        bidToPersist = new Bid();
        bidToPersist.setAmount(bidDto.getAmount());
        bidToPersist.setAuction(getAuctionById());
        bidToPersist.setBuyer(getUserById());
        bidToPersist.setWinning(true);
    }

    private void setLastBidWinningStateToFalseByAuctionId() {
        auctionService.setLastBidWinningStateToFalseByAuctionId(bidDto.getAuctionId());
    }

    private Auction getAuctionById() {
        return auctionService.getOne(bidDto.getAuctionId());
    }

    private User getUserById() {
        return securityService.getUserByEmail(bidDto.getBuyerEmail());
    }

    private void saveBid() {
        auctionService.saveBid(bidToPersist);
        responseDto.success = true;
    }
}
