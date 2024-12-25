package iys.customer.employee.interactions.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SpaceTest {

    @Test
    void equalsById() {
        UUID id = UUID.randomUUID();
        Space entity = new Space();
        entity.setId(id);
        Space sameEntity = new Space();
        sameEntity.setId(id);
        Space differentEntity = new Space();
        differentEntity.setId(UUID.randomUUID());

        assertThat(entity).isEqualTo(entity);
        assertThat(entity).isEqualTo(sameEntity);
        assertThat(entity.hashCode()).isEqualTo(sameEntity.hashCode());
        assertThat(entity.hashCode()).isNotEqualTo(differentEntity.hashCode());
        assertThat(sameEntity.hashCode()).isNotEqualTo(differentEntity.hashCode());
        assertThat(entity.hashCode()).isNotEqualTo(new AnotherClass());
        assertThat(entity).isNotEqualTo(differentEntity);
        assertThat(sameEntity).isNotEqualTo(differentEntity);
        assertThat(entity).isNotEqualTo(null);
        assertThat(entity).isNotEqualTo(new AnotherClass());
    }


    @ParameterizedTest
    @EnumSource(value = SpaceTestCaseEnm.class,
            names = {"ACTIVE_NOT_BILLS", "INACTIVE_NOT_WAITING_BILLS",
                    "ACTIVE_WAITING_BILLS", "INACTIVE_WAITING_BILLS"})
    void spaces(SpaceTestCaseEnm spaceCase) {

        System.out.println(spaceCase);
    }

    void addReference() {
        Space space = new Space();

        space.addReference("a");


    }

    @Test
    void removeReference() {
    }

    @Test
    void removeThisSpace() {
    }

    @Test
    void cancelOrder() {
    }

    @Test
    void bills() {
    }

    @Test
    void cancelBills() {
    }

    @Test
    void uuidGenerator() {
        System.out.println(UUID.randomUUID());
    }

    private enum SpaceTestCaseEnm {
        ACTIVE_NOT_BILLS,
        INACTIVE_NOT_WAITING_BILLS,
        ACTIVE_WAITING_BILLS,
        INACTIVE_WAITING_BILLS
    }

    public static class AnotherClass {
    }


}