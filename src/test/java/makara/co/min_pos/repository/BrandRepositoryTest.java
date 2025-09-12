package makara.co.min_pos.repository;

import makara.co.min_pos.models.entities.Brand;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BrandRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void testFindByIdAndIsDeletedFalse() throws Exception{
        // Create and save a Brand entity
        Brand brand = new Brand();
        brand.setName("Test Brand");

        Brand savedBrand = entityManager.persistFlushFind(brand); // Save and flush

        // Act: Fetch the brand from the repository
        Optional<Brand> foundBrand = brandRepository.findByIdAndIsDeletedFalse(savedBrand.getId());

        // Assert: Verify it is correctly retrieved
        assertTrue(foundBrand.isPresent());
        assertEquals("Test Brand", foundBrand.get().getName());

    }

}
