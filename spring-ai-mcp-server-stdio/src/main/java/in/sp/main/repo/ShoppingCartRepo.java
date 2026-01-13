package in.sp.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.sp.main.entity.ShoppingCart;
import java.util.Optional;



@Repository
public interface ShoppingCartRepo extends JpaRepository<ShoppingCart, Long>{

	Optional<ShoppingCart> findByCustomer(String customer);
}
