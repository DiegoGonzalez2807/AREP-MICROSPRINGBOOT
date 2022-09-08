/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.escuelaing.arep.microspringboot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import edu.escuelaing.arep.microspringboot.anotaciones.RequestMapping;

/**
 *
 * @author diego
 */
public class MicroSpringBoot {

    //Declaracion de arrelo de metodos creados para que lea MicroSpring



    public static void main(String... args) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, URISyntaxException{

        Map<String, Method> methodsMap = new HashMap<String, Method>();
        String className = args[0];
        Class c = Class.forName(className);
        Method[] declaredMethods = c.getDeclaredMethods();


        for(Method m : declaredMethods){
            if(m.isAnnotationPresent(RequestMapping.class)){
                try{
                    // /hello, WebService -> requestMapping("/hello") metodo
                    methodsMap.put(m.getAnnotation(RequestMapping.class).value(),m);
                    System.out.println("Invoking: "+m.getName()+" in class "+c.getName());
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }

        //SERVIDOR WEB 
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: "+getPort());
            System.exit(1);
        }
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            String possiblePath = null;
            boolean flag = true;
            while ((inputLine = in.readLine()) != null) {
                if (flag) {
                    flag = false;
                    possiblePath = inputLine.split(" ")[1];
                }
              
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }   

            String content = "404 NOT FOUND";
            if(methodsMap.containsKey(possiblePath)){
                content = (String) methodsMap.get(possiblePath).invoke(null);
            }

            outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n"
                    +content;
            out.println(outputLine);

            out.close();
            in.close();
            clientSocket.close();
        }

        serverSocket.close();
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
        return Integer.parseInt(System.getenv("PORT"));
    }
    return 4567;
 }
}
