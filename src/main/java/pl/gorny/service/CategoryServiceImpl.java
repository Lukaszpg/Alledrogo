package pl.gorny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gorny.dao.CategoryDao;
import pl.gorny.model.Auction;
import pl.gorny.model.Category;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> getAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category getOne(Long id) {
        return categoryDao.findOne(id);
    }

    @Override
    public void add(Category category) {
        categoryDao.save(category);
    }
}
