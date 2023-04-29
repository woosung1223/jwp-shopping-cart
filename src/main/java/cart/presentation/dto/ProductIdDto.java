package cart.presentation.dto;

import javax.validation.constraints.Positive;

public class ProductIdDto {

    @Positive(message = "상품 id는 0보다 커야 합니다.")
    private Integer id;

    public ProductIdDto() {
    }

    public ProductIdDto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
