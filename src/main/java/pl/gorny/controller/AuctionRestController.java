package pl.gorny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.gorny.action.BidAction;
import pl.gorny.action.CreateAuctionAction;
import pl.gorny.dto.ResponseDto;

@RestController
public class AuctionRestController {

    @Autowired
    private CreateAuctionAction createAuctionAction;

    @Autowired
    private BidAction bidAction;

    @ResponseBody
    @PostMapping("/createAuction")
    public ResponseDto create(@RequestBody String body) {
        createAuctionAction.setJson(body);
        createAuctionAction.execute();

        return createAuctionAction.getResponseDto();
    }

    @ResponseBody
    @PostMapping("/auctions/details/bid")
    public ResponseDto bid(@RequestBody String body) {
        bidAction.setJson(body);
        bidAction.execute();

        return bidAction.getResponseDto();
    }
}
