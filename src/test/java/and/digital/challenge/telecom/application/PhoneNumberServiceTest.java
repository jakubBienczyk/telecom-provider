package and.digital.challenge.telecom.application;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import and.digital.challenge.telecom.api.dto.PhoneNumberDTO;
import and.digital.challenge.telecom.domain.Customer;
import and.digital.challenge.telecom.domain.PhoneNumber;
import and.digital.challenge.telecom.repository.PhoneNumberRepository;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PhoneNumberServiceTest {

    @Mock
    private PhoneNumberRepository phoneNumberRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private PhoneNumberService phoneNumberService;

    @Test
    public void shouldReturnAllPhoneNumbers() {
        // given
        Customer customer = givenCustomer();
        PhoneNumber number1 = givenPhoneNumberOne(customer);
        PhoneNumber number2 = givenPhoneNumberTwo(customer);
        given(phoneNumberRepository.findAll()).willReturn(asList(number1, number2));

        // when
        List<PhoneNumberDTO> phoneNumbers = phoneNumberService.getAllPhoneNumbers();

        // then
        assertThat(phoneNumbers)
            .extracting("id", "active", "number", "customerId")
            .contains(
                tuple(number1.getId(), number1.isActive(), number1.getNumber(), number1.getCustomer().getId()),
                tuple(number2.getId(), number2.isActive(), number2.getNumber(), number2.getCustomer().getId())
            );
    }

    @Test
    public void shouldReturnAllCustomerPhoneNumbers() {
        // given
        Customer customer = givenCustomer();
        PhoneNumber number1 = givenPhoneNumberOne(customer);
        PhoneNumber number2 = givenPhoneNumberTwo(customer);
        given(customerService.getCustomer(customer.getId())).willReturn(customer);
        given(phoneNumberRepository.findAllByCustomer(customer)).willReturn(asList(number1, number2));

        // when
        List<PhoneNumberDTO> phoneNumbers = phoneNumberService.getAllCustomerPhoneNumbers(customer.getId());

        // then
        assertThat(phoneNumbers)
            .extracting("id", "active", "number", "customerId")
            .contains(
                tuple(number1.getId(), number1.isActive(), number1.getNumber(), number1.getCustomer().getId()),
                tuple(number2.getId(), number2.isActive(), number2.getNumber(), number2.getCustomer().getId())
            );
    }

    @Test
    public void shouldActivatePhoneNumber() {
        // given
        Customer customer = givenCustomer();
        PhoneNumber number = givenNotActivatedPhoneNumber(customer);
        given(phoneNumberRepository.findById(number.getId())).willReturn(Optional.of(number));

        // when
        phoneNumberService.activatePhoneNumber(number.getId());

        // then
        verify(phoneNumberRepository).save(argThat(PhoneNumber::isActive));
    }

    private Customer givenCustomer() {
        return Customer.builder()
            .id("customer id")
            .build();
    }

    private PhoneNumber givenPhoneNumberOne(Customer customer) {
        return PhoneNumber.builder()
            .id("1")
            .active(true)
            .number("one")
            .customer(customer)
            .build();
    }

    private PhoneNumber givenPhoneNumberTwo(Customer customer) {
        return PhoneNumber.builder()
            .id("2")
            .active(false)
            .number("two")
            .customer(customer)
            .build();
    }

    private PhoneNumber givenNotActivatedPhoneNumber(Customer customer) {
        return PhoneNumber.builder()
            .id("not active")
            .active(false)
            .number("three")
            .customer(customer)
            .build();
    }

}