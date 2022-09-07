/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.escuelaing.arep.microspringboot;

import edu.escuelaing.arep.microspringboot.anotaciones.RequestMapping;

/**
 *
 * @author diego
 */
public class JunitTest {
    @Test
    public static void m1(){}
    
    public static void m2(){}
    
    @Test
    public static void m3(){}
    
    @Test
    public static void m4()throws Exception{
    throw(new Exception("ERRO M4"));}
    
    public static void m5(){}
    
    @Test
    public static void m6(){}
    
    @RequestMapping("/hello")
    public static void RequestMappingTest(){
        System.out.println("TEST request mapping");
    }
}
