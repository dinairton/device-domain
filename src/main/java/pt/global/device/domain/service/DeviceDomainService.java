package pt.global.device.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.global.device.domain.dto.DeviceDomainDTO;
import pt.global.device.domain.persistence.model.DeviceDomain;
import pt.global.device.domain.persistence.model.StateEnum;
import pt.global.device.domain.persistence.repository.DeviceDomainRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceDomainService {
    private final DeviceDomainRepository deviceDomainRepository;

    public DeviceDomainDTO createDeviceDomain(DeviceDomainDTO deviceDomain){
        DeviceDomain domain = convertToModel(deviceDomain);
        domain.setCreationDateTime(LocalDateTime.now());
        return convertToDto(deviceDomainRepository.save(domain));
    }

    public DeviceDomainDTO updateDeviceDomain(Long id, DeviceDomainDTO dto){
        DeviceDomain domain = deviceDomainRepository.findFirstById(id).orElseThrow(() -> new RuntimeException("Device domain not found"));
        if (!domain.getState().equals(StateEnum.IN_USE)) {
            domain.setName(dto.getName());
            domain.setBrand(dto.getBrand());
        }
        domain.setState(dto.getState());
        deviceDomainRepository.save(domain);
        return convertToDto(domain);
    }

    public void deleteDeviceDomain(Long id){
        DeviceDomain domain = deviceDomainRepository.findFirstById(id).orElseThrow(() -> new RuntimeException("Device domain not found"));
        if (domain.getState().equals(StateEnum.IN_USE)) {
            throw new RuntimeException("Device domain in use");
        }
        deviceDomainRepository.deleteById(id);
    }

    public DeviceDomainDTO getDeviceDomainById(Long id){
        Optional<DeviceDomain> opt = deviceDomainRepository.findById(id);

        return opt.map(this::convertToDto).orElse(null);
    }

    public List<DeviceDomainDTO> getAllDeviceDomains(){
        return deviceDomainRepository.findAll().stream().map(this::convertToDto).toList();
    }

    public List<DeviceDomainDTO> getAllByBrand(String brand){
        return deviceDomainRepository.findByBrandContaining(brand).stream().map(this::convertToDto).toList();
    }

    public List<DeviceDomainDTO> getAllByState(StateEnum state){
        return deviceDomainRepository.findByState(state).stream().map(this::convertToDto).toList();
    }

    private DeviceDomainDTO convertToDto(DeviceDomain deviceDomain) {
        return DeviceDomainDTO.builder()
                .id(deviceDomain.getId())
                .name(deviceDomain.getName())
                .brand(deviceDomain.getBrand())
                .state(deviceDomain.getState())
                .creationDateTime(deviceDomain.getCreationDateTime())
                .build();
    }

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
