package cart.persistence;

import cart.business.domain.product.Product;
import cart.business.domain.product.ProductImage;
import cart.business.domain.product.ProductName;
import cart.business.domain.product.ProductPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MemoryProductRepositoryTest {

    private MemoryProductRepository memoryProductRepository;

    @BeforeEach
    void init() {
        this.memoryProductRepository = new MemoryProductRepository();
    }

    @Test
    @DisplayName("Product 객체를 넣었을 때 ID를 반환할 수 있다")
    void test_insert() {
        //given
        Product salmon = new Product(1,
                new ProductName("salmon"),
                new ProductImage("https://www.salmonlover.com"),
                new ProductPrice(17000));

        //when
        Integer actualId = memoryProductRepository.insert(salmon);

        //then
        assertThat(actualId).isEqualTo(1);
    }

    @Test
    @DisplayName("모든 Product 객체를 반환할 수 있다")
    void test_findAll() {
        //given
        Product salmon = new Product(1,
                new ProductName("salmon"),
                new ProductImage("https://www.salmonlover.com"),
                new ProductPrice(17000));

        Product pizza = new Product(2,
                new ProductName("pizza"),
                new ProductImage("http://www.pizzalover.com"),
                new ProductPrice(40000));

        //when
        memoryProductRepository.insert(salmon);
        memoryProductRepository.insert(pizza);

        List<Product> allProducts = memoryProductRepository.findAll();
        Product actualSalmon = allProducts.get(0);
        Product actualPizza = allProducts.get(1);

        //then
        assertThat(actualSalmon.getId()).isEqualTo(salmon.getId());
        assertThat(actualPizza.getId()).isEqualTo(pizza.getId());
    }

    @Test
    @DisplayName("Product 객체를 업데이트 할 수 있다")
    void update() {
        //given
        Product salmon = new Product(1,
                new ProductName("salmon"),
                new ProductImage("https://www.salmonlover.com"),
                new ProductPrice(17000));

        Product pizza = new Product(1,
                new ProductName("pizza"),
                new ProductImage("http://www.pizzalover.com"),
                new ProductPrice(40000));
        //when
        memoryProductRepository.insert(salmon);
        memoryProductRepository.update(pizza);
        List<Product> products = memoryProductRepository.findAll();

        //then
        assertThat(products.get(0).getName()).isEqualTo("pizza");
    }

    @Test
    @DisplayName("해당하는 Product 객체를 삭제할 수 있다")
    void remove() {
        //given
        Product salmon = new Product(1,
                new ProductName("salmon"),
                new ProductImage("https://www.salmonlover.com"),
                new ProductPrice(17000));

        //when
        memoryProductRepository.insert(salmon);
        Product actual = memoryProductRepository.remove(1);

        //then
        assertThat(actual).isEqualTo(salmon);
    }
}
