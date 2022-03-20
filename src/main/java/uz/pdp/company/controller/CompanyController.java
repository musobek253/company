package uz.pdp.company.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.company.entity.Company;
import uz.pdp.company.pyload.ApiResponse;
import uz.pdp.company.pyload.CompanyDto;
import uz.pdp.company.service.CompanyService;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    @PostMapping("/add")
    public ResponseEntity<?> addCompany(@Valid @RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.addCompany(companyDto);
        return  ResponseEntity.status(apiResponse.isSuccess()? 201:409).body(apiResponse);
    }

    /**
     * Bu yo'l  bazada mavjud company ma'lumotlarini o'zgartirish uchun ishlatiladi
     * @param id
     * @param companyDto
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?>editCompany(@Valid @PathVariable Integer id,@RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.editCompany(id, companyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    /**
     * Bu metod barcha companyni qaytaradigan  controller
     * @return list qaytadi
     */
    @GetMapping("/all")
    public ResponseEntity<List<Company>> getallCompany(){
        List<Company> company = companyService.getCompany();
        return ResponseEntity.ok(company);
    }

    /**
     * bu id buyicha company qaytaradi
     * @param id
     * @return
     */
    @GetMapping("/all/{id}")
    public ResponseEntity<Company> getById(@PathVariable Integer id){
        Company company = companyService.getById(id);
        return ResponseEntity.ok(company);
    }

    /**
     * Bu metod id buyich companylarni uchiradi
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletedCompany(@PathVariable Integer id){
        ApiResponse apiResponse = companyService.deletedCompany(id);
        return ResponseEntity.status(apiResponse.isSuccess()?NO_CONTENT:BAD_REQUEST).body(apiResponse);
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

    /**
     * bu yerda valid qilish uchun  yozilga metod  siz yozga messgeni qaytaradi
     * @param ex
     * @return
     */

}
