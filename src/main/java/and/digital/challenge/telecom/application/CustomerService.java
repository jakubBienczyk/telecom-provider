package and.digital.challenge.telecom.application;

import and.digital.challenge.telecom.api.exception.CustomerNotFoundException;
import and.digital.challenge.telecom.domain.Customer;
import and.digital.challenge.telecom.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomer(String customerId) {
        return customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

}
