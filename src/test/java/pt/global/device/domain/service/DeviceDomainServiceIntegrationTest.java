package pt.global.device.domain.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pt.global.device.domain.dto.DeviceDomainDTO;
import pt.global.device.domain.persistence.model.StateEnum;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class DeviceDomainServiceIntegrationTest {

    @Autowired
    private DeviceDomainService service;

    @Test
    @Order(1)
    void findAllDevices() {
        assertEquals(1, service.getAllDeviceDomains().size());
    }


    @Test
    @Order(2)
    void createDeviceDomain() {
        DeviceDomainDTO domain = DeviceDomainDTO.builder().name("Test Device").brand("Test Brand").state(StateEnum.AVAILABLE).build();

        service.createDeviceDomain(domain);

        DeviceDomainDTO result = service.getAllDeviceDomains().stream().filter(x -> x.getName().equals("Test Device")).findFirst().orElse(null);

        assertNotNull(result);
    }

    @Test
    @Order(3)
    void updateDeviceDomain() {
        DeviceDomainDTO domain = service.getDeviceDomainById(100L);

        domain.setName("Test Device Updated");

        service.updateDeviceDomain(100L, domain);

        DeviceDomainDTO result = service.getAllDeviceDomains().stream().filter(x -> x.getName().equals("Test Device Updated")).findFirst().orElse(null);

        assertNotNull(result);
    }

    @Test
    @Order(4)
    void findByBrand() {
        assertEquals(1, service.getAllByBrand("brand 1").size());
    }

    @Test
    @Order(5)
    void findByState() {
        assertEquals(2, service.getAllByState(StateEnum.AVAILABLE).size());
    }


    @Test
    @Order(6)
    void deleteDeviceDomain() {
        service.deleteDeviceDomain(100L);

        assertThrows(RuntimeException.class, () -> service.getDeviceDomainById(100L));

    }

}
