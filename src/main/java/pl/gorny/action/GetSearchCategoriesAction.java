package pl.gorny.action;

import com.google.gson.Gson;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.gorny.dto.CategoryDto;
import pl.gorny.dto.ResponseDto;
import pl.gorny.model.Category;
import pl.gorny.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetSearchCategoriesAction extends AbstractAction<CategoryDto> {

    @Autowired
    private CategoryService categoryService;

    private List<Category> categories;

    private List<CategoryDto> categoryDtos;

    public GetSearchCategoriesAction() {
        responseDto = new ResponseDto();
        logger = LoggerFactory.getLogger(GetSearchCategoriesAction.class);
    }

    @Override
    public void execute() {
        try {
            getAllCategories();
            parseCategoriesToDto();
            parseDtosToJson();
            responseDto.success = true;
        } catch(Exception e) {
            responseDto.success = false;
            logger.error(e.getMessage());
        }
    }

    private void getAllCategories() {
        categories = categoryService.getAll();
    }

    private void parseCategoriesToDto() {
        categoryDtos = new ArrayList<>();

        if(categories != null && !categories.isEmpty()) {
            for (Category category : categories) {
                CategoryDto categoryDto = new CategoryDto();
                categoryDto.setName(category.getName());
                categoryDtos.add(categoryDto);
            }
        }
    }

    private void parseDtosToJson() {
        Gson gson = new Gson();
        responseDto.body = gson.toJson(categoryDtos);
    }
}
