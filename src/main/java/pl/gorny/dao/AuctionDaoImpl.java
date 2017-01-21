package pl.gorny.dao;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.gorny.model.Auction;
import pl.gorny.model.Bid;
import pl.gorny.utils.Queries;

import java.util.List;

@Transactional
@Repository("AuctionDao")
public class AuctionDaoImpl extends AbstractDao implements AuctionDao {

    @Override
    public void save(Auction auction) {
        persist(auction);
    }

    @Override
    public List<Auction> findAll() {
        Query query = getSession().createSQLQuery(Queries.GET_ALL_NOT_ENDED_AUCTIONS).addEntity(Auction.class);
        return query.list();
    }

    @Override
    public List<Auction> getTopFour() {
        Query query = getSession().createSQLQuery(Queries.GET_ALL_NOT_ENDED_AUCTIONS).addEntity(Auction.class);
        query.setMaxResults(4);
        return query.list();
    }

    @Override
    public Auction getOne(Long id) {
        Query query = getSession().createSQLQuery(Queries.GET_SINGLE_AUCTION).addEntity(Auction.class);
        query.setParameter("id", id);

        return (Auction) query.uniqueResult();
    }

    @Override
    public void saveBid(Bid bid) {
        persist(bid);
    }

    @Override
    public void setLastBidWinningStateToFalseByAuctionId(Long id) {
        Query query = getSession().createSQLQuery(Queries.UPDATE_LAST_WINNING_STATE_BY_AUCTION_ID);
        query.setParameter("id", id);

        query.executeUpdate();
    }

    @Override
    public Bid findWinningBidByAuctionId(Long id) {
        Query query = getSession().createSQLQuery(Queries.GET_WINNING_BID_BY_AUCTION_ID).addEntity(Bid.class);
        query.setParameter("id", id);

        return (Bid) query.uniqueResult();
    }

    @Override
    public List<Bid> findBidsForAuction(Long id) {
        Query query = getSession().createSQLQuery(Queries.GET_BIDS_BY_AUCTION_ID).addEntity(Bid.class);
        query.setParameter("id", id);

        return query.list();
    }
}
