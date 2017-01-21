package pl.gorny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.gorny.service.AuctionService;
import pl.gorny.service.CategoryService;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuctionService auctionService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getIndexPage() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("categoryList", categoryService.getAll());
        modelAndView.addObject("auctionList", auctionService.getTopFourAuctions());

        return modelAndView;
    }
}