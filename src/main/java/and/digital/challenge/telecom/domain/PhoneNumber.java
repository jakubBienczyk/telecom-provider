package and.digital.challenge.telecom.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity(name = "phone_number")
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumber {

    @Id
    private String id;

    @Column
    private String number;

    @Column
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}
