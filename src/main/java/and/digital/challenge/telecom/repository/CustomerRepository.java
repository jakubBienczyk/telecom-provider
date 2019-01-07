package and.digital.challenge.telecom.repository;

import and.digital.challenge.telecom.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {

}
