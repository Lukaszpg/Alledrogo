package pl.gorny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.gorny.action.GetSearchCategoriesAction;
import pl.gorny.dto.ResponseDto;

@RestController
public class TemplateRestController {

    @Autowired
    private GetSearchCategoriesAction getSearchCategoriesAction;

    @GetMapping("/get-search-categories")
    @ResponseBody
    public ResponseDto getSearchCategories() {
        getSearchCategoriesAction.execute();

        return getSearchCategoriesAction.getResponseDto();
    }
}
