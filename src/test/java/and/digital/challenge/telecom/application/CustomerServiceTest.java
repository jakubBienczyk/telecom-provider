package and.digital.challenge.telecom.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import and.digital.challenge.telecom.api.exception.CustomerNotFoundException;
import and.digital.challenge.telecom.domain.Customer;
import and.digital.challenge.telecom.repository.CustomerRepository;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void shouldReturnCustomerWithGivenId() {
        // given
        String customerId = "id";
        Customer expectedCustomer = mock(Customer.class);
        given(customerRepository.findById(customerId)).willReturn(Optional.of(expectedCustomer));

        // when
        Customer customer = customerService.getCustomer(customerId);

        // then
        assertThat(customer).isEqualTo(expectedCustomer);
    }

    @Test(expected = CustomerNotFoundException.class)
    public void shouldThrowExceptionWhenCustomerDoesNotExist() {
        // given
        String customerId = "id";
        given(customerRepository.findById(customerId)).willReturn(Optional.empty());

        // when & then
        customerService.getCustomer(customerId);
    }

}