package com.distribuida.principal;

import com.distribuida.entities.Cliente;
import com.distribuida.entities.Factura;

import java.util.Date;

public class FacturaPrincipal {
    public static void main(String[] args){

        Factura factura = new Factura();
        Cliente cliente = new Cliente(1,"1755571872","Katherine","Aguirre","Tababela","0967865057","klac2504@gmail.com");

        factura.setIdFactura(1);
        factura.setNumFactura("FAC-0001");
        factura.setFecha(new Date());
        factura.setTotalNeto(15.00);
        factura.setIva(15.00);
        factura.setTotal(115.00);

        //inyeccionn de dependecias

        factura.setCliente(cliente);

        System.out.println(factura.toString());
    }

}
