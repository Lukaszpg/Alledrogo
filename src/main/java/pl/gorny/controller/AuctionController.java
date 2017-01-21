package pl.gorny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import pl.gorny.model.Auction;
import pl.gorny.service.AuctionService;
import pl.gorny.service.CategoryService;

@Controller
public class AuctionController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuctionService auctionService;

    @GetMapping("/sell")
    public ModelAndView getSellPage() {
        ModelAndView modelAndView = new ModelAndView("fragments/sell");
        modelAndView.addObject("categoryList", categoryService.getAll());

        return modelAndView;
    }

    @GetMapping("/auctions/details/{id}")
    public ModelAndView getSingleAuction(@PathVariable(value="id") Long id) {
        ModelAndView modelAndView = new ModelAndView("fragments/single");

        Auction auction = auctionService.getOne(id);
        auction.setWinningBid(auctionService.getWinningBidByAuctionId(id));
        auction.setBids(auctionService.getBidsForAuction(id));

        modelAndView.addObject("auction", auction);

        return modelAndView;
    }
}
