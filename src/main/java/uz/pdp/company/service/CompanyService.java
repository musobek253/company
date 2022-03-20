package uz.pdp.company.service;

import org.springframework.stereotype.Service;
import uz.pdp.company.entity.Address;
import uz.pdp.company.entity.Company;
import uz.pdp.company.pyload.ApiResponse;
import uz.pdp.company.pyload.CompanyDto;
import uz.pdp.company.repository.AddressRepository;
import uz.pdp.company.repository.CompanyRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;

    public CompanyService(CompanyRepository companyRepository, AddressRepository addressRepository) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
    }

    /**
     * Bu metod commpaniya ma'lumotlar bazasiga qo'shish uchun ishlatiladi
     * @param companyDto
     * @return
     */
    public ApiResponse addCompany(CompanyDto companyDto){
        if (companyRepository.existsAllByCorpName(companyDto.getCorpName()))
            return new ApiResponse("Already exist Company ",false);
        Address address = new Address();
        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());
        Address address1 = addressRepository.save(address);
        Company company = new Company();
        company.setAddress(address1);
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        companyRepository.save(company);
        return new ApiResponse("Succsessfully added",true);
    }

    /**
     * Bu companyi malumotlarini o'zgartish uchun ishlatiladi
     * @param id
     * @param companyDto
     * @return o'zgartirilgan o'zgartirilmagani haqida
     */
    public ApiResponse editCompany(Integer id,CompanyDto companyDto) {
        ApiResponse result;
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent()) {
            result = new ApiResponse("Company not found", false);
        } else {
            Company company = optionalCompany.get();
            Address address = company.getAddress();
            address.setStreet(companyDto.getStreet());
            address.setHomeNumber(companyDto.getHomeNumber());
            Address address1 = addressRepository.save(address);
            company.setDirectorName(companyDto.getDirectorName());
            if (companyRepository.existsAllByCorpNameAndIdNot(companyDto.getCorpName(),id)) {
                result = new ApiResponse("Already Company name", false);
            } else {
                company.setCorpName(companyDto.getCorpName());
                company.setAddress(address1);
                companyRepository.save(company);
                result = new ApiResponse("Etited successfully editd", true);
            }
        }
        return result;
    }

    /**
     * Bu bazdagi barcha companylarini list holatda qaytaradi bu metodan faqat dastur admini ko'rishi mumkin
     * @return list qatadi
     */
    public List<Company> getCompany(){
        return companyRepository.findAll();
    }

    public Company getById(Integer id){
        Optional<Company> byId = companyRepository.findById(id);
        return byId.orElseGet(Company::new);
    }

    /**
     * BU companyani o'chirish uchun ishlatiladi addressi bn birga o'chiradi
     * @param  id
     * @return o'chgan o'chmaganni aytadi
     */
    public ApiResponse deletedCompany(Integer id){

            Optional<Company> optionalCompany = companyRepository.findById(id);
            if (optionalCompany.isPresent()) {
                Company company = optionalCompany.get();
                Address address = company.getAddress();
                addressRepository.deleteById(address.getId());
                companyRepository.deleteById(id);
                return new ApiResponse("Successfully deleted", true);
            }

            return new ApiResponse("Xattolik", false);
        }

}
