package makara.co.min_pos.service;

import lombok.extern.slf4j.Slf4j;
import makara.co.min_pos.models.entities.Brand;
import makara.co.min_pos.models.response.BrandResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class BrandServiceTest {
    @Mock
    private BrandService brandService;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate(){
        // given
        Brand brand = new Brand();
        brand.setName("IPhone");
        // when
        when(brandService.create(brand)).thenReturn(brand);
        Brand createBrand = brandService.create(brand);
        // then
        assertNotNull(createBrand);
        assertEquals("IPhone",createBrand.getName());
        verify(brandService, times(1)).create(brand);
        log.info("createBrand"+createBrand);

    }

    @Test
    void testGetById(){
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("IPhone");

        when(brandService.getById(1L)).thenReturn(brand);

        Brand foundBrand = brandService.getById(1L);

        assertNotNull(foundBrand);
        assertEquals(1L,foundBrand.getId());
        assertEquals("IPhone",foundBrand.getName());
        verify(brandService,times(1)).getById(1L);
    }

    @Test
    void testUpdate(){
        Brand newBrand = new Brand();
        newBrand.setName("Update Brand");

        Brand updateBrand = new Brand();
        updateBrand.setId(1L);
        updateBrand.setName("Update Brand");

        when(brandService.update(1L,newBrand)).thenReturn(updateBrand);

        Brand result = brandService.update(1L, newBrand);

        assertNotNull(result);
        assertEquals("Update Brand",result.getName());
        verify(brandService, times(1)).update(1L, newBrand);

    }

    @Test
    void testDeleteById() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Nokia");

        when(brandService.deleteById(1L)).thenReturn(brand);

        Brand deleteBrand = brandService.deleteById(1L);

        assertNotNull(deleteBrand);
        assertEquals(1L,deleteBrand.getId());
        verify(brandService, times(1)).deleteById(1L);
    }

    @Test
    void testListAllBrand(){
        BrandResponse brand1 = new BrandResponse(1L, "Brand 1");
        BrandResponse brand2 = new BrandResponse(2L, "Brand 2");

        when(brandService.listAllBrand()).thenReturn(List.of(brand1,brand2));

        List<BrandResponse> brands = brandService.listAllBrand();

        assertNotNull(brands);
        assertEquals(2,brands.size());
        assertEquals("Brand 1",brands.get(0).getName());
        assertEquals("Brand 2",brands.get(1).getName());
        verify(brandService, times(1)).listAllBrand();
    }

    @Test
    void testGetWithPagination(){
        BrandResponse brand1 = new BrandResponse(1L, "Brand 1");
        BrandResponse brand2 = new BrandResponse(2L, "Brand 2");

        Page<BrandResponse> brandPage = new PageImpl<>(List.of(brand1,brand2));

        Map<String ,String> params = Collections.singletonMap("page","0");

        when(brandService.getWithPagination(params)).thenReturn(brandPage);

        Page<BrandResponse> result = brandService.getWithPagination(params);

        assertNotNull(result);
        assertEquals(2,result.getTotalElements());
        assertEquals("Brand 1", result.getContent().get(0).getName());
        assertEquals("Brand 2", result.getContent().get(1).getName());
        verify(brandService, times(1)).getWithPagination(params);


    }

}
