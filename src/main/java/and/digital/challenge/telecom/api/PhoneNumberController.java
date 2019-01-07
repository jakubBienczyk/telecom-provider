package and.digital.challenge.telecom.api;

import and.digital.challenge.telecom.api.dto.PhoneNumberDTO;
import and.digital.challenge.telecom.api.exception.CustomerNotFoundException;
import and.digital.challenge.telecom.api.exception.PhoneNumberNotFoundException;
import and.digital.challenge.telecom.application.PhoneNumberService;
import com.aol.cyclops.trycatch.Try;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PhoneNumberController {

    @Autowired
    private PhoneNumberService phoneNumberService;

    @GetMapping(value = "/numbers")
    public List<PhoneNumberDTO> getPhoneNumbers() {
        return phoneNumberService.getAllPhoneNumbers();
    }

    @GetMapping(value = "/customer/{customerId}/numbers")
    public ResponseEntity<List<PhoneNumberDTO>> getCustomerPhoneNumbers(@PathVariable String customerId) {
        return Try.withCatch(() -> phoneNumberService.getAllCustomerPhoneNumbers(customerId), CustomerNotFoundException.class)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/number/{numberId}/activate")
    public ResponseEntity activatePhoneNumber(@PathVariable String numberId) {
        return Try.runWithCatch(() -> phoneNumberService.activatePhoneNumber(numberId), PhoneNumberNotFoundException.class)
            .map(v -> ResponseEntity.noContent().build())
            .orElse(ResponseEntity.notFound().build());
    }

}
