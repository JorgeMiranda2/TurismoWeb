package com.turismo.Dtos.DtosOutput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoResponseLogin {
    String token;
    String userName;
}
