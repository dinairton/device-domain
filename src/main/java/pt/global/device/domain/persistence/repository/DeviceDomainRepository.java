package pt.global.device.domain.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.global.device.domain.persistence.model.DeviceDomain;
import pt.global.device.domain.persistence.model.StateEnum;

import java.util.List;

@Repository
public interface DeviceDomainRepository extends JpaRepository <DeviceDomain, Long>{

    List<DeviceDomain> findByBrandContaining(String brand);

    List<DeviceDomain> findByState(StateEnum state);

}
