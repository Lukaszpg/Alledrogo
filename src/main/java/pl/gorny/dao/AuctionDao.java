package pl.gorny.dao;

import pl.gorny.model.Auction;
import pl.gorny.model.Bid;

import java.util.List;

public interface AuctionDao {
    void save(Auction auction);

    List<Auction> findAll();

    List<Auction> getTopFour();

    Auction getOne(Long id);

    void saveBid(Bid bid);

    void setLastBidWinningStateToFalseByAuctionId(Long id);

    Bid findWinningBidByAuctionId(Long id);

    List<Bid> findBidsForAuction(Long id);

    void endAuction(Long id);

    List<Auction> findNotEndedAuctionsByCriteria(String item, Long categoryId);
}
