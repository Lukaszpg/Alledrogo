package pl.gorny.action;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.gorny.dto.AuctionDto;
import pl.gorny.model.Auction;
import pl.gorny.model.Category;
import pl.gorny.service.AuctionService;
import pl.gorny.service.CategoryService;
import pl.gorny.model.User;
import pl.gorny.service.SecurityService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CreateAuctionAction extends AbstractAction {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private SecurityService securityService;

    private AuctionDto auctionDto;

    private Auction auctionToPersist;

    @Override
    public void execute() {
        parseJsonToObject();
        prepareAuctionObject();
        saveAuction();
    }

    private void parseJsonToObject() {
        Gson gson = new Gson();
        auctionDto = gson.fromJson(json, AuctionDto.class);
    }

    private void prepareAuctionObject() {
        auctionToPersist = new Auction();
        auctionToPersist.setCategory(getCategoryFromDatabase());
        auctionToPersist.setTitle(auctionDto.getTitle());
        auctionToPersist.setDescription(auctionDto.getDescription());
        auctionToPersist.setEndDate(stringToDateTime());
        auctionToPersist.setItemsAmount(auctionDto.getAmount());
        auctionToPersist.setSeller(getSeller());
        auctionToPersist.setPrice(auctionDto.getPrice());
        auctionToPersist.setHasEnded(false);
    }

    private Category getCategoryFromDatabase() {
        return categoryService.getOne(auctionDto.getCategory());
    }

    private User getSeller() {
        return securityService.getUserByEmail(auctionDto.getUserEmail());
    }

    private void saveAuction() {
        auctionService.save(auctionToPersist);
        responseDto.success = true;
    }

    private LocalDateTime stringToDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(auctionDto.getEndDate() + " 00:00", formatter);
    }
}
