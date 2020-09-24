package nure.canteen.service.Product;

import nure.canteen.domain.Product;

import java.util.List;

public interface ProductService {

    Product getById(Long id);

    void save(Product product);

    void delete(Long id);

    List<Product> getAll();
}
