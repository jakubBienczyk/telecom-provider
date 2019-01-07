package and.digital.challenge.telecom.api.exception;

public class PhoneNumberNotFoundException extends RuntimeException {

    public PhoneNumberNotFoundException(String phoneNumberId) {
        super(String.format("Phone number with id %s not found.", phoneNumberId));
    }
}
