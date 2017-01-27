package pl.gorny.dao;

import pl.gorny.model.Auction;
import pl.gorny.model.Category;

import java.math.BigInteger;
import java.util.List;

public interface CategoryDao {
    List<Category> findAll();

    Category findOne(Long id);

    void save(Category category);

    BigInteger findCategoryIdByCategoryName(String name);
}
