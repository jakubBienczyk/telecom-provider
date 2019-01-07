package and.digital.challenge.telecom.api.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PhoneNumberDTO {

    String id;

    String number;

    boolean active;

    String customerId;

}
