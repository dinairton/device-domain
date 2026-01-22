package pt.global.device.domain.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.global.device.domain.dto.DeviceDomainDTO;
import pt.global.device.domain.persistence.model.StateEnum;
import pt.global.device.domain.service.DeviceDomainService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/api/device-domain")
@RequiredArgsConstructor
public class DeviceDomainController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceDomainController.class);

    // Service layer that encapsulates the business logic for the "Device Domain".
    private final DeviceDomainService service;

    /**
     * Retrieves details of a specific device by its ID.
     *
     * @param id The unique identifier of the device.
     * @return The DeviceDomainDTO object if found (HTTP 200), otherwise HTTP 404 (Not Found).
     */
    @GetMapping(value="/{id}")
    public ResponseEntity<DeviceDomainDTO> getDeviceDomain(@PathVariable Long id) {
        logger.info("Get device domain with ID: {}", id);
        DeviceDomainDTO dto = service.getDeviceDomainById(id);

        return Optional.ofNullable(dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a list of all devices.
     *
     * @return A list of DeviceDomainDTO objects (HTTP 200).
     */
    @GetMapping
    public ResponseEntity<List<DeviceDomainDTO>> getAll() {
        logger.info("Get all devices domain...");
        return ResponseEntity.ok(service.getAllDeviceDomains());
    }

    /**
     * Retrieves a list of devices filtered by brand.
     *
     * @param brand The brand name used as a filter.
     * @return A list of DeviceDomainDTO objects filtered by the specified brand (HTTP 200).
     */
    @GetMapping(value = "/byBrand")
    public ResponseEntity<List<DeviceDomainDTO>> getAllByBrand(@RequestParam String brand) {
        logger.info("Get all devices domain by brand...");
        return ResponseEntity.ok(service.getAllByBrand(brand));
    }

    /**
     * Retrieves a list of devices filtered by state.
     *
     * @param state The state (enum) used as a filter (e.g., active, inactive).
     * @return A list of DeviceDomainDTO objects filtered by the specified state (HTTP 200).
     */
    @GetMapping(value="/byState")
    public ResponseEntity<List<DeviceDomainDTO>> getAllByState(@RequestParam StateEnum state) {
        logger.info("Get all devices domain by state...");
        return ResponseEntity.ok(service.getAllByState(state));
    }

    /**
     * Updates an existing device by ID with new data.
     *
     * @param id  The unique identifier of the device to update.
     * @param dto A DeviceDomainDTO object with the updated information.
     * @return The updated DeviceDomainDTO object (HTTP 200).
     */
    @PutMapping(value="/{id}")
    public ResponseEntity<DeviceDomainDTO> update(@PathVariable Long id, @RequestBody DeviceDomainDTO dto) {
        logger.info("Update device domain with ID: {}", id);
        return ResponseEntity.ok(service.updateDeviceDomain(id, dto));
    }

    /**
     * Creates a new device in the system.
     *
     * @param dto A DeviceDomainDTO object containing the details of the device to create.
     * @return The newly created DeviceDomainDTO object (HTTP 200).
     */
    @PostMapping
    public ResponseEntity<DeviceDomainDTO> create(@Valid @RequestBody DeviceDomainDTO dto) {
        logger.info("Create device domain....");
        return ResponseEntity.ok(service.createDeviceDomain(dto));
    }

    /**
     * Deletes a specific device by its ID.
     *
     * @param id The unique identifier of the device to delete.
     * @return An HTTP 200 status if the deletion was successful.
     */
    @DeleteMapping(value="/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.info("Delete device domain with ID: {}", id);
        service.deleteDeviceDomain(id);
        return ResponseEntity.ok("Device domain deleted successfully");
    }
}