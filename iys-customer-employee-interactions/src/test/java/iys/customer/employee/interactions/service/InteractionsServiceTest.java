package iys.customer.employee.interactions.service;

import iys.customer.employee.interactions.assembler.InteractionAssemblerModel;
import iys.customer.employee.interactions.assembler.InteractionModel;
import iys.customer.employee.interactions.entity.Reference;
import iys.customer.employee.interactions.entity.Space;
import iys.customer.employee.interactions.model.CustomerSpace;
import iys.customer.employee.interactions.model.SpaceCreated;
import iys.customer.employee.interactions.model.SpaceWithDetail;
import iys.customer.employee.interactions.repository.ReferenceRepository;
import iys.customer.employee.interactions.repository.SpaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.Direction.DESC;

@ExtendWith(SpringExtension.class)
class InteractionsServiceTest {

    InteractionsService service;

    @Mock
    SpaceRepository repositoryMock;

    @Mock
    ReferenceRepository referenceRepositoryMock;

    @Mock
    PagedResourcesAssembler<Space> pagedResourcesAssemblerMock;

    @Mock
    InteractionAssemblerModel interactionAssemblerModelMock;

    @BeforeEach
    void setUp() {
        service = new InteractionsService(repositoryMock, referenceRepositoryMock, pagedResourcesAssemblerMock,
                interactionAssemblerModelMock);
    }

    @Test
    void givenAnNewCustomerSpaceWhenCreateNewCustomerThenSaveIntoRepository() {
        Space space = new Space();
        space.setId(UUID.randomUUID());
        when(repositoryMock.save(any())).thenReturn(space);

        SpaceCreated spaceCreated = service.createNewCustomerSpace(new CustomerSpace());

        assertNotNull(spaceCreated);
        assertNotNull(spaceCreated.space());
        assertEquals(space.getId(), spaceCreated.space().getId());
    }


    @Test
    void givenSpacesIntoRepositoryWhenFindAllInteractionsThenReturnListWithPage() {
        // Given : precondition
        Reference reference = new Reference();
        reference.setCode("reference");
        reference.setId(1L);
        Space space = new Space();
        space.setExternalReference(List.of(reference));
        Page<Space> spaces = new PageImpl<>(List.of(space));
        List<Sort.Order> orders = new ArrayList<>();
        Sort.Order code = new Sort.Order(DESC, "code");
        orders.add(code);
        PageRequest pageRequest = PageRequest.of(1, 1,
                Sort.by(List.of(new Sort.Order(DESC, "code"))));
        when(repositoryMock.findByIsActiveIsTrue(pageRequest)).thenReturn(spaces);


        // When : Action of behavious that we are going to test

        PagedModel<InteractionModel> allInteractions = service.getAllInteractions(1, 1, List.of("code"), DESC.toString());


        // Then : verify output

        //   assertThat(allInteractions).isNotNull();
    }

    @Test
    void whenWantRetrieveSpaceDetail_findByIdIntoRepository() {
        UUID uuid = UUID.randomUUID();
        Space space = new Space();
        space.setId(uuid);
        space.setExternalReference(new ArrayList<>());
        space.setExternalReference(List.of(new Reference("sss")));
        when(repositoryMock.findById(any())).thenReturn(Optional.of(space));

        SpaceWithDetail interactionDetailByUuid = service.getInteractionDetailByUuid(uuid);

        assertThat(interactionDetailByUuid).isNotNull();
    }
}