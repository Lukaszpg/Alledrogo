package pl.gorny.dao;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.gorny.model.Category;
import pl.gorny.utils.Queries;

import java.math.BigInteger;
import java.util.List;

@Transactional
@Repository("categoryDao")
public class CategoryDaoImpl extends AbstractDao implements CategoryDao {

    @Override
    public List<Category> findAll() {
        Query query = getSession().createSQLQuery(Queries.GET_ALL_CATEGORIES).addEntity(Category.class);

        return query.list();
    }

    @Override
    public Category findOne(Long id) {
        Query query = getSession().createSQLQuery(Queries.GET_CATEGORY_BY_ID).addEntity(Category.class);
        query.setParameter("id", id);

        return (Category) query.uniqueResult();
    }

    @Override
    public void save(Category category) {
        persist(category);
    }

    @Override
    public BigInteger findCategoryIdByCategoryName(String name) {
        Query query = getSession().createSQLQuery(Queries.GET_CATEGORY_ID_BY_NAME);
        query.setParameter("name", name);

        return (BigInteger) query.uniqueResult();
    }
}
