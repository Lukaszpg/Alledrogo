package pl.gorny.action;

import com.google.gson.Gson;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.gorny.dto.AuctionDto;
import pl.gorny.dto.ResponseDto;
import pl.gorny.model.Auction;
import pl.gorny.model.Category;
import pl.gorny.service.AuctionService;
import pl.gorny.service.CategoryService;
import pl.gorny.model.User;
import pl.gorny.service.SecurityService;
import pl.gorny.utils.DateUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CreateAuctionAction extends AbstractAction<AuctionDto> {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private SecurityService securityService;

    private Auction auctionToPersist;

    public CreateAuctionAction() {
        responseDto = new ResponseDto();
        logger = LoggerFactory.getLogger(CreateAuctionAction.class);
    }

    @Override
    public void execute() {
        try {
            parseJsonToObject();
            prepareAuctionObject();
            saveAuction();
            responseDto.success = true;
        } catch(Exception e) {
            responseDto.success = false;
            logger.error(e.getMessage());
        }
    }

    private void parseJsonToObject() {
        Gson gson = new Gson();
        dto = gson.fromJson(json, AuctionDto.class);
    }

    private void prepareAuctionObject() {
        auctionToPersist = new Auction();
        auctionToPersist.setCategory(getCategoryFromDatabase());
        auctionToPersist.setTitle(dto.getTitle());
        auctionToPersist.setDescription(dto.getDescription());
        auctionToPersist.setEndDate(prepareAuctionEndDate());
        auctionToPersist.setItemsAmount(dto.getAmount());
        auctionToPersist.setSeller(getSeller());
        auctionToPersist.setPrice(dto.getPrice());
        auctionToPersist.setHasEnded(false);
    }

    private LocalDateTime prepareAuctionEndDate() {
        return LocalDateTime.now().plusDays(dto.getDurationInDays());
    }

    private Category getCategoryFromDatabase() {
        return categoryService.getOne(dto.getCategory());
    }

    private User getSeller() {
        return securityService.getUserByEmail(dto.getUserEmail());
    }

    private void saveAuction() {
        auctionService.save(auctionToPersist);
    }
}
