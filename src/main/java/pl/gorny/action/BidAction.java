package pl.gorny.action;

import com.google.gson.Gson;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.gorny.dto.BidDto;
import pl.gorny.dto.ResponseDto;
import pl.gorny.model.Auction;
import pl.gorny.model.Bid;
import pl.gorny.model.User;
import pl.gorny.service.AuctionService;
import pl.gorny.service.SecurityService;
import pl.gorny.utils.NumberUtils;

@Component
public class BidAction extends AbstractAction<BidDto> {

    private Bid bidToPersist;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private SecurityService securityService;

    public BidAction() {
        logger = LoggerFactory.getLogger(BidAction.class);
    }

    @Override
    public void execute() {
        try {
            responseDto = new ResponseDto();
            parseJsonToObject();
            if(validate()) {
                prepareBidObjectFromDto();
                setLastBidWinningStateToFalseByAuctionId();
                saveBid();
                parseWinningBidToJson();
                responseDto.success = true;
            }
        } catch (Exception e) {
            responseDto.success = false;
            logger.error(e.getMessage());
        }
    }

    private boolean validate() {
        if(!checkIsBidAmountValid()) {
            return false;
        }

        if(checkIfBuyerEqualsSeller()) {
            return false;
        }

        if(!checkIsBidLowerThanOrEqualToWinningBidOrOriginalPrice()) {
            return false;
        }

        if(checkIsAuctionEnded()) {
            return false;
        }

        return true;
    }

    private boolean checkIsBidAmountValid() {
        if(NumberUtils.isDouble(dto.getAmount())) {
            return true;
        }

        responseDto.message = "Podana kwota nie jest w właściwym formacie.";
        responseDto.success = false;

        return false;
    }

    private boolean checkIfBuyerEqualsSeller() {
        Auction auction = getAuctionById();
        User user = getUserByEmail();
        if(auction.getSeller().getId().equals(user.getId())) {
            responseDto.message = "Nie możesz licytować aukcji której jesteś wystawiającym.";
            responseDto.success = false;

            return true;
        }

        return false;
    }

    private boolean checkIsBidLowerThanOrEqualToWinningBidOrOriginalPrice() {
        Bid winningBid = auctionService.getWinningBidByAuctionId(dto.getAuctionId());
        if(winningBid != null) {
            if(winningBid.getAmount() >= Double.parseDouble(dto.getAmount())) {
                responseDto.message = "Wartość oferty nie może być mniejsza bądź równa wygrywającej ofercie.";
                responseDto.success = false;

                return false;
            }
        } else {
            Auction auction = auctionService.getOne(dto.getAuctionId());
            if(auction.getPrice() >= Double.parseDouble(dto.getAmount())) {
                responseDto.message = "Wartość oferty nie może być mniejsza bądź równa cenie początkowej.";
                responseDto.success = false;

                return false;
            }
        }

        return true;
    }

    private boolean checkIsAuctionEnded() {
        Auction auction = getAuctionById();
        if(auction.getHasEnded()) {
            responseDto.message = "Ta aukcja już się zakończyła.";
            responseDto.success = false;

            return true;
        }

        return false;
    }

    private void parseJsonToObject() {
        Gson gson = new Gson();
        dto = gson.fromJson(json, BidDto.class);
    }

    private void prepareBidObjectFromDto() {
        bidToPersist = new Bid();
        bidToPersist.setAmount(Double.parseDouble(dto.getAmount()));
        bidToPersist.setAuction(getAuctionById());
        bidToPersist.setBuyer(getUserByEmail());
        bidToPersist.setWinning(true);
    }

    private void setLastBidWinningStateToFalseByAuctionId() {
        auctionService.setLastBidWinningStateToFalseByAuctionId(dto.getAuctionId());
    }

    private Auction getAuctionById() {
        return auctionService.getOne(dto.getAuctionId());
    }

    private User getUserByEmail() {
        return securityService.getUserByEmail(dto.getBuyerEmail());
    }

    private void saveBid() {
        auctionService.saveBid(bidToPersist);
    }

    private void parseWinningBidToJson() {
        BidDto winningBidDto = new BidDto();
        Bid winningBid = auctionService.getWinningBidByAuctionId(dto.getAuctionId());
        if (winningBid != null) {
            Gson gson = new Gson();
            winningBidDto.setAmount(winningBid.getAmount().toString());
            responseDto.body = gson.toJson(winningBidDto);
        }
    }
}
