package ara.main.Service;

import ara.main.Entity.Product;
import ara.main.Repositories.JDBCQuerys;
import ara.main.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class ProductService implements CRUDInterface<Product> {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private JDBCQuerys jdbcQuerys;

    @Override
    public ResponseEntity<String> register(Product product) {
        try {
            if (productRepository.existsById(product.getIdProduct())) {
                return ResponseEntity.badRequest().body("Ya existe ese producto");
            }
            return saveProduct(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @Override
    public ResponseEntity<String> modify(Product product) {
        try {
            if (productRepository.existsById(product.getIdProduct())) {
                return saveProduct(product);
            }
            return ResponseEntity.badRequest().body("No se encuentra el producto");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    public ResponseEntity<String> delete(BigInteger identification) {
        try {
            if (identification == null) {
                return ResponseEntity.ofNullable("Rellene el campo");
            }
            if (productRepository.existsById(identification)) {
                productRepository.deleteById(identification);
                return ResponseEntity.ok("Eliminado Correctamente");
            } else {
                return ResponseEntity.badRequest().body("No existe el producto");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    public ResponseEntity<List<Product>> getAll() {
        try {
            if (productRepository.findAll() == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(productRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    private ResponseEntity<String> saveProduct(Product product) {
        if (product.getQuantityAvalaible() <= 0) {
            return ResponseEntity.badRequest().body("La cantidad disponible debe ser mayor a 0");
        }
        productRepository.save(product);
        return ResponseEntity.ok("El producto fue registrado con exito");
    }

    public ResponseEntity<List<Product>> findProducts(String character) {
        return ResponseEntity.ok(jdbcQuerys.findProduct(character.toLowerCase()));
    }

    public ResponseEntity<Product> findProductid(String character) {
        try {
            Product product = productRepository.findById(new BigInteger(character)).orElse(null);

            if (product != null) {
              return   ResponseEntity.ok(product);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    public  ResponseEntity<List<Product>> findMostPurchasedProduct(String dni){
        if (dni==null){
            return getAll();
        }
        List<BigInteger> listQuery=jdbcQuerys.getMostPurchased(dni);
        if (listQuery.isEmpty()){
            return getAll();
        }else{
            List<Product> products = new java.util.ArrayList<>(List.of());
            for (BigInteger item: listQuery){
                products.add(productRepository.findById(item).orElse(null));
            }
            return ResponseEntity.ok(products);
        }
    }
}
