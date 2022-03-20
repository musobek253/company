package uz.pdp.company.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.company.entity.Worker;
import uz.pdp.company.pyload.ApiResponse;
import uz.pdp.company.pyload.WorkerDto;
import uz.pdp.company.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/worker")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    /**
     * Bu metod workerlani qo'sh ichun controller ctroller orqali servicega cilentdan kelgan malumotlarni berib yuboramiz
     * @param workerDto
     * @return natija qaytadi
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addWorker(@Valid @RequestBody WorkerDto workerDto){
        ApiResponse apiResponse = workerService.addWorker(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    /**
     * worker ma'lumotlarini o'zgartirish uchun metod 200 qaytsa bu o'zgartirilganini va 409
     * qaytsa bu cilent tomonidan xato ma'lumot kiritilganini bildiradi
     * @param id
     * @param workerdto
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editWorker(@Valid @PathVariable Integer id, @RequestBody WorkerDto workerdto) {
        ApiResponse apiResponse = workerService.editWorker(id, workerdto);
        return ResponseEntity.status(apiResponse.isSuccess()? 202:409).body(apiResponse);
    }

    /**
     * workerni ma'lumotlarini o'chirish
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletedWorker(@PathVariable Integer id){
        ApiResponse apiResponse = workerService.deletedWorker(id);
        return ResponseEntity.status(apiResponse.isSuccess()?203:409).body(apiResponse);
    }

    /**
     * bu barcha workelar ro'xatini qaytaradi
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<List<Worker>> getAllWorker(){
        List<Worker> allWorker = workerService.getAllWorker();
        return ResponseEntity.ok(allWorker);
    }

    /**
     * bu bitta ishxonaga ishlaydigan barcha ishchilar ro'xati
     * @param companyId
     * @return
     */
    @GetMapping("/all/company/{companyId}")
    public ResponseEntity<List<Worker>> getByCompanyId(@PathVariable Integer companyId){
        List<Worker> byCompanyId = workerService.getByCompanyId(companyId);
        return ResponseEntity.ok(byCompanyId);
    }


    /**
     * bir xil kasb egalarini ro'xati yani bazadagi barcha bir xil kasb egalari ro'xati buni faqat super Admin ko'ra oladi
     * @param departmntId
     * @return
     */
    @GetMapping("/all/department/{departmntId}")
    public ResponseEntity<?> getByDepartmentId(@PathVariable Integer departmntId){
        List<Worker> byDepartmentId = workerService.getByDepartmentId(departmntId);
        return ResponseEntity.ok(byDepartmentId);
    }
    @GetMapping("/all/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        Worker worker = workerService.getById(id);
        return ResponseEntity.ok(worker);
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
