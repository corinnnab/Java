package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Product;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter fill file path: ");
        String path = sc.next();

        List<Product> products = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();

            while (line != null) {
                String[] product = line.split(",");
                products.add(new Product(product[0], Double.parseDouble(product[1])));
                line = br.readLine();
            }

            Double avg = products.stream().map(p -> p.getPrice()).reduce(0.0, (x, y) -> x + y) / products.size();
            System.out.println("Average price: " + avg);

            Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

            List<String> names = products.stream().filter(p -> p.getPrice() < avg).map(p -> p.getName())
                    .sorted(comp.reversed()).collect(Collectors.toList());
            names.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}