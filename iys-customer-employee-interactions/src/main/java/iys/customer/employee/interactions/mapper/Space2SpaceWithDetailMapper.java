package iys.customer.employee.interactions.mapper;

import iys.customer.employee.interactions.entity.Reference;
import iys.customer.employee.interactions.entity.Space;
import iys.customer.employee.interactions.model.SpaceWithDetail;

import java.util.function.Supplier;

public final class Space2SpaceWithDetailMapper {

    public static Supplier<Space2SpaceWithDetailMapper> INSTANCE = Space2SpaceWithDetailMapper::new;

    private Space2SpaceWithDetailMapper() {
    }

    public SpaceWithDetail toSpaceWithDetail(Space space) {
        return new SpaceWithDetail(space.getId().toString(),
                space.getExternalReference().stream().map(Reference::getCode).toList());
    }
}
