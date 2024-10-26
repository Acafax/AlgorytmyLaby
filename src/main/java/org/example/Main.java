package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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


//        List<String> listaStringow = listaElementow.stream()
//                .map(element -> element.id() + ":" + "wartosc:" + element.wartosc() + "waga: " + element.waga()+"\n")
//                .toList();

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


        //Tworzenie wektorów

        ArrayList<int[]>listaWektorow = new ArrayList<>();

        for (int i = 1; i<=y; i++){
            int[] tabica = new int[x];
            for (int z=0; z < x; z++){
                tabica[z] = random.nextInt(2);
            }
            listaWektorow.add(tabica);
        }

        List<Plecak> listaPlecaków = new ArrayList<>();


//Laby pierwsze ORAZ 1. Podpunkt z Lab2
        for (int[] array : listaWektorow){
            System.out.println(Arrays.toString(array));
        }
        int idWektora = 0;
        for (int[] wektor : listaWektorow){
            idWektora++;
            PlecakKlasa plecakKlasa = new PlecakKlasa(0, 0);
            for (int i=0; i<x; i++){
                //meotda pobiera miejsce w wektorze
                System.out.println("gen:" + (i+1)+" "+(wektor[i]));

                PlecakKlasa plecak = obliczaniePlecaka(wektor, listaElementow, x);
                plecakKlasa.setWartosc(plecak.getWartosc());
                plecakKlasa.setWaga(plecak.getWaga());
                //listaPlecaków.add(new Plecak(plecak.getWartosc(), plecak.getWaga()));
            }

            listaPlecaków.add(new Plecak(idWektora,plecakKlasa.getWartosc(),plecakKlasa.getWaga()));
            System.out.println("\n");
        }

        System.out.println(listaPlecaków);

        // 2. Podpunkt z lab 2

        int maxWaga =5;

        mutowanieLepsze(listaElementow,listaPlecaków, maxWaga, listaWektorow,x);

//        listaPlecaków.forEach(plecak -> {
//            while (czyNieTrzebaMutować(plecak,maxWaga)){
//                mutowanie(x,plecak,listaPlecaków,listaWektorow.get(plecak.idWektora()));
//
//            }
//        });


        System.out.println("KONIEC");
        System.out.println("");
        System.out.println("KONIEC");
        System.out.println("KONIEC");

    }

    private static void mutowanieLepsze(List<Element> listaElementow,List<Plecak> listaPlecaków, int maxWaga, ArrayList<int[]> listaWektorow, int x) {
        listaPlecaków.forEach(plecak -> {
            Random random = new Random();
            int[] wektor = listaWektorow.get(plecak.idWektora());
            PlecakKlasa plecakKlasa = new PlecakKlasa(plecak.waga(), plecak.wartosc());

            while (czyNieTrzebaMutować(plecakKlasa,maxWaga)){
                int randomNumber = random.nextInt(x);
                Plecak mutowanyPlecak;

                System.out.println("WAGA PLECAKA: "+plecak.waga());

                wektor[randomNumber] = odwrotnaLiczba(wektor[randomNumber]);

                PlecakKlasa plecakKlasaMutowany = obliczaniePlecaka(wektor, listaElementow, x);
                plecakKlasa.setWaga(plecakKlasaMutowany.getWaga());
                plecakKlasa.setWartosc(plecakKlasa.getWartosc());
                System.out.println("NOWA WAGA"+plecakKlasa.getWaga());


                System.out.println("CZY TRZEAB MUTOWAĆ: "+ czyNieTrzebaMutować(plecakKlasa,maxWaga));

            }
        });
    }

    private static String mutowanie(int x,Plecak plecak,List<Plecak> listaPlecaków,int[] wektor){
        Random random = new Random();

        System.out.println("Mutowanie");
        System.out.println(plecak.waga());

        int randomNumber = random.nextInt(x);
        System.out.println("Przed mutacją:");
        System.out.println("RANDOMOWA LICZBA: " + randomNumber);
        System.out.println(wektor[randomNumber]);
        System.out.println("RANDOMOWA LICZBA: " + randomNumber);

        wektor[randomNumber] = odwrotnaLiczba(wektor[randomNumber]);
        System.out.println(" ");
        System.out.println("Po Mutoacji");
        System.out.println(wektor[randomNumber]);
        System.out.println("");

        System.out.println(wektor[0]);
        System.out.println(wektor[1]);
        System.out.println(wektor[2]);
        System.out.println(wektor[3]);
        System.out.println(wektor[4]);

        listaPlecaków.add(plecak.idWektora(), plecak);

        System.out.println("\n");
        return "Zmutowany";
    }

    private static int odwrotnaLiczba(int i){
        if (i==0){
            return 1;
        }else {
            return 0;
        }
    }

    private static Boolean czyNieTrzebaMutować(PlecakKlasa plecakKlasa,int maxWaga){
        if (plecakKlasa.getWaga()>maxWaga) {
            return true;
        }
        return false;
    }



    private static PlecakKlasa obliczaniePlecaka(int[] wektor,List<Element> listaElementow,int x){
        //przejscie przez wektor aby obliczyć wartość plecaka
        // 1. przejdz przez każdy element wektora jeżeli wartość to 1 to przejdź przez listę elementów i sprawdz
        // zmienne elementow dla id = i
        PlecakKlasa plecak = new PlecakKlasa(0, 0);
        for (int i=0; i<x; i++){
            if (wektor[i] ==1){
                int obecnaWagaPlecaka = plecak.getWaga();
                int obecnaWartośćPlecaka = plecak.getWartosc();

                int nowaWaga = obecnaWagaPlecaka+listaElementow.get(i).waga();
                int nowaWartosc = obecnaWartośćPlecaka+listaElementow.get(i).wartosc();

                plecak.setWaga(nowaWaga);
                plecak.setWartosc(nowaWartosc);
            }

            //System.out.println(plecak);

        }

        return plecak;
    }


}