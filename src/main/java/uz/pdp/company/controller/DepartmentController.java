package uz.pdp.company.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.company.entity.Department;
import uz.pdp.company.pyload.ApiResponse;
import uz.pdp.company.pyload.DepartmentDto;
import uz.pdp.company.service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addDepartment(@Valid @RequestBody DepartmentDto departmentDto){
         ApiResponse apiResponse = departmentService.addDepartment(departmentDto);
         return ResponseEntity.status(apiResponse.isSuccess() ? 201:409).body(apiResponse);
    }

    /**
     * bu controller yo'li departmentlarni o'zgartirish uchun xizmat qiladi
     * @param id
     * @param departmentDto bu cilent tarafidan yubariladigan ma'lumot
     * @return bu esa dastur qaytaradiga javob  yani o'zgartirildi yoki yo'q
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> editDepartment(@Valid @PathVariable Integer id,@RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.editDepartment(id, departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    /**
     * bu id buyicha departmini chaqirish
     * @param id
     * @return natija bizga departmnt qaytadi
     */
    @GetMapping("/all/{id}")
    public ResponseEntity<Department> getbyid(@PathVariable Integer id){
        Department department = departmentService.getById(id);
        return ResponseEntity.ok(department);
    }

    /**
     * bu metod orqali companyaga tegishli departminlarni listini qaytaradi
     * @param companyId
     * @return
     */
    @GetMapping("/all/company/{companyId}")
    public ResponseEntity<?> getByCompanyId(@PathVariable Integer companyId){
        List<Department> departments = departmentService.GetCompanyDepartment(companyId);
        return ResponseEntity.ok(departments);
    }

    /**
     * mu metod barcha departminlar listini qaytradi bu faqat dastur bosh adminga ko'rsatilsin
     * @return departminlar listi qaytadi
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllDepartment(){
        List<Department> allDepartment = departmentService.getAllDepartment();
        return ResponseEntity.ok(allDepartment);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeletedDepartment(@PathVariable Integer id){
        ApiResponse apiResponse = departmentService.deletedDepartment(id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:500).body(apiResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
