package pl.gorny.service;

import pl.gorny.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    Category getOne(Long id);
}
