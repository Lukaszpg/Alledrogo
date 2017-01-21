package pl.gorny.action;

import com.google.gson.Gson;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.gorny.dto.AuctionDto;
import pl.gorny.dto.CategoryDto;
import pl.gorny.model.Category;
import pl.gorny.service.CategoryService;

@Component
public class AddCategoryAction extends AbstractAction {

    @Autowired
    private CategoryService categoryService;

    private CategoryDto categoryDto;

    private Category categoryToPersist;

    public AddCategoryAction() {
        logger = LoggerFactory.getLogger(AddCategoryAction.class);
    }

    @Override
    public void execute() {
        try {
            parseJsonToObject();
            prepareCategoryObject();
            saveCategory();
            responseDto.success = true;
        } catch(Exception e) {
            responseDto.success = false;
            logger.error(e.getMessage());
        }
    }

    private void parseJsonToObject() {
        Gson gson = new Gson();
        categoryDto = gson.fromJson(json, CategoryDto.class);
    }

    private void prepareCategoryObject() {
        categoryToPersist = new Category();
        categoryToPersist.setName(categoryDto.getName());
    }

    private void saveCategory() {
        categoryService.add(categoryToPersist);
    }
}
