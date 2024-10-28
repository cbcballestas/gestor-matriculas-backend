package com.cballestas.gestion_matriculas.security;

import java.util.Date;

public record AuthResponse(
        String token,
        Date expiration
) {
}
