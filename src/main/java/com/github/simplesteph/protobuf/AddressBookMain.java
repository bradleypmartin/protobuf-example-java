package com.github.simplesteph.protobuf;

import com.example.tutorial.AddressBookProtos;
import com.google.protobuf.Timestamp;
import example.simple.Simple.SimpleMessage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;

public class AddressBookMain {

    public static void main(String[] args) {

        System.out.println("Hello world!");

        // preparing to build a Person message
        AddressBookProtos.Person.Builder builder = AddressBookProtos.Person.newBuilder();

        // preparing a Timestamp object to inject into the metadata (may not explicitly need this?)
        Instant time = Instant.now();
        Timestamp timestamp = Timestamp.newBuilder().setSeconds(time.getEpochSecond())
                .setNanos(time.getNano()).build();

        // person fields (could try adding to an AddressBook in the future)
        builder.setId(42)  // set the id field
                .setEmail("brad@coolemail.com")  // set the email field
                .setName("Brad") // set the name field
                .setLastUpdated(timestamp);

        System.out.println(builder.toString());

        AddressBookProtos.Person person = builder.build();

        // write the protocol buffers binary to a file
        try {
            FileOutputStream outputStream = new FileOutputStream("person_message.bin");
            person.writeTo(outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // send as byte array
        // byte[] bytes = message.toByteArray();

        try {
            System.out.println("Reading from file... ");
            FileInputStream fileInputStream = new FileInputStream("person_message.bin");
            AddressBookProtos.Person personFromFile = AddressBookProtos.Person.parseFrom(fileInputStream);
            System.out.println(personFromFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
