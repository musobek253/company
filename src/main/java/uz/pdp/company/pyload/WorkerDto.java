package uz.pdp.company.pyload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto {
    @NotNull(message = "LastNamenni kiriting")
    private String lastName;
    @NotNull(message = "fristName kiriting")
    private String firstName;
    @NotNull(message = "PhoneNumberni kiriting")
    private String phoneNumber;
    @NotNull(message = "departmin Id kiriting")
    private Integer departmentId;
    @NotNull(message = "ko'cha nomini kiriting")
    private String street;
    @NotNull(message = "uy nomerni kiritng")
    private String homeNumber;
}
