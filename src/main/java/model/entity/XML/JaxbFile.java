package model.entity.XML;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JaxbFile {

    private static final Path p= Paths.get("src/main/resources/XmlLog.xml");

    public static void log(String happened) {
        try {
            JAXBContext context = JAXBContext.newInstance(LogBook.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            LogBook logs;

            if(Files.exists(p)) {
                Unmarshaller unmarshaller = context.createUnmarshaller();
                logs=(LogBook) unmarshaller.unmarshal(p.toFile());
            }else logs=new LogBook();

            logs.addLogs(new LogEntry(happened));
            marshaller.marshal(logs, p.toFile());


        }catch (Exception e){
            System.out.println("Greska prilikom zapisivanja u log");
        }
    }

    public static void ispis(){
        try{
            JAXBContext context=JAXBContext.newInstance(LogBook.class);
            Unmarshaller unmarshaller=context.createUnmarshaller();

            LogBook logs=new LogBook();

            if(Files.exists(p)) {
                logs=(LogBook) unmarshaller.unmarshal(p.toFile());
            }

            for(LogEntry log:logs.getLogs()){
                System.out.println(log.getTime()+" - "+log.getHappened());
            }
        }catch (Exception e){
            System.out.println("Greska prilikom ispisa logova");
        }
    }

}
