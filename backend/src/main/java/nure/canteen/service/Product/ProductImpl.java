package nure.canteen.service.Product;

import lombok.extern.slf4j.Slf4j;
import nure.canteen.domain.Product;
import nure.canteen.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class ProductImpl implements ProductService {

    @Autowired
    ProductRepo productRepo;
    @Override
    public Product getById(Long id) {
        log.info("IN ProductServiceImpl getById {}", id);
        Optional<Product> productResponse = productRepo.findById(id);
        Product product = productResponse.get();
        return product;
    }

    @Override
    public void save(Product product) {
        log.info("IN ProductServiceImpl save {}", product);
        productRepo.save(product);
    }

    @Override
    public void delete(Long id) {
        log.info("IN ProductServiceImpl delete {}", id);
        productRepo.deleteById(id);

    }

    @Override
    public List<Product> getAll() {
        log.info("IN ProductServiceImpl getAll");
        return productRepo.findAll();
    }
}
