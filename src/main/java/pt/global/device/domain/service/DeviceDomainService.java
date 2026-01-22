package pt.global.device.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.global.device.domain.dto.DeviceDomainDTO;
import pt.global.device.domain.exception.InvalidStateException;
import pt.global.device.domain.exception.ResourceNotFoundException;
import pt.global.device.domain.persistence.model.DeviceDomain;
import pt.global.device.domain.persistence.model.StateEnum;
import pt.global.device.domain.persistence.repository.DeviceDomainRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DeviceDomainService {

    /**
     * Repository responsible for persistence operations
     * related to Device Domain entities.
     */
    private final DeviceDomainRepository deviceDomainRepository;

    /**
     * Creates a new Device Domain entity in the database.
     *
     * @param dto A {@link DeviceDomainDTO} containing information for the new Device Domain.
     * @return The created {@link DeviceDomainDTO} with the generated ID and creation timestamp.
     */
    public DeviceDomainDTO createDeviceDomain(DeviceDomainDTO dto) {
        DeviceDomain domain = convertToModel(dto);
        domain.setCreationDateTime(LocalDateTime.now());
        return convertToDto(deviceDomainRepository.save(domain));
    }

    /**
     * Updates an existing Device Domain entity by its ID.
     * <p>
     * The update operation is allowed only if the current state of the entity
     * is not {@link StateEnum#IN_USE}. Otherwise, only the state of the Device Domain
     * can be updated.
     * </p>
     *
     * @param id  The unique identifier of the Device Domain to update.
     * @param dto A {@link DeviceDomainDTO} containing the updated details.
     * @return The updated {@link DeviceDomainDTO}.
     * @throws RuntimeException if the device domain is not found or if an attempt is made
     *                          to update fields other than the state while the entity is in use.
     */
    public DeviceDomainDTO updateDeviceDomain(Long id, DeviceDomainDTO dto) {
        DeviceDomain domain = deviceDomainRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Device domain not found"));

        if (domain.getState().equals(StateEnum.IN_USE) && (dto.getName()!=null || dto.getBrand()!=null)) {
            throw new InvalidStateException("Cannot update Name and Brand while device domain is in use");
        }

        if (dto.getName() != null)
            domain.setName(dto.getName());

        if (dto.getBrand() != null)
            domain.setBrand(dto.getBrand());

        if (dto.getState() != null)
           domain.setState(dto.getState());

        deviceDomainRepository.save(domain);
        return convertToDto(domain);
    }

    /**
     * Deletes a Device Domain entity by its ID.
     * <p>
     * A Device Domain can only be deleted if its state is not {@link StateEnum#IN_USE}.
     * Otherwise, a {@link RuntimeException} is thrown.
     * </p>
     *
     * @param id The unique identifier of the Device Domain to delete.
     * @throws RuntimeException if the entity is in use or not found in the database.
     */
    public void deleteDeviceDomain(Long id) {
        DeviceDomain domain = deviceDomainRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Device domain not found"));
        if (domain.getState().equals(StateEnum.IN_USE)) {
            throw new InvalidStateException("Device domain in use");
        }
        deviceDomainRepository.deleteById(id);
    }

    /**
     * Retrieves a Device Domain by its unique ID.
     *
     * @param id The unique identifier of the Device Domain to retrieve.
     * @return A {@link DeviceDomainDTO} representing the retrieved entity, or {@code null} if not found.
     * @throws RuntimeException if the entity is in use or not found in the database.
     */
    public DeviceDomainDTO getDeviceDomainById(Long id) {
        DeviceDomain domain = deviceDomainRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Device domain not found"));
        return convertToDto(domain);
    }

    /**
     * Retrieves all Device Domains from the database.
     *
     * @return A {@link List} of {@link DeviceDomainDTO} representing all Device Domain entities.
     */
    public List<DeviceDomainDTO> getAllDeviceDomains() {
        return deviceDomainRepository.findAll().stream().map(this::convertToDto).toList();
    }

    /**
     * Retrieves Device Domains filtered by a specific brand name.
     *
     * @param brand The name of the brand to filter by.
     * @return A {@link List} of {@link DeviceDomainDTO} representing the filtered Device Domains.
     */
    public List<DeviceDomainDTO> getAllByBrand(String brand) {
        return deviceDomainRepository.findByBrandContaining(brand).stream().map(this::convertToDto).toList();
    }

    /**
     * Retrieves Device Domains filtered by their state.
     *
     * @param state The {@link StateEnum} representing the state to filter by.
     * @return A {@link List} of {@link DeviceDomainDTO} representing the filtered Device Domains.
     */
    public List<DeviceDomainDTO> getAllByState(StateEnum state) {
        return deviceDomainRepository.findByState(state).stream().map(this::convertToDto).toList();
    }

    /**
     * Converts a {@link DeviceDomain} entity to a {@link DeviceDomainDTO}.
     *
     * @param deviceDomain The entity object to be converted.
     * @return A {@link DeviceDomainDTO} containing the mapped fields.
     */
    private DeviceDomainDTO convertToDto(DeviceDomain deviceDomain) {
        return DeviceDomainDTO.builder()
                .id(deviceDomain.getId())
                .name(deviceDomain.getName())
                .brand(deviceDomain.getBrand())
                .state(deviceDomain.getState())
                .creationDateTime(deviceDomain.getCreationDateTime())
                .build();
    }

    /**
     * Converts a {@link DeviceDomainDTO} to a {@link DeviceDomain} entity.
     *
     * @param deviceDomain The DTO object to be converted.
     * @return A {@link DeviceDomain} entity containing the mapped fields.
     */
    private DeviceDomain convertToModel(DeviceDomainDTO deviceDomain) {
        return DeviceDomain.builder()
                .id(deviceDomain.getId())
                .name(deviceDomain.getName())
                .brand(deviceDomain.getBrand())
                .state(deviceDomain.getState())
                .creationDateTime(deviceDomain.getCreationDateTime())
                .build();
    }
}