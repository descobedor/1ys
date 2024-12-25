package iys.customer.employee.interactions.controller;

import com.google.zxing.WriterException;
import iys.customer.employee.interactions.assembler.InteractionModel;
import iys.customer.employee.interactions.entity.Space;
import iys.customer.employee.interactions.model.CustomerSpace;
import iys.customer.employee.interactions.model.SpaceCreated;
import iys.customer.employee.interactions.model.SpaceWithDetail;
import iys.customer.employee.interactions.service.InteractionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.LENIENT;
import static org.springframework.data.domain.Sort.Direction.DESC;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class InteractionsControllerTest {

    InteractionsController interactionsController;

    @Mock
    InteractionsService interactionsServiceMock;

    @BeforeEach
    void setUp() {
        interactionsController = new InteractionsController(interactionsServiceMock);
    }

    @Test
    void givenWeHaveACustomerOnPlaceForToBeServed_WhenEmployeeWaiteOrWaitressWantsStartToServiceThem_ThenWeStoredThePlaceIntoSpaceTable() throws IOException, WriterException {
        //GIVEN : precondition

        Space space = new Space();
        space.setId(UUID.randomUUID());
        when(interactionsServiceMock.createNewCustomerSpace(any())).thenReturn(new SpaceCreated(space));

        // WHEN : action for behaviour


        var newCustomerSpace = interactionsController.createNewCustomerSpace(new CustomerSpace());

        // THEN : assert condition

        assertThat(newCustomerSpace).isNotNull();

    }

    @Test
    void getAllCustomerSpaceWithDetails() {
        //GIVEN
        String referenceCode = "5TT2M";
        String code = UUID.randomUUID().toString();
        SpaceWithDetail spaceWithCodeRes =
                new SpaceWithDetail(code, List.of(referenceCode));
        InteractionModel interactionModel = new InteractionModel();
        interactionModel.setReferenceCode(spaceWithCodeRes.reference());
        interactionModel.setCode(referenceCode);

        PagedModel<InteractionModel> representationModel =
                PagedModel.of(List.of(interactionModel),
                        new PagedModel.PageMetadata(1, 1, 1, 1));
        when(interactionsServiceMock.getAllInteractions(1, 1, List.of(""), DESC.toString()))
                .thenReturn(representationModel);


        //WHEN
        ResponseEntity<PagedModel<InteractionModel>> actual =
                interactionsController.getAllCustomerSpaceWithDetails(1, 1, List.of(""), DESC);


        //THEN
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody().getContent()).isNotNull();
        assertThat(actual.getBody().getContent().iterator()).isNotNull();
    }

    @Test
    void givenACoupleOfRegisteredSites_whenWaitressOrWaiterGetDetails_thenReturnDetailsForSpaceConcrete() {
        when(interactionsServiceMock.getInteractionDetailByUuid(any())).thenReturn(new SpaceWithDetail("dsads",
                new ArrayList<>()));

        ResponseEntity<SpaceWithDetail> space =
                interactionsController.getCustomerSpaceWithDetailByUUID(UUID.randomUUID().toString());

        assertThat(space).isNotNull();
        assertThat(space.getBody()).isNotNull();
        assertThat(space.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

    }


    @Test
    void givenCreateSpace_whenCustomerWantsOrder_thenCreateAnOrder() {
        ResponseEntity<Void> order = interactionsController.createOrder(UUID.randomUUID().toString());

        assertThat(order.getStatusCode().is2xxSuccessful()).isTrue();
    }
}