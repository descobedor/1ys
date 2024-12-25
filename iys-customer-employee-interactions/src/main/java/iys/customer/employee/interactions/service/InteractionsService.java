package iys.customer.employee.interactions.service;

import iys.customer.employee.interactions.assembler.InteractionAssemblerModel;
import iys.customer.employee.interactions.assembler.InteractionModel;
import iys.customer.employee.interactions.common.exception.InteractionNotFoundException;
import iys.customer.employee.interactions.common.exception.ReferenceDuplicatedException;
import iys.customer.employee.interactions.common.exception.SpaceNotCreatedException;
import iys.customer.employee.interactions.entity.Space;
import iys.customer.employee.interactions.mapper.Space2SpaceWithDetailMapper;
import iys.customer.employee.interactions.model.CustomerSpace;
import iys.customer.employee.interactions.model.SpaceCreated;
import iys.customer.employee.interactions.model.SpaceWithDetail;
import iys.customer.employee.interactions.repository.ReferenceRepository;
import iys.customer.employee.interactions.repository.SpaceRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InteractionsService {

    private final SpaceRepository repository;

    private final ReferenceRepository referenceRepository;

    private final PagedResourcesAssembler<Space> pagedResourcesAssembler;

    private final InteractionAssemblerModel interactionAssemblerModel;

    public InteractionsService(SpaceRepository repository, ReferenceRepository referenceRepository, PagedResourcesAssembler<Space> pagedResourcesAssembler, InteractionAssemblerModel interactionAssemblerModel) {
        this.repository = repository;
        this.referenceRepository = referenceRepository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.interactionAssemblerModel = interactionAssemblerModel;
    }

    public SpaceCreated createNewCustomerSpace(CustomerSpace customerSpace) {
        SpaceCreated spaceCreated;
        try {
            spaceCreated = new SpaceCreated(repository.save(new Space(customerSpace)));
        } catch (Exception e) {
            spaceCreated = new SpaceCreated(repository.save(new Space()));
        }
        return spaceCreated;
    }

    public PagedModel<InteractionModel> getAllInteractions(int page, int size, List<String> sortList, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(interactionAssemblerModel.createSortOrder(sortList, sortOrder)));
        return pagedResourcesAssembler
                .toModel(repository.findByIsActiveIsTrue(pageable), interactionAssemblerModel);
    }

    public SpaceWithDetail getInteractionDetailByUuid(UUID uuidSite) {
        return repository.findById(uuidSite)
                .map(space -> Space2SpaceWithDetailMapper.INSTANCE.get()
                        .toSpaceWithDetail(space))
                .orElseThrow(InteractionNotFoundException::new);
    }

    public void createOrder(UUID space) {
        repository.findById(space)
                .map(spaceSite -> {
                    spaceSite.order();
                    repository.save(spaceSite);
                    return spaceSite;
                }).orElseThrow(InteractionNotFoundException::new);
    }

    public void removeOrder(UUID space) {
        repository.findById(space)
                .map(spaceSite -> {
                    spaceSite.cancelOrder();
                    repository.save(spaceSite);
                    return spaceSite;
                }).orElseThrow(InteractionNotFoundException::new);
    }

    public void addNewReferenceIntoSpace(UUID space, CustomerSpace customerSpace) {
        repository.findById(space)
                .map(spaceSite -> {
                    spaceSite.addReference(customerSpace.getReferenceCode());
                    try {
                        repository.save(spaceSite);
                    } catch (Exception e) {
                        throw new ReferenceDuplicatedException("reference " + customerSpace.getReferenceCode() + " duplicated");
                    }
                    return spaceSite;
                }).orElseThrow(InteractionNotFoundException::new);
    }

    @Transactional
    public void removeReferenceIntoSpace(UUID space, String reference) {
        repository.findById(space)
                .map(spaceSite -> {
                    spaceSite.removeReference(reference);
                    referenceRepository.deleteByCode(reference);
                    repository.save(spaceSite);
                    return spaceSite;
                })
                .orElseThrow(() -> new SpaceNotCreatedException("space inactive"));
    }

    public void createBills(UUID space) {
        repository.findById(space)
                .map(spaceSite -> {
                    spaceSite.bills();
                    repository.save(spaceSite);
                    return spaceSite;
                })
                .orElseThrow(() -> new SpaceNotCreatedException("space inactive"));
    }

    public void deleteSpaceSite(UUID space) {
        repository.findById(space)
                .map(spaceSite -> {
                    spaceSite.removeThisSpace();
                    repository.save(spaceSite);
                    return spaceSite;
                })
                .orElseThrow(() -> new SpaceNotCreatedException("space inactive"));
    }

    public void cancelBills(UUID space) {
        repository.findById(space)
                .map(spaceSite -> {
                    spaceSite.cancelBills();
                    repository.save(spaceSite);
                    return spaceSite;
                })
                .orElseThrow(() -> new SpaceNotCreatedException("space inactive"));
    }

}
