package pl.gorny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.gorny.action.AddCategoryAction;
import pl.gorny.dto.ResponseDto;

@RestController
@RequestMapping("/rest/admin")
public class AdminRestController {

    @Autowired
    private AddCategoryAction addCategoryAction;

    @ResponseBody
    @PostMapping("/add-category")
    public ResponseDto saveCategory(@RequestBody String body) {
        addCategoryAction.setJson(body);
        addCategoryAction.execute();

        return addCategoryAction.getResponseDto();
    }
}
