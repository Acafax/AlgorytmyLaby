package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\Dell\\IdeaProjects\\AlogrytmyZadanie\\src\\main\\resources\\Elementy.json");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Element> listaElementow = objectMapper.readValue(file, new TypeReference<>() {});


        List<String> listaStringow = listaElementow.stream()
                .map(element -> element.id() + ":" + "wartosc:" + element.wartosc() + "waga: " + element.waga()+"\n")
                .toList();

        //System.out.println(listaElementow);

        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

//        System.out.print("podaj długość pojedyńczego wektoru:");
//        int x = scanner.nextInt();
        int x = 5;


//        System.out.print("Podaj ilosc wektorow/ ilosc elementow:");
//        int y = scanner.nextInt();
        int y= 5;
        scanner.close();


        int[] tabica = new int[x];
        ArrayList<int[]>listaTablic = new ArrayList<>();

        for (int i = 1; i<=y; i++){
            for (int z=0; z < x; z++){
                tabica[z] = random.nextInt(2);
            }
            listaTablic.add(tabica);

        }

        //System.out.println(Arrays.toString(tabica));


        List<Plecak> listaPlecaków = new ArrayList<>();



        for (int[] array : listaTablic){
            System.out.println(Arrays.toString(array));
        }
        for (int[] wektor : listaTablic){
            for (int i=0; i<x; i++){
                System.out.println("gen:" + (i+1)+" "+(wektor[i]));
                PlecakKlasa plecak = obliczaniePlecaka(wektor, listaElementow, x);
                listaPlecaków.add(new Plecak(plecak.getWartosc(), plecak.getWaga()));
            }
            System.out.println("\n");
        }

        System.out.println(listaPlecaków);


//        List<Element> elementyWPlecaku = new ArrayList<>();
        //Element[] plecaki = new Element[x];
    }
    private static PlecakKlasa obliczaniePlecaka(int[] wektor,List<Element> listaElementow,int x){
        //przejscie przez wektor aby obliczyć wartość plecaka
        // 1. przejdz przez każdy element wektora jeżeli wartość to 1 to przejdź przez listę elementów i sprawdz
        // zmienne elementow dla id = i
        PlecakKlasa plecak = new PlecakKlasa(0, 0);
        for (int i=0; i<x; i++){
            if (wektor[i] !=1){
                //System.out.println("");
            }
            int waga = listaElementow.get(i).waga();
            int wartosc = listaElementow.get(i).wartosc();
            //System.out.println(plecak);

        }

    }


}