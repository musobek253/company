package uz.pdp.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.company.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {

    boolean existsAllByStreetAndHomeNumber(String street, String homeNumber);
}
