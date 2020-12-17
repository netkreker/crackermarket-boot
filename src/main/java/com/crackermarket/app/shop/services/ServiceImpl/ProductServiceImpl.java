package com.crackermarket.app.shop.services.ServiceImpl;

import com.crackermarket.app.shop.entities.Product;
import com.crackermarket.app.shop.repository.ProductDAO;
import com.crackermarket.app.shop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductDAO productDAO;

    @Autowired
    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public ProductServiceImpl() {

    }

    @Override
    public void save(Product product) {
        productDAO.save(product);
    }

    @Override
    public Product findById(String id) {
        return productDAO.findById(UUID.fromString(id));
    }

    @Override
    public List<Product> findByName(String name) {
        return productDAO.findByName(name);
    }

    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public void delete(String id) {
        productDAO.delete(productDAO.findById(UUID.fromString(id)));
    }

    @Override
    public void update(Product product) {
        productDAO.update(product);
    }
}
