package uz.pdp.company.pyload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.company.entity.Company;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentDto {
    @NotNull(message = "Department name kiriting")
    private String name;
    @NotNull(message = "Companiyani tanlang ")
    private Integer companyId;
}
