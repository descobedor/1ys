package iys.customer.employee.synchroniser.service;

import iys.customer.employee.synchroniser.entity.Space;
import iys.customer.employee.synchroniser.model.CustomerSpace;
import iys.customer.employee.synchroniser.model.SpaceCreated;
import iys.customer.employee.synchroniser.repository.SynchronisesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito.when;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class SynchronisesServiceTest {

    SynchronisesService service;

    @Mock
    SynchronisesRepository repositoryMock;

    @BeforeEach
    void setUp() {
        service = new SynchronisesService(repositoryMock);
    }

    @Test
    void givenAnNewCustomerSpaceWhenCreateNewCustomerThenSaveIntoRepository() {
        when(repositoryMock.save(any()))
                .thenReturn(new Space(UUID.randomUUID().toString()));

        SpaceCreated spaceCreated = service.createNewCustomerSpace();

        assertNotNull(spaceCreated);
    }
}