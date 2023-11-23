package com.turismo.Dtos.DtosInput;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoRegister {
    String email;
    String name;
    String lastName;
    String userName;
    String password;
}
