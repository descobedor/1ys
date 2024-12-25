package iys.customer.employee.interactions.repository;

import iys.customer.employee.interactions.entity.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SpaceRepositoryTest {

    @Autowired
    SpaceRepository spaceRepository;

    Space space;

    @BeforeEach
    void setUp() {
        // Given : Setup object or precondition
        space = new Space();
    }

    @Test
    void givenSpaceObject_whenSave_thenReturnSaveSpace() {

        // When : Action of behavious that we are going to test

        Space saveSpace = spaceRepository.save(space);

        // Then : Verify the output

        assertThat(saveSpace).isNotNull();
        assertThat(saveSpace.getId()).isNotNull();
    }

    @Test
    void givenSpaceList_whenFindAll_thenSpaceList() {
        // Given : Setup object or precondition

        Space spaceOne = new Space();
        Space spaceTwo = new Space();
        spaceRepository.save(spaceOne);
        spaceRepository.save(spaceTwo);

        // When : Action of behavious that we are going to test

        Page<Space> all = spaceRepository.findAll(Pageable.ofSize(2));

        // Then : Verify the output

        all.stream().forEach(System.out::println);
    }
}