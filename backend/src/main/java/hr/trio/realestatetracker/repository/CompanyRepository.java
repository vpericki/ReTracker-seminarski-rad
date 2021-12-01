package hr.trio.realestatetracker.repository;

import hr.trio.realestatetracker.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
