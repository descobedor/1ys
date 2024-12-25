package iys.customer.employee.interactions.assembler;

import iys.customer.employee.interactions.entity.Reference;
import iys.customer.employee.interactions.entity.Space;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InteractionAssemblerModel implements RepresentationModelAssembler<Space, InteractionModel> {

    @Override
    public InteractionModel toModel(Space entity) {
        InteractionModel interactionModel = new InteractionModel();
        interactionModel.setCode(entity.getId().toString());
        interactionModel.setReferenceCode(entity
                .getExternalReference().stream().map(Reference::getCode).toList());
        interactionModel.setWaitingForEmployee(entity.isOrder());
        interactionModel.setNumInteractions(entity.getOrderCount());
        interactionModel.setBills(entity.isWaitingBills());
        interactionModel.setNumBills(entity.getBillsCount());
        return interactionModel;
    }

    public List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (!sort.isEmpty()) {
                if (sortDirection != null) {
                    direction = Sort.Direction.fromString(sortDirection);
                } else {
                    direction = Sort.Direction.DESC;
                }
                sorts.add(new Sort.Order(direction, sort));
            }
        }
        return sorts;
    }

}
