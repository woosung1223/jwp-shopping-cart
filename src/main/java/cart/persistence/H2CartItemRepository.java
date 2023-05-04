package cart.persistence;

import cart.business.domain.cart.CartItemId;
import cart.business.domain.member.MemberId;
import cart.business.domain.product.ProductId;
import cart.business.repository.CartItemRepository;
import cart.business.domain.cart.CartItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class H2CartItemRepository implements CartItemRepository {

    private final RowMapper<CartItem> cartItemRowMapper = (resultSet, rowNum) -> {
        Integer id = resultSet.getInt("id");
        Integer productId = resultSet.getInt("product_id");
        Integer memberId = resultSet.getInt("member_id");

        return new CartItem(new CartItemId(id), new ProductId(productId), new MemberId(memberId));
    };

    private final JdbcTemplate jdbcTemplate;

    public H2CartItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer save(CartItem cartItem) {
        final String sql = "INSERT INTO CART (product_id, member_id) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, cartItem.getProductId());
            statement.setInt(2, cartItem.getMemberId());
            return statement;
        }, keyHolder);
        return keyHolder.getKeyAs(Integer.class);
    }

    @Override
    public CartItem remove(Integer cartItemId) {
        final String findSql = "SELECT * FROM CART WHERE id = (?)";
        final String sql = "DELETE FROM CART WHERE id = (?)";
        CartItem deleted = jdbcTemplate.queryForObject(findSql, cartItemRowMapper, cartItemId);
        jdbcTemplate.update(sql, cartItemId);
        return deleted;
    }

    @Override
    public List<CartItem> findAllByMemberId(Integer memberId) {
        final String sql = "SELECT * FROM CART WHERE member_id = (?)";
        return jdbcTemplate.query(sql, cartItemRowMapper, memberId);
    }
}
