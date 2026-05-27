package com.risosuit.CajeroService.Service;

import com.risosuit.CajeroService.DAO.TarjetaDAOImplementation;

import com.risosuit.CajeroService.ML.Tarjeta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private TarjetaDAOImplementation tarjetaDAO;
    
    @Override
    public UserDetails loadUserByUsername(String numTarjeta)
            throws UsernameNotFoundException {
        
        Tarjeta tarjeta = (Tarjeta) tarjetaDAO
                .findByNumTarjeta(numTarjeta).object;
        if (tarjeta == null) {
            throw new UsernameNotFoundException("No se encontró la tarjeta con el número: " + numTarjeta);
        }
        if((tarjeta.getStatus() == 0)){
               throw new DisabledException ("Tarjeta Deshabilitada: " + numTarjeta);
        }
        
        return User.builder()
                .username(tarjeta.getNumTarjeta())
                .password(tarjeta.getPin())
                .build();
    }
}
