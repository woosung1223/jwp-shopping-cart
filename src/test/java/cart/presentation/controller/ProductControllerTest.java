package cart.presentation.controller;

import cart.business.domain.product.Product;
import cart.business.domain.product.ProductId;
import cart.business.domain.product.ProductImage;
import cart.business.domain.product.ProductName;
import cart.business.domain.product.ProductPrice;
import cart.business.service.ProductService;
import cart.config.WebMvcConfiguration;
import cart.presentation.adapter.AuthInterceptor;
import cart.presentation.dto.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ProductController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        WebMvcConfiguration.class,
                        AuthInterceptor.class
                }
        ))
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("/product 로 POST 요청을 보낼 수 있다")
    void test_create_request() throws Exception {
        // given
        willDoNothing().given(productService).create(any(Product.class));

        String content = objectMapper.writeValueAsString(
                new ProductDto(1, "teo", "https://", 1));

        // when
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                // then
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("/product 로 GET 요청을 보낼 수 있다")
    void test_read_request() throws Exception {
        // given
        given(productService.readAll()).willReturn(List.of(
                new Product(new ProductId(1), new ProductName("test"),
                        new ProductImage("https://test.com"), new ProductPrice(1000))
        ));

        // when
        mockMvc.perform(get("/product"))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("test"))
                .andExpect(jsonPath("$[0].url").value("https://test.com"))
                .andExpect(jsonPath("$[0].price").value(1000));
    }

    @Test
    @DisplayName("/product 로 PUT 요청을 보낼 수 있다")
    void test_update_request() throws Exception {
        // given
        willDoNothing().given(productService).update(any(Product.class));

        String content = objectMapper.writeValueAsString(
                new ProductDto(1, "teo", "https://", 1)
        );

        // when
        mockMvc.perform(put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))

                // then
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("/product 로 DELETE 요청을 보낼 수 있다")
    void test_delete_request() throws Exception {
        // given
        willDoNothing().given(productService).delete(any(Integer.class));

        String content = objectMapper.writeValueAsString(
                new ProductDto(1, "teo", "https://", 1)
        );

        // when
        mockMvc.perform(delete("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))

                // then
                .andExpect(status().isNoContent());
    }
}
