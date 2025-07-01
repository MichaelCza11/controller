package com.distribuida.dao;

import ch.qos.logback.core.net.server.Client;
import com.distribuida.model.Cliente;
import jakarta.transaction.Transactional;
import net.bytebuddy.dynamic.scaffold.TypeInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value=false)
public class ClienteRepositorioTestIntegracion {

    @Autowired //inyeccion de dependencias
    private ClienteRepositorio clienteRepositorio;

    @Test
    public void findAll(){

        List<Cliente>clientes = clienteRepositorio.findAll();
        assertNotNull(clientes);
        assertNotNull(clientes.size() >0);
        for(Cliente item : clientes){
            System.out.println(item.toString());
        }
    }

    @Test
    public void findOne(){
        Optional<Cliente> cliente = clienteRepositorio.findById(1);
        assertTrue(cliente.isPresent(), "El cliente con id= 39 deberia existir");
        System.out.println(cliente.toString());
    }

    @Test
    public void save(){
        Cliente cliente = new Cliente(0,"1752365434","Juan","Tapia", "Av. por ahi", "0923435623", "juan23@gmail.com");
        Cliente clienteGuardado = clienteRepositorio.save(cliente);
        assertNotNull(clienteGuardado.getIdCliente(),"Ell cliente guardado debe tener un id");
        assertEquals("1752365434", clienteGuardado.getCedula());
        assertEquals("Juan", clienteGuardado.getNombre());
    }

    @Test
    public void update(){
        Optional<Cliente> clienteExistente = clienteRepositorio.findById(39);
        assertTrue(clienteExistente.isPresent());

        clienteExistente.orElse(null).setCedula("1734658509");
        clienteExistente.orElse(null).setNombre("Juan2");
        clienteExistente.orElse(null).setApellido("Tapia2");
        clienteExistente.orElse(null).setDireccion("Av.2222");
        clienteExistente.orElse(null).setTelefono("0934535678");
        clienteExistente.orElse(null).setCorreo("jtapia4@gmail.com");

        Cliente clienteActualizado = clienteRepositorio.save(clienteExistente.orElse(null));

        assertEquals("Juan2", clienteActualizado.getNombre());
        assertEquals("Tapia2", clienteActualizado.getApellido());
    }

    @Test
    public void delete() {
        if (clienteRepositorio.existsById(39)) ;{
            clienteRepositorio.deleteById(39);
        }
        assertFalse(clienteRepositorio.existsById(40),"El id=40 deberia haberse eliminado");

    }
}
