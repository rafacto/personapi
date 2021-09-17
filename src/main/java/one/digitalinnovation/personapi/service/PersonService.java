package one.digitalinnovation.personapi.service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exceptions.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private PersonRepository personRepository;
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public MessageResponseDTO createPerson(PersonDTO personDTO){

        Person personToBeSaved = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(personToBeSaved);
        return getMessageResponseDTO(savedPerson.getId(), "Created person with id ");
    }

    public List<PersonDTO> findAllPeople() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findPersonById(Long id) throws PersonNotFoundException {
        Person foundPerson = returnPersonIfExists(id);

        return personMapper.toDTO(foundPerson);
    }

    public void deletePersonById(Long id) throws PersonNotFoundException {
        returnPersonIfExists(id);
        personRepository.deleteById(id);
    }

    public MessageResponseDTO updatePersonById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        returnPersonIfExists(id);

        Person personToBeUpdated = personMapper.toModel(personDTO);
        Person updatedPerson = personRepository.save(personToBeUpdated);
        return getMessageResponseDTO(updatedPerson.getId(), "Updated person with id ");
    }

    private MessageResponseDTO getMessageResponseDTO(Long id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }

    private Person returnPersonIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() ->  new PersonNotFoundException(id));
    }
}
