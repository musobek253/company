package uz.pdp.company.service;

import org.springframework.stereotype.Service;
import uz.pdp.company.entity.Address;
import uz.pdp.company.entity.Department;
import uz.pdp.company.entity.Worker;
import uz.pdp.company.pyload.ApiResponse;
import uz.pdp.company.pyload.WorkerDto;
import uz.pdp.company.repository.AddressRepository;
import uz.pdp.company.repository.DepartmentRepository;
import uz.pdp.company.repository.WokerReposirory;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    private final WokerReposirory wokerReposirory;
    private final DepartmentRepository departmentRepository;
    private final AddressRepository addressRepository;

    public WorkerService(WokerReposirory wokerReposirory, DepartmentRepository departmentRepository, AddressRepository addressRepository) {
        this.wokerReposirory = wokerReposirory;
        this.departmentRepository = departmentRepository;
        this.addressRepository = addressRepository;
    }

    /**
     * bu workerni bazaga qo'shish uchun service metodi
     * @param workerDto workerdto bu cilent tomonida keladigan ma'lumotlar
     * @return
     */
    public ApiResponse addWorker(WorkerDto workerDto) {
        if (wokerReposirory.existsAllByPhoneNumber(workerDto.getPhoneNumber()))
            return new ApiResponse("Already exist PhoneNumber",false);
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Department not found",false);
        Address address = new Address();
        address.setHomeNumber(workerDto.getHomeNumber());
        address.setStreet(workerDto.getStreet());
        Address address1 = addressRepository.save(address);
        Worker worker = new Worker();
        worker.setDepartment(optionalDepartment.get());
        worker.setAddress(address1);
        worker.setFirstName(workerDto.getFirstName());
        worker.setLastName(workerDto.getLastName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        wokerReposirory.save(worker);
        return new ApiResponse("Succsessfully added",true);
    }

    public ApiResponse editWorker(Integer id, WorkerDto workerdto) {
        Optional<Worker> optionalWorker = wokerReposirory.findById(id);
        if (!optionalWorker.isPresent())
            return new ApiResponse("Worker not found",false);
        Optional<Department> optionalDepartment = departmentRepository.findById(workerdto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Department not found",false);
        if (wokerReposirory.existsAllByPhoneNumberAndIdNot(workerdto.getPhoneNumber(), id))
            return new ApiResponse("Already exist phoneNumber",false);
        Worker worker = optionalWorker.get();
        Address address = worker.getAddress();
        if (!addressRepository.findById(address.getId()).isPresent())
            return new ApiResponse("Address not found",false);
        address.setStreet(workerdto.getStreet());
        address.setHomeNumber(workerdto.getHomeNumber());
        Address address1 = addressRepository.save(address);
        worker.setPhoneNumber(workerdto.getPhoneNumber());
        worker.setAddress(address1);
        worker.setLastName(workerdto.getLastName());
        worker.setDepartment(optionalDepartment.get());
        worker.setFirstName(workerdto.getFirstName());
        wokerReposirory.save(worker);
        return new ApiResponse("Succsessfully edited",true);
    }

    /**
     * worker ma'lumotlarini o'chirish  buy yerda adders ma'lumotlari ham o'chadi
     * @param id
     * @return
     */
    public ApiResponse deletedWorker(Integer id){
        Optional<Worker> optionalWorker = wokerReposirory.findById(id);
        if (optionalWorker.isPresent()){
            Worker worker = optionalWorker.get();
            Address address = worker.getAddress();
            addressRepository.deleteById(address.getId());
            wokerReposirory.deleteById(id);
            return new ApiResponse("Successfully deleted",true);
        }
        return new ApiResponse("Error",false);
    }

    /**
     * bu metod bitta companyni ishchilarini list qilib qaytaradi
     * @param companyId
     * @return
     */
    public List<Worker> getByCompanyId(Integer companyId){
        return wokerReposirory.getAllByDepartment_CompanyId(companyId);
    }

    /**
     * bu metod bir xil departminlar ro'xatini beradi
     * @param departmentId
     * @return
     */
    public List<Worker> getByDepartmentId(Integer departmentId){
        return wokerReposirory.getAllByDepartmentId(departmentId);
    }

    /**
     * bu barcha workerlar ro'xatini beradi bu faqat super adminlar ko'rishi mumkin
     * @return
     */
    public List<Worker>getAllWorker(){
        return wokerReposirory.findAll();
    }

    /**
     * bu bitta workermi id buyicha chiqaradi
     * @param id
     * @return
     */
    public Worker getById(Integer id){
        Optional<Worker> optionalWorker = wokerReposirory.findById(id);
        return optionalWorker.orElseGet(Worker::new);
    }
}
