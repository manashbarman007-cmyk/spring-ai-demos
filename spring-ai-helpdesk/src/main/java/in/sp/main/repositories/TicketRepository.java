package in.sp.main.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.sp.main.entity.Ticket;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{
	
	Optional<Ticket> findByEmail(String email); // JPA creates it automatically
		
}
