package pl.gorny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gorny.dao.AuctionDao;
import pl.gorny.model.Auction;
import pl.gorny.model.Bid;

import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private AuctionDao auctionDao;

    @Override
    public void save(Auction auction) {
        auctionDao.save(auction);
    }

    @Override
    public List<Auction> getAll() {
        return auctionDao.findAll();
    }

    @Override
    public List<Auction> getTopFourAuctions() {
        return auctionDao.getTopFour();
    }

    @Override
    public Auction getOne(Long id) {
        return auctionDao.getOne(id);
    }

    @Override
    public void saveBid(Bid bid) {
        auctionDao.saveBid(bid);
    }

    @Override
    public void setLastBidWinningStateToFalseByAuctionId(Long id) {
        auctionDao.setLastBidWinningStateToFalseByAuctionId(id);
    }

    @Override
    public Bid getWinningBidByAuctionId(Long id) {
        return auctionDao.findWinningBidByAuctionId(id);
    }

    @Override
    public List<Bid> getBidsForAuction(Long id) {
        return auctionDao.findBidsForAuction(id);
    }
}
