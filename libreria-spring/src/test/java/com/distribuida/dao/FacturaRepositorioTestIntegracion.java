package com.distribuida.dao;

import com.distribuida.model.Cliente;
import com.distribuida.model.Factura;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)
public class FacturaRepositorioTestIntegracion {

    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private FacturaRepositorio facturaRepositorio;

    @Test
    public void findAll(){
        List<Factura> facturas = facturaRepositorio.findAll();
        assertNotNull(facturas);
        assertTrue(facturas.size()>0);
        for(Factura item: facturas){
            System.out.println(item.toString());
        }
    }

    @Test
    public void finOne(){
        Optional<Factura> facturaExistente = facturaRepositorio.findById(1);
        assertNotNull(facturaExistente.isPresent());
        assertEquals("FAC-0001", facturaExistente.orElse(null).getNumFactura(), "La factura deberia existir");
        assertEquals(134.79, facturaExistente.orElse(null).getNumFactura(), "La factura deberia existir");
        System.out.println();
    }

    @Test
    public void save(){
        Optional<Cliente> cliente = clienteRepositorio.findById(1);
        Factura factura = new Factura(0, "FAC-00099", new Date(),100.00,15.00,115.00,cliente.orElse(null));
        Factura facturaGuarda = facturaRepositorio.save(factura);
        assertNotNull(facturaGuarda);
        assertEquals("FACC-0009",facturaGuarda.getNumFactura());
        assertEquals(115.0,facturaGuarda.getTotal());

        facturaRepositorio.save(factura);
    }

    @Test
    public void update(){
        Optional<Factura> factura = facturaRepositorio.findById(86);
        Optional<Cliente> cliente = clienteRepositorio.findById(2);
        factura.orElse(null).setNumFactura("FAC-00100");
        factura.orElse(null).setFecha(new Date());
        factura.orElse(null).setTotalNeto(200.00);
        factura.orElse(null).setIva(30.00);
        factura.orElse(null).setTotal(230.00);
        factura.orElse(null).setCliente(cliente.orElse(null));

        Factura facturaActualizada = facturaRepositorio.save(factura.orElse(null));

        assertNotNull(facturaActualizada);
        assertEquals("FAC - 00100", facturaActualizada.getNumFactura());
        assertEquals(230.0, facturaActualizada.getTotal());
    }

    @Test
    public void delete(){
        if (facturaRepositorio.existsById(86));
        facturaRepositorio.deleteById(86);
        assertFalse(facturaRepositorio.existsById(86));
    }

}
