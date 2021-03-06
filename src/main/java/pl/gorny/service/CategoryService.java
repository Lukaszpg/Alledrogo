package pl.gorny.service;

import pl.gorny.model.Category;

import java.math.BigInteger;
import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    Category getOne(Long id);

    void add(Category category);

    BigInteger getCategoryIdByCategoryName(String name);
}
