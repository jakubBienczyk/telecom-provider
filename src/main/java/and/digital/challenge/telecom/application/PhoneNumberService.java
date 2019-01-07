package and.digital.challenge.telecom.application;

import static java.util.stream.Collectors.toList;

import and.digital.challenge.telecom.api.dto.PhoneNumberDTO;
import and.digital.challenge.telecom.api.exception.PhoneNumberNotFoundException;
import and.digital.challenge.telecom.domain.Customer;
import and.digital.challenge.telecom.domain.PhoneNumber;
import and.digital.challenge.telecom.repository.PhoneNumberRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberService {

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    private CustomerService customerService;

    public List<PhoneNumberDTO> getAllPhoneNumbers() {
        return phoneNumberRepository.findAll().stream()
            .map(this::buildPhoneNumberDTO)
            .collect(toList());
    }

    public List<PhoneNumberDTO> getAllCustomerPhoneNumbers(String customerId) {
        Customer customer = customerService.getCustomer(customerId);
        return phoneNumberRepository.findAllByCustomer(customer).stream()
            .map(this::buildPhoneNumberDTO)
            .collect(toList());
    }

    public void activatePhoneNumber(String phoneNumberId) {
        PhoneNumber phoneNumber = phoneNumberRepository.findById(phoneNumberId)
            .orElseThrow(() -> new PhoneNumberNotFoundException(phoneNumberId));
        phoneNumber.setActive(true);
        phoneNumberRepository.save(phoneNumber);
    }

    private PhoneNumberDTO buildPhoneNumberDTO(PhoneNumber phoneNumber) {
        return PhoneNumberDTO.builder()
            .id(phoneNumber.getId())
            .number(phoneNumber.getNumber())
            .active(phoneNumber.isActive())
            .customerId(phoneNumber.getCustomer().getId())
            .build();
    }
}
