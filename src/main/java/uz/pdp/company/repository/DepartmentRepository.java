package uz.pdp.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.company.entity.Department;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    boolean existsAllByNameAndCompanyId(String name, Integer company_id);
    boolean existsAllByNameAndCompanyIdAndIdNot(String name, Integer company_id, Integer id);

    List<Department>  getAllByCompanyId(Integer company_id);
}
