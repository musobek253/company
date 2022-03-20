package uz.pdp.company.service;

import org.springframework.stereotype.Service;
import uz.pdp.company.entity.Company;
import uz.pdp.company.entity.Department;
import uz.pdp.company.pyload.ApiResponse;
import uz.pdp.company.pyload.DepartmentDto;
import uz.pdp.company.repository.CompanyRepository;
import uz.pdp.company.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentrepository;
    private final CompanyRepository companyRepository;

    public DepartmentService(DepartmentRepository departmentrepository, CompanyRepository companyRepository) {
        this.departmentrepository = departmentrepository;
        this.companyRepository = companyRepository;
    }

    /**
     * Bu metod  bazaga department qo'shish uchun
     * @param departmentDto
     * @return
     */
    public ApiResponse addDepartment(DepartmentDto departmentDto) {
        if (departmentrepository.existsAllByNameAndCompanyId(departmentDto.getName(),departmentDto.getCompanyId()))
            return new ApiResponse("Already exist Department",false);
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Company Not found",false);
        Department department  =new Department();
        department.setCompany(optionalCompany.get());
        department.setName(departmentDto.getName());
        departmentrepository.save(department);
        return new ApiResponse("Successfully Created",true);
    }

    /**
     * Bu metod demartment ma'lumotlarini o'zgartirish uchun
     * @param id
     * @param departmentDto
     * @return
     */
    public ApiResponse editDepartment(Integer id, DepartmentDto departmentDto){
        Optional<Department> departmentOptional = departmentrepository.findById(id);
        if (!departmentOptional.isPresent())
            return new ApiResponse("Department not found",false);
        if (departmentrepository.existsAllByNameAndCompanyIdAndIdNot(departmentDto.getName(),departmentDto.getCompanyId(),id))
            return new ApiResponse("Already exist Department name",false);
        Department department = departmentOptional.get();
        department.setName(departmentDto.getName());
        department.setCompany(companyRepository.findById(departmentDto.getCompanyId()).get());
        departmentrepository.save(department);
        return new ApiResponse("Succsessfully edited",true);
    }

    /**
     * Bu departmentlarni bazadan o'chirish
     * @param id
     * @return
     */
    public ApiResponse deletedDepartment(Integer id){
        Optional<Department> optionalDepartment = departmentrepository.findById(id);
        if (optionalDepartment.isPresent()){
            departmentrepository.deleteById(id);
            return new ApiResponse("Succsessfully deleted",true);
        }
        return new ApiResponse("Error!!!!!",false);
    }

    /**
     * bitta departmentni qaytarish id buyich
     * @param id
     * @return
     */
    public Department getById(Integer id){
        Optional<Department> byId = departmentrepository.findById(id);
        return byId.orElseGet(Department::new);
    }

    /**
     * barcha departminlarin chiqaradigan
     * @return
     */
    public List<Department> getAllDepartment(){
        return departmentrepository.findAll();
    }

    /**
     * bu metod bitta companiyaga tegishli departmentlar ro'xati companiya idsi buyicha chiqaradi
     * @param companyId
     * @return
     */
    public List<Department> GetCompanyDepartment(Integer companyId){
        return departmentrepository.getAllByCompanyId(companyId);
    }
}
