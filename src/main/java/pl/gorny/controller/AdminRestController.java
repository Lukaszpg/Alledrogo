package pl.gorny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.gorny.action.AddCategoryAction;
import pl.gorny.dto.ResponseDto;

@RestController
public class AdminRestController {

    @Autowired
    private AddCategoryAction addCategoryAction;

    @ResponseBody
    @PostMapping("/admin/save-category")
    public ResponseDto saveCategory(@RequestBody String body) {
        addCategoryAction.setJson(body);
        addCategoryAction.execute();

        return addCategoryAction.getResponseDto();
    }
}
