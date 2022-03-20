package uz.pdp.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.company.entity.Worker;

import java.util.List;

@Repository
public interface WokerReposirory extends JpaRepository<Worker,Integer> {
    boolean existsAllByPhoneNumber(String phoneNumber);
    boolean existsAllByPhoneNumberAndIdNot(String phoneNumber, Integer id);

    List<Worker> getAllByDepartment_CompanyId(Integer department_company_id);
    List<Worker> getAllByDepartmentId(Integer department_id);
}
