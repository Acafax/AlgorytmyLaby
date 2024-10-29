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
        //File file = new File("C:\\Users\\Dell\\IdeaProjects\\AlogrytmyZadanie\\src\\main\\resources\\Elementy.json");
        File file = new File("/home/wiktor/Desktop/Java_projekty/AlgorytmyLaby/src/main/resources/Elementy.json");
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


        //List<Plecak> listaPlecakow = new ArrayList<>();


//Laby pierwsze ORAZ 1. Podpunkt z Lab2
        for (int[] array : listaWektorow){
            System.out.println(Arrays.toString(array));
        }
//        int idWektora = 0;
//        for (int[] wektor : listaWektorow){
//            idWektora++;
//            PlecakKlasa plecakKlasa = new PlecakKlasa(0, 0);
//            for (int i=0; i<x; i++){
//                //meotda pobiera miejsce w wektorze
//                System.out.println("gen:" + (i+1)+" "+(wektor[i]));
//
//                PlecakKlasa plecak = obliczaniePlecaka(wektor, listaElementow, x);
//                plecakKlasa.setWartosc(plecak.getWartosc());
//                plecakKlasa.setWaga(plecak.getWaga());
//                //listaPlecaków.add(new Plecak(plecak.getWartosc(), plecak.getWaga()));
//            }
//
//            listaPlecaków.add(new Plecak(idWektora,plecakKlasa.getWartosc(),plecakKlasa.getWaga()));
//            System.out.println("\n");
//        }

        List<Plecak>  listaPlecakow = createListaPlecakow(x,listaWektorow, listaElementow);
        System.out.println(listaPlecakow);

        // 2. Podpunkt z lab 2

        int maxWaga =5;

        procesMudowania(maxWaga,x,listaPlecakow, listaWektorow);

//        listaPlecaków.forEach(plecak -> {
//
//            if (czyNieTrzebaMutować(plecak,maxWaga)){
//                mutowanie(x,plecak,listaPlecaków,listaWektorow.get(plecak.idWektora()));
//            }
//        });

    }
    private static List<Plecak> procesMudowania(int maxWaga, int x,List<Plecak> listaPlecaków,ArrayList<int[]> listaWektorow){
        listaPlecaków.forEach(plecak -> {

            if (czyNieTrzebaMutować(plecak,maxWaga)){
                mutowanie(x,plecak,listaPlecaków,listaWektorow.get(plecak.idWektora()));
            }
        });
        return listaPlecaków;
    }

    private static List<Plecak> createListaPlecakow(int x, ArrayList<int[]> listaWektorow, List<Element> listaElementow ){
        int idWektora=0;
        List<Plecak> listaPlecakow = new ArrayList<>(List.of());
        for (int[] wektor : listaWektorow){
            idWektora++;
            PlecakKlasa plecakKlasa = obliczaniePlecaka(wektor, listaElementow, x);
            Plecak plecak = new Plecak(idWektora, plecakKlasa.getWartosc(), plecakKlasa.getWaga());
            listaPlecakow.add(plecak);

        }
        return listaPlecakow;

    }

    private static List<Plecak> mutowanie(int x,Plecak plecak,List<Plecak> listaPlecaków,int[] wektor){
        Random random = new Random();

        System.out.println("Mutowanie");
        System.out.println(plecak.waga());

        int randomNumber = random.nextInt(x+1);
        System.out.println("Przed mutacją:");
        System.out.println(wektor[randomNumber]);
        wektor[randomNumber] = odwrotnaLiczba(wektor[randomNumber]);
        System.out.println("Po Mutoacji");
        System.out.println(wektor[randomNumber]);


//        System.out.println(wektor[0]);
//        System.out.println(wektor[1]);
//        System.out.println(wektor[2]);
//        System.out.println(wektor[3]);
//        System.out.println(wektor[4]);
        return listaPlecaków;
    }

    private static int odwrotnaLiczba(int i){
        if (i==0){
            return 1;
        }else {
            return 0;
        }
    }

    private static Boolean czyNieTrzebaMutować(Plecak plecak,int maxWaga){
        if (plecak.waga()>maxWaga) {
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