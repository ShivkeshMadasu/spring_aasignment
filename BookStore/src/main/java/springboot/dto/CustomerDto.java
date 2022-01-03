package springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private int id;

    @NotBlank(message="Name is required")
    private String name;

    @Pattern(regexp="^[789][0-9]{9}",message = "enter 10 digit mobile number")
    @NotBlank(message="Mobile Number is required")
    private String mobileNumber;
}
