package com.example.demo.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.Person;
import com.example.demo.Repo.PersonRepository;

@Service
public class PersonService {
    
    @Autowired
    private PersonRepository personRepository;

    public void savePersonsFromCsv(MultipartFile file) throws IOException {
        List<Person> persons = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        int row = 0;
        while ((line = br.readLine()) != null) {
            String[] fields = line.split(",");
            if (row == 0) {
                if (!fields[0].equals("name") || !fields[1].equals("email") || !fields[2].equals("age")) {
                    throw new RuntimeException("Invalid CSV file header");
                }
            } else {
                if (fields.length != 3) {
                    throw new RuntimeException("Invalid number of fields in row " + row);
                }
                String name = fields[0];
                String email = fields[1];
                int age;
                try {
                    age = Integer.parseInt(fields[2]);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid age in row " + row);
                }
                persons.add(new Person(name, email, age));
            }
            row++;
        }
        personRepository.saveAll(persons);
    }
}
