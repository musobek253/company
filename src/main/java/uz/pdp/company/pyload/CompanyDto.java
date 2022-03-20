package uz.pdp.company.pyload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    @NotNull(message = "Companiya nomini kiriting")
    private String corpName;
    @NotNull(message = "Directorni kiriting")
    private String directorName;
    @NotNull(message = "Ko'changiz nomini kiriting")
    private String street;
    @NotNull(message = "uy nomeringizni kiriting")
    private String homeNumber;
}
