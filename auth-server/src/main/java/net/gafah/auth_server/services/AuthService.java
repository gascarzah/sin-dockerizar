package net.gafah.auth_server.services;

import net.gafah.auth_server.dtos.TokenDto;
import net.gafah.auth_server.dtos.UserDto;

public interface AuthService {

    TokenDto login(UserDto user);
    TokenDto validateToken(TokenDto token);
}