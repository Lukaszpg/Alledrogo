package pl.gorny.utils;

public class Queries {
    public static final String GET_USER_BY_EMAIL = "SELECT u.* FROM users u WHERE u.email = :email";
    public static final String GET_ALL_CATEGORIES = "SELECT c.* FROM categories c";
    public static final String GET_CATEGORY_BY_ID = "SELECT c.* FROM categories c WHERE c.id = :id";
    public static final String GET_ALL_NOT_ENDED_AUCTIONS = "SELECT a.*, c.*, u.* FROM auctions a LEFT JOIN users " +
            "u ON a.seller_id = u.id LEFT JOIN categories c ON a.category_id = c.id WHERE a.has_ended = 0";
    public static final String GET_SINGLE_AUCTION = "SELECT a.*, c.*, u.* FROM auctions a LEFT JOIN users " +
            "u ON a.seller_id = u.id LEFT JOIN categories c ON a.category_id = c.id WHERE a.id = :id";
    public static final String UPDATE_LAST_WINNING_STATE_BY_AUCTION_ID = "UPDATE bids SET is_winning = 0 WHERE auction_id = :id";
    public static final String GET_WINNING_BID_BY_AUCTION_ID = "SELECT b.* FROM bids b WHERE b.is_winning = 1 AND b.auction_id = :id";
    public static final String GET_BIDS_BY_AUCTION_ID = "SELECT b.* FROM bids b LEFT JOIN users u ON b.buyer_id = u.id WHERE b.auction_id = :id";
}
