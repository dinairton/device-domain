package pt.global.device.domain.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.global.device.domain.persistence.model.DeviceDomain;
import pt.global.device.domain.persistence.model.StateEnum;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceDomainRepository extends JpaRepository <DeviceDomain, Long>{

    Optional<DeviceDomain> findFirstById(Long id);

    List<DeviceDomain> findByBrandContaining(String brand);

    List<DeviceDomain> findByState(StateEnum state);

}
