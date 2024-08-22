package com.example.ecommerce.product;

import com.example.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    private final ProductMapper mapper;
    public Integer createProduct(@Valid ProductRequest request) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId() ;
    }

   public List<ProductPurchaseResponse> purchaseProducts (List<ProductPurchaseRequest> request){
       var productIds = request // if user buy 5 product
               .stream()
               .map(ProductPurchaseRequest::productId)
               .toList();
       var storedProducts = repository.findAllByIdInOrderById(productIds); //admin have 4
       if( productIds.size() != storedProducts.size() ){
           throw new ProductPurchaseException("One or more products dose not exits ");
       }
       var  storesRequest = request
               .stream()
               .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
               .toList();
       var purchasedProducts = new ArrayList<ProductPurchaseResponse>(); // List of purchased products using arraylist
            for(int i = 0; i < storesRequest.size(); i++){
                var product = storedProducts.get(i);
                var productRequest = storesRequest.get(i);
                if(product.getAvailableQuantity() < productRequest.quantity()){
                    throw new ProductPurchaseException("Insufficient stock quantity for product with ID ::"+productRequest.productId());
                }
                var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity(); //if database have 10, user buy 5. Update product = 5
                product.setAvailableQuantity(newAvailableQuantity);
                repository.save(product);
                purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
            }
       return purchasedProducts;
   }

    public ProductResponse findById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(()-> new EntityNotFoundException("Product not found with the ID ::"+ productId));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
