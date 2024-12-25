package iys.customer.employee.interactions.controller;

import com.google.zxing.WriterException;
import iys.customer.employee.interactions.assembler.InteractionModel;
import iys.customer.employee.interactions.controller.validator.UUIDFormatValidation;
import iys.customer.employee.interactions.model.CustomerSpace;
import iys.customer.employee.interactions.model.SpaceCreated;
import iys.customer.employee.interactions.model.SpaceWithDetail;
import iys.customer.employee.interactions.service.InteractionsService;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static iys.customer.employee.interactions.common.QRHelper.getQRCodeImage;

@RestController
@RequestMapping(value = "/interactions")
public class InteractionsController {

    private static final String X_SPACE_ID_CONST = "X-Space-Id";
    private static final String NUMBER_PAGE_DEFAULT = "0";
    private static final String NUMBER_SIZE_PAGE_DEFAULT = "30";
    private static final String FIELD_SORT_EMPTY_DEFAULT = "";
    private static final String SORT_DIRECTION_DEFAULT = "DESC";

    private final InteractionsService service;

    public InteractionsController(InteractionsService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<byte[]> createNewCustomerSpace(@RequestBody CustomerSpace customerSpace) throws IOException, WriterException {
        SpaceCreated newCustomerSpace = service.createNewCustomerSpace(customerSpace);
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_SPACE_ID_CONST, newCustomerSpace.space().getId().toString());
        return ResponseEntity
                .ok().headers(headers)
                .body(getQRCodeImage(newCustomerSpace.space().getId().toString(), 250, 250));
    }

    @GetMapping
    public ResponseEntity<PagedModel<InteractionModel>> getAllCustomerSpaceWithDetails(
            @RequestParam(defaultValue = NUMBER_PAGE_DEFAULT) int page,
            @RequestParam(defaultValue = NUMBER_SIZE_PAGE_DEFAULT) int size,
            @RequestParam(defaultValue = FIELD_SORT_EMPTY_DEFAULT) List<String> sortList,
            @RequestParam(defaultValue = SORT_DIRECTION_DEFAULT) Sort.Direction sortOrder) {
        return ResponseEntity
                .ok(service.getAllInteractions(page, size, sortList, sortOrder.toString()));
    }

    @GetMapping(value = "/{code}")
    public ResponseEntity<SpaceWithDetail> getCustomerSpaceWithDetailByUUID(@UUIDFormatValidation @PathVariable String code) {
        return ResponseEntity
                .ok(service.getInteractionDetailByUuid(UUID.fromString(code)));
    }

    @PutMapping(value = "/{code}/orders")
    public ResponseEntity<Void> createOrder(@UUIDFormatValidation @PathVariable String code) {
        service.createOrder(UUID.fromString(code));
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping(value = "/{code}/orders")
    public ResponseEntity<Void> deleteOrder(@UUIDFormatValidation @PathVariable String code) {
        service.removeOrder(UUID.fromString(code));
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping(value = "/{code}/orders/{reference}")
    public ResponseEntity<Void> deleteOrderByReference(
            @UUIDFormatValidation @PathVariable String code,
            @UUIDFormatValidation @PathVariable String reference) {
        return ResponseEntity.accepted().build();
    }

    @PutMapping(value = "/{code}")
    public ResponseEntity<Void> addReferenceIntoSpace(
            @UUIDFormatValidation @PathVariable String code,
            @RequestBody CustomerSpace customerSpace) {
        service.addNewReferenceIntoSpace(UUID.fromString(code), customerSpace);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping(value = "/{code}/references/{reference}")
    public ResponseEntity<Void> removeReferenceIntoSpace(
            @UUIDFormatValidation @PathVariable String code,
            @PathVariable String reference) {
        service.removeReferenceIntoSpace(UUID.fromString(code), reference);
        return ResponseEntity.accepted().build();
    }

    @PostMapping(value = "/{code}/bills")
    public ResponseEntity<Void> bills(
            @UUIDFormatValidation @PathVariable String code) {
        service.createBills(UUID.fromString(code));
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping(value = "/{code}/bills")
    public ResponseEntity<Void> cancelBills(
            @UUIDFormatValidation @PathVariable String code) {
        service.cancelBills(UUID.fromString(code));
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping(value = "/{code}")
    public ResponseEntity<Void> deleteSpace(
            @UUIDFormatValidation @PathVariable String code) {
        service.deleteSpaceSite(UUID.fromString(code));
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/geos")
    public ResponseEntity<String> siHayQuienLoSepa() {
        return ResponseEntity.ok("TONTO QUIEN LO LEA");
    }

}
