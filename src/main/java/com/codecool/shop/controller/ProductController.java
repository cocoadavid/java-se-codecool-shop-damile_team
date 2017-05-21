package com.codecool.shop.controller;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.ProductDaoWithJDBC;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProductController {

    private static ProductCategory filteredCategory;
    private static Supplier filteredSupplier;
    private static List<Product> filteredProduct;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    public static ModelAndView renderProducts(Request req, Response res) throws IOException {
//        ProductDao productDataStore = ProductDaoMem.getInstance();
//        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
//        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductDaoWithJDBC productDaoWithJDBC = new ProductDaoMemWithJDBC();
        SupplierDaoWithJDBC supplierDaoWithJDBC = new SupplierDaoMemWithJDBC();
        ProductCategoryDaoWithJDBC productCategoryDaoWithJDBC = new ProductCategoryDaoMemWithJDBC();

        Map params = new HashMap<>();
        params.put("category", productCategoryDaoWithJDBC.getAllCategories());
        params.put("supplier", supplierDaoWithJDBC.getAllSupplier());
        params.put("products", productDaoWithJDBC.listAllProducts());
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderForCategory(Request req, Response res) throws IOException {
//        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
//        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        ProductDaoWithJDBC productDaoWithJDBC = new ProductDaoMemWithJDBC();
        SupplierDaoWithJDBC supplierDaoWithJDBC = new SupplierDaoMemWithJDBC();
        ProductCategoryDaoWithJDBC productCategoryDaoWithJDBC = new ProductCategoryDaoMemWithJDBC();


        String selectedCategory = req.params(":categoryName");

        for (ProductCategory cat : productCategoryDaoWithJDBC.getAllCategories()) {
            if (selectedCategory.equals(cat.getName())) {
                filteredCategory = cat;
            }
        }
        logger.debug("Products filtered by the following category: {}", filteredCategory.getName());
        Map params = new HashMap<>();
        params.put("category", productCategoryDaoWithJDBC.getAllCategories());
        params.put("products", productDaoWithJDBC.getProductBy(filteredCategory));
        params.put("supplier", supplierDaoWithJDBC.getAllSupplier());
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderForSupplier(Request req, Response res) throws IOException {
//        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
//        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductDaoWithJDBC productDaoWithJDBC = new ProductDaoMemWithJDBC();
        SupplierDaoWithJDBC supplierDaoWithJDBC = new SupplierDaoMemWithJDBC();
        ProductCategoryDaoWithJDBC productCategoryDaoWithJDBC = new ProductCategoryDaoMemWithJDBC();

        String selectedSupplier = req.params(":supplierName");

        for (Supplier sup : supplierDaoWithJDBC.getAllSupplier()) {
            if (selectedSupplier.equals(sup.getName())) {
                filteredSupplier = sup;
            }
        }
        logger.debug("Products filtered by the following supplier: {}", filteredSupplier.getName());
        Map params = new HashMap<>();
        params.put("supplier", supplierDaoWithJDBC.getAllSupplier());
        params.put("category", productCategoryDaoWithJDBC.getAllCategories());
        params.put("products", productDaoWithJDBC.getProductBy(filteredSupplier));
        return new ModelAndView(params, "product/index");
    }
}
