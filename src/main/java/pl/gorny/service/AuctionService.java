package pl.gorny.service;

import pl.gorny.model.Auction;
import pl.gorny.model.Bid;

import java.util.List;

public interface AuctionService {
    void save(Auction auction);

    List<Auction> getAll();

    List<Auction> getTopFourAuctions();

    Auction getOne(Long id);

    void saveBid(Bid bid);

    void setLastBidWinningStateToFalseByAuctionId(Long id);

    Bid getWinningBidByAuctionId(Long id);

    List<Bid> getBidsForAuction(Long id);

    List<Auction> getNotEndedAuctionsByCriteria(String item, Long id);
}
