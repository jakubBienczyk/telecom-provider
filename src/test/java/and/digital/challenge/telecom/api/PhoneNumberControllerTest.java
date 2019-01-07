package and.digital.challenge.telecom.api;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import and.digital.challenge.telecom.api.dto.PhoneNumberDTO;
import and.digital.challenge.telecom.api.exception.CustomerNotFoundException;
import and.digital.challenge.telecom.api.exception.PhoneNumberNotFoundException;
import and.digital.challenge.telecom.application.PhoneNumberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PhoneNumberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneNumberService phoneNumberService;

    @Test
    public void shouldReturnAllPhoneNumbers() throws Exception {
        // given
        PhoneNumberDTO numberOne = givenPhoneNumberOne();
        PhoneNumberDTO numberTwo = givenPhoneNumberTwo();
        given(phoneNumberService.getAllPhoneNumbers()).willReturn(asList(numberOne, numberTwo));

        // when & then
        mockMvc.
            perform(get("/numbers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))

            .andExpect(jsonPath("$[0].id", is(equalTo(numberOne.getId()))))
            .andExpect(jsonPath("$[0].active", is(equalTo(numberOne.isActive()))))
            .andExpect(jsonPath("$[0].number", is(equalTo(numberOne.getNumber()))))
            .andExpect(jsonPath("$[0].customerId", is(equalTo(numberOne.getCustomerId()))))

            .andExpect(jsonPath("$[1].id", is(equalTo(numberTwo.getId()))))
            .andExpect(jsonPath("$[1].active", is(equalTo(numberTwo.isActive()))))
            .andExpect(jsonPath("$[1].number", is(equalTo(numberTwo.getNumber()))))
            .andExpect(jsonPath("$[1].customerId", is(equalTo(numberTwo.getCustomerId()))));
    }

    @Test
    public void shouldReturnAllCustomerPhoneNumbers() throws Exception {
        // given
        String customerId = "customer id";
        PhoneNumberDTO numberOne = givenPhoneNumberOne();
        PhoneNumberDTO numberTwo = givenPhoneNumberTwo();
        given(phoneNumberService.getAllCustomerPhoneNumbers(customerId)).willReturn(asList(numberOne, numberTwo));

        // when & then
        mockMvc.
            perform(get("/customer/" + customerId + "/numbers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))

            .andExpect(jsonPath("$[0].id", is(equalTo(numberOne.getId()))))
            .andExpect(jsonPath("$[0].active", is(equalTo(numberOne.isActive()))))
            .andExpect(jsonPath("$[0].number", is(equalTo(numberOne.getNumber()))))
            .andExpect(jsonPath("$[0].customerId", is(equalTo(numberOne.getCustomerId()))))

            .andExpect(jsonPath("$[1].id", is(equalTo(numberTwo.getId()))))
            .andExpect(jsonPath("$[1].active", is(equalTo(numberTwo.isActive()))))
            .andExpect(jsonPath("$[1].number", is(equalTo(numberTwo.getNumber()))))
            .andExpect(jsonPath("$[1].customerId", is(equalTo(numberTwo.getCustomerId()))));
    }

    @Test
    public void shouldReturnNotFoundWhileGettingNonExistingCustomerPhoneNumbers() throws Exception {
        // given
        String customerId = "customer id";
        given(phoneNumberService.getAllCustomerPhoneNumbers(customerId))
            .willThrow(CustomerNotFoundException.class);

        // when & then
        mockMvc.
            perform(get("/customer/" + customerId + "/numbers"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnNoContentAndActivatePhoneNumber() throws Exception {
        // given
        String phoneNumberId = "number id";
        willDoNothing().given(phoneNumberService).activatePhoneNumber(phoneNumberId);

        // when & then
        mockMvc.
            perform(put("/number/" + phoneNumberId + "/activate"))
            .andExpect(status().isNoContent());
        verify(phoneNumberService).activatePhoneNumber(phoneNumberId);
    }

    @Test
    public void shouldReturnNotFoundWhileActivatingNonExistingPhoneNumber() throws Exception {
        // given
        String phoneNumberId = "number id";
        willThrow(PhoneNumberNotFoundException.class).given(phoneNumberService).activatePhoneNumber(phoneNumberId);

        // when & then
        mockMvc.
            perform(put("/number/" + phoneNumberId + "/activate"))
            .andExpect(status().isNotFound());
    }

    private PhoneNumberDTO givenPhoneNumberOne() {
        return PhoneNumberDTO.builder()
            .id("1")
            .active(true)
            .number("one")
            .customerId("customer 1")
            .build();
    }

    private PhoneNumberDTO givenPhoneNumberTwo() {
        return PhoneNumberDTO.builder()
            .id("2")
            .active(false)
            .number("two")
            .customerId("customer 2")
            .build();
    }

}