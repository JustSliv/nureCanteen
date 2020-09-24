package nure.canteen.controller;

import nure.canteen.domain.Product;
import nure.canteen.service.Product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/product/")
public class ProductController {

    @Autowired
    public ProductService productService;

    @RequestMapping(value = {"id"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long productId){
        HttpHeaders headers = new HttpHeaders();

        if(productId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product product = this.productService.getById(productId);

        return new ResponseEntity<>(product,headers, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid Product product){
        HttpHeaders headers = new HttpHeaders();

        if(product == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.productService.save(product);
        return new ResponseEntity<>(product, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product product, UriComponentsBuilder builder){
        HttpHeaders headers = new HttpHeaders();

        if(product == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.productService.save(product);

        return new ResponseEntity<>(product, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "id", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Product> deleteProduct(Long id){
        Product product = this.productService.getById(id);

        if(product == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.productService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Product>> getAllPersons(){
        List<Product> products = this.productService.getAll();

        if(products.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
