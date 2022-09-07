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
public class WebServices {
    
    @RequestMapping("/hello")
    public static String helloWorld(){
        return "Hello World";
    }
    
    @RequestMapping("/status")
    public static String serverStatus(){
        return "Running";
    }
    
}
