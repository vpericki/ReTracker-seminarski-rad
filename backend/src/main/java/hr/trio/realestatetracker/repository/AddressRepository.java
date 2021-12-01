package hr.trio.realestatetracker.repository;

import hr.trio.realestatetracker.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
