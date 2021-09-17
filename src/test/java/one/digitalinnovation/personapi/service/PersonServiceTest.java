package one.digitalinnovation.personapi.service;

import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.repository.PersonRepository;
import one.digitalinnovation.personapi.utils.PersonUtils;
import org.apache.logging.log4j.message.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void testGivenPersonDTOReturnSavedMessage() {
        PersonDTO personDTO = PersonUtils.createFakeDTO();
        Person savedPerson = PersonUtils.createFakeEntity();

        when(personRepository.save(any(Person.class))).thenReturn(savedPerson);

        MessageResponseDTO expectedMessage = createExpectedMessage(savedPerson.getId());
        MessageResponseDTO returnedMessage = personService.createPerson(personDTO);

        Assertions.assertEquals(expectedMessage, returnedMessage);
    }

    private MessageResponseDTO createExpectedMessage(Long id) {
        return MessageResponseDTO.builder()
                .message("Created person with id " + id)
                .build();
    }
}
