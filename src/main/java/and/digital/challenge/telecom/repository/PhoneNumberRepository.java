package and.digital.challenge.telecom.repository;

import and.digital.challenge.telecom.domain.Customer;
import and.digital.challenge.telecom.domain.PhoneNumber;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, String> {

    List<PhoneNumber> findAllByCustomer(Customer customer);

}
