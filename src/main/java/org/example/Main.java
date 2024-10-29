package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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

        List<Plecak>  listaPlecakow = createListaPlecakow(x,listaWektorow, listaElementow);
        System.out.println(listaPlecakow);

        // 2. Podpunkt z lab 2

        int maxWaga =10;

        List<Plecak> zmutowanaListaPlecakow = procesMudowania(maxWaga, x, listaPlecakow, listaWektorow,listaElementow);

        System.out.println("\nLisa stara:");
        listaPlecakow.forEach(System.out::println);
        System.out.println("\nLisa NOWA:");
        zmutowanaListaPlecakow.forEach(System.out::println);

        List<PoleRuletki> ruletka = ruletka(zmutowanaListaPlecakow);


        ruletka.forEach(System.out::println);

    }

        private static List<PoleRuletki> ruletka(List<Plecak> listaPlecakow){
            Random random = new Random();
            int randomNumer = random.nextInt(1, 1000)/1000;

            int wartoscPlecakow  = listaPlecakow.stream().mapToInt(Plecak::wartosc).sum();
            float jednorazowePoleNaRuletce = (float) 1 /wartoscPlecakow;
            //stowrzenie listy z przypisaymi polami
            List<PoleRuletki> listaPolRuletki = new ArrayList<>();
            listaPlecakow.forEach(plecak -> {
                System.out.println(jednorazowePoleNaRuletce);
                float wiekoscPola = plecak.wartosc() * jednorazowePoleNaRuletce*1000;
                //BigDecimal bigDecimal = new BigDecimal(wiekoscPola).setScale(3, RoundingMode.HALF_UP);
                listaPolRuletki.add(new PoleRuletki(plecak.idWektora(), wiekoscPola));
            });




            return listaPolRuletki;
        }



    private static List<Plecak> procesMudowania(int maxWaga, int x,List<Plecak> listaPlecaków,ArrayList<int[]> listaWektorow,List<Element> listaElementow ){
        List<Plecak> listaNowychPlecaków = new ArrayList<>();
        listaPlecaków.forEach(plecak -> {
            PlecakKlasa plecakKlasa = new PlecakKlasa(plecak.wartosc(),plecak.waga());

//            if (czyNieTrzebaMutować(plecakKlasa,maxWaga)){
//                mutowanie(maxWaga,x,plecak,listaWektorow.get(plecak.idWektora()), listaElementow);
//            }
            Plecak zmutowanyPlecak = mutowanie(maxWaga, x, plecak, listaWektorow.get(plecak.idWektora() - 1), listaElementow);
            listaNowychPlecaków.add(zmutowanyPlecak);

        });
        return listaNowychPlecaków;
    }

    private static Plecak mutowanie(int maxWaga,int x,Plecak plecak,int[] wektor,List<Element> listaElementow ){
        Random random = new Random();
        PlecakKlasa plecakKlasa = new PlecakKlasa(plecak.wartosc(),plecak.waga());

        while (czyNieTrzebaMutować(plecakKlasa,maxWaga)){
            int randomNumber = random.nextInt(x);

            for (int i= 0; i <x; i++){
                if (i==randomNumber){
                    wektor[i] = odwrotnaLiczba(wektor[i]);
                }
            }
            PlecakKlasa plecakKlasa1 = createPlecakKlasa(wektor, listaElementow, x);
            plecakKlasa.setWartosc(plecakKlasa1.getWartosc());
            plecakKlasa.setWaga(plecakKlasa1.getWaga());
        }

        return new Plecak(plecak.idWektora(),plecakKlasa.getWartosc(),plecakKlasa.getWaga());
    }


    private static List<Plecak> createListaPlecakow(int x, ArrayList<int[]> listaWektorow, List<Element> listaElementow ){
        int idWektora=0;
        List<Plecak> listaPlecakow = new ArrayList<>(List.of());
        for (int[] wektor : listaWektorow){
            idWektora++;
            PlecakKlasa plecakKlasa = createPlecakKlasa(wektor, listaElementow, x);
            Plecak plecak = new Plecak(idWektora, plecakKlasa.getWartosc(), plecakKlasa.getWaga());
            listaPlecakow.add(plecak);

        }
        return listaPlecakow;

    }

    private static int odwrotnaLiczba(int i){
        if (i==0){
            return 1;
        }else {
            return 0;
        }
    }

    private static Boolean czyNieTrzebaMutować(PlecakKlasa plecak,int maxWaga){
        if (plecak.getWaga()>maxWaga) {
            return true;
        }
        return false;
    }

    private static PlecakKlasa createPlecakKlasa(int[] wektor, List<Element> listaElementow, int x){
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