package br.com.sistemaescolar;

import br.com.sistemaescolar.domain.user.UserRole;
import org.junit.jupiter.api.Test;

public class TempTest {

    @Test
    void algo(){

        System.out.println(UserRole.ADMIN.getRole());
    }

}
