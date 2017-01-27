package pl.gorny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.gorny.model.Auction;
import pl.gorny.service.AuctionService;
import pl.gorny.service.CategoryService;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuctionService auctionService;

    @PostMapping("/search")
    public ModelAndView search(@RequestParam("item") String item, @RequestParam("category") String categoryName) {
        ModelAndView modelAndView = new ModelAndView("fragments/search");
        Long categoryId = categoryService.getCategoryIdByCategoryName(categoryName).longValue();
        List<Auction> auctions = auctionService.getNotEndedAuctionsByCriteria(item, categoryId);
        modelAndView.addObject("auctions", auctions);
        modelAndView.addObject("item", item);

        return modelAndView;
    }
}
