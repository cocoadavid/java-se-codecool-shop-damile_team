import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.slf4j.impl.SimpleLogger;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.io.IOException;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {

        logger.info("testinfo");
        logger.debug("testdebug");

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8884);

        // populate some data for the memory storage
        populateData();

        // Always add generic routes to the end
        get("/", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(ProductController.renderProducts(req, res));
        });

        // Equivalent with above
        get("/Category/:categoryName", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(ProductController.renderForCategory(req, res));
        });
//
        get("/Supplier/:supplierName", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(ProductController.renderForSupplier(req, res));
        });

        // Add this line to your project to enable the debug screen
        enableDebugScreen();
    }

    public static void populateData() {

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

    }


}
