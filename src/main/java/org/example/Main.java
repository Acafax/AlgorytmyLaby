package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        //File file = new File("C:\\Users\\Dell\\IdeaProjects\\AlogrytmyZadanie\\src\\main\\resources\\Elementy.json");
        //File file = new File("/home/wiktor/Desktop/Java_projekty/AlgorytmyLaby/src/main/resources/Elementy.json");
        File file = new File("/home/wiktor/Pulpit/java_projekty/AlgorytmyLaby/src/main/resources/Elementy.json");

        ObjectMapper objectMapper = new ObjectMapper();
        List<Element> listaElementow = objectMapper.readValue(file, new TypeReference<>() {});

        Scanner scanner = new Scanner(System.in);

//        System.out.print("podaj długość pojedyńczego wektoru:");
//        int x = scanner.nextInt();
        int x = 5;


//        System.out.print("Podaj ilosc wektorow/ ilosc elementow:");
//        int y = scanner.nextInt();
        int y= 5;
        scanner.close();

        //Tworzenie wektorów
        ArrayList<int[]> listaWektorow = tworzenieWektorow(x, y);

        System.out.println("POCZĄTEK:");
        wyswietlWektor(listaWektorow);
        System.out.println("");
        List<Plecak>  poczatkowalistaPlecakow = createListaPlecakow(x,listaWektorow, listaElementow);

        poczatkowalistaPlecakow.stream()
                .map(element -> "WARTOŚĆ: " + element.wartosc() + "   WAGA:" + element.waga())
                .forEach(System.out::println);

        int maxWaga =10;

        int iloscPrzejscPetli =0;
        //ArrayList<int[]> listaWektorowKoncowych = new ArrayList<>();

        while ( iloscPrzejscPetli <=50){

            List<Plecak> listaPlecakow = new ArrayList<>(poczatkowalistaPlecakow);

           iloscPrzejscPetli++;

            // Proces mudowanie przy przeładowaniu
            List<Plecak> zmutowanaListaPlecakow = procesMudowania(maxWaga, x, listaPlecakow, listaWektorow,listaElementow);

            poczatkowalistaPlecakow.clear();
            poczatkowalistaPlecakow.addAll(zmutowanaListaPlecakow);

            List<PoleRuletki> ruletka = ruletka(zmutowanaListaPlecakow);
            List<Plecak> listaNowychPlecakow = new ArrayList<>();
            for (int i =1; i <=y ; i++){
                listaNowychPlecakow.add(wyborPolaRuletki(listaPlecakow,ruletka));
            }

            ArrayList<int[]> krzyzowanieLista = procesKrzyzowania(x,listaNowychPlecakow, listaWektorow);

            krzyzowanieLista.replaceAll(wektor ->{
                Random random = new Random();
                int i = random.nextInt(100);
                if (i <15){
                    return mutowanieWektora(maxWaga,x,wektor,listaElementow);
                }else return wektor;

            });

            // podmiana starej listy wektorow na nowy
            listaWektorow.clear();
            listaWektorow.addAll(krzyzowanieLista);

            //podmiana starej listy plecakow na nowy
            List<Plecak> listaPlecakow1 = createListaPlecakow(x, listaWektorow, listaElementow);
            poczatkowalistaPlecakow.clear();
            poczatkowalistaPlecakow.addAll(listaPlecakow1);


        }
        System.out.println("\nKONIEC");
        wyswietlWektor(listaWektorow);
        System.out.println("");
        List<Plecak>  listaPlecakow2 = createListaPlecakow(x,listaWektorow, listaElementow);

        listaPlecakow2.stream()
                .map(element -> "WARTOŚĆ: " + element.wartosc() + "   WAGA:" + element.waga())
                .forEach(System.out::println);


    }
//sdf
    private static void wyswietlWektor(ArrayList<int[]> listaWektorow){
        listaWektorow.forEach(wektor -> {
            String string = Arrays.toString(wektor);
            System.out.println(string);
        });
    }

    private static ArrayList<int[]> krzyzowanie(int wielkoscWektora, List<Plecak> listaNowychPlecakow, ArrayList<int[]> listaWektorow ){
        int miejscePodzialu = wielkoscWektora/2;
        int warunekKrzyzowania = 82;
        Random random = new Random();
        ArrayList<int[]> listaNowychWektorow = new ArrayList<>();

        for (int i = 0; i<= listaNowychPlecakow.size()-2 ; i+=2 ){
            int[] plecak1 = listaWektorow.get(i);
            int[] plecak2 = listaWektorow.get(i+1);
            //System.out.println("\ni = "+ i);


            int osobystePrawdopodobienstwoKrzyzowania = random.nextInt(0, 100);
            //System.out.println(osobystePrawdopodobienstwoKrzyzowania);
            if (osobystePrawdopodobienstwoKrzyzowania>warunekKrzyzowania) {
                listaNowychWektorow.add(plecak1);
                listaNowychWektorow.add(plecak2);
            }else {
                //System.out.println("KRZYZOWANIE");
                int[] nowyPlecak1 = Arrays.copyOf(plecak1, plecak1.length);
                int[] nowyPlecak2 = Arrays.copyOf(plecak2, plecak2.length);

                for(int e= miejscePodzialu; e< plecak1.length; e++){
                    nowyPlecak1[e] = plecak2[e];
                    nowyPlecak2[e] = plecak1[e];
                }
                listaNowychWektorow.add(nowyPlecak1);
                listaNowychWektorow.add(nowyPlecak2);
            }
        }
        return listaNowychWektorow;
    }

    private static ArrayList<int[]> procesKrzyzowania(int wielkoscWektora, List<Plecak> listaNowychPlecakow, ArrayList<int[]> listaWektorow ){
        boolean listaNieparzysta = listaNowychPlecakow.size()%2!=0;
        ArrayList<int[]> listaNowychWektorow = new ArrayList<>();

        if (listaNieparzysta){
            listaNowychWektorow.addAll(krzyzowanie(wielkoscWektora,listaNowychPlecakow,listaWektorow));
            listaNowychWektorow.add(listaWektorow.get(listaNowychPlecakow.size()-1));
        }else {
            listaNowychWektorow.addAll(krzyzowanie(wielkoscWektora,listaNowychPlecakow,listaWektorow));
        }
    return listaNowychWektorow;
    }

    private static Plecak wyborPolaRuletki(List<Plecak>  listaPlecakow,List<PoleRuletki> ruletka){
        Random random = new Random();
        List<PoleRuletki> listaPolDoWyboru= new ArrayList<>();

        ruletka.forEach(pole -> {
            for (int i=1; pole.wielkoscPola() > i; i++){
                listaPolDoWyboru.add(pole);
            }
        });
        int randomIndex = random.nextInt(1, listaPolDoWyboru.size());
        return listaPlecakow.get((listaPolDoWyboru.get(randomIndex).idPlecaka())-1);
    }

    private static List<PoleRuletki> ruletka(List<Plecak> listaPlecakow){
        int wartoscPlecakow  = listaPlecakow.stream().mapToInt(Plecak::wartosc).sum();
        float jednorazowePoleNaRuletce = (float) 1 /wartoscPlecakow;
        List<PoleRuletki> listaPolRuletki = new ArrayList<>();
        listaPlecakow.forEach(plecak -> {
            float wiekoscPola = plecak.wartosc() * jednorazowePoleNaRuletce*1000;
            listaPolRuletki.add(new PoleRuletki(plecak.idWektora(), wiekoscPola));
        });
        return listaPolRuletki;
    }

    private static ArrayList<int[]> tworzenieWektorow(int x, int y){
        Random random = new Random();
        ArrayList<int[]>listaWektorow = new ArrayList<>();

        for (int i = 1; i<=y; i++){
            int[] tabica = new int[x];
            for (int z=0; z < x; z++){
                tabica[z] = random.nextInt(2);
            }
            listaWektorow.add(tabica);
        }
        return listaWektorow;
    }

    private static List<Plecak> procesMudowania(int maxWaga, int x,List<Plecak> listaPlecaków,ArrayList<int[]> listaWektorow,List<Element> listaElementow ){
        List<Plecak> listaNowychPlecaków = new ArrayList<>();
        listaPlecaków.forEach(plecak -> {

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

    private static int[] mutowanieWektora(int maxWaga,int x,int[] wektor,List<Element> listaElementow ){
        Random random = new Random();
        int randomNumber = random.nextInt(x);

        for (int i= 0; i <x; i++){
            if (i==randomNumber){
                wektor[i] = odwrotnaLiczba(wektor[i]);
            }
        }
        PlecakKlasa plecakKlasa = createPlecakKlasa(wektor, listaElementow, x);
        if (plecakKlasa.getWaga()>maxWaga){
            mutowanieWektora(maxWaga,x,wektor,listaElementow);
        }

        return  wektor;
    }

    private static List<Plecak> createListaPlecakow(int dlugoscWektora, ArrayList<int[]> listaWektorow, List<Element> listaElementow ){
        int idWektora=0;
        List<Plecak> listaPlecakow = new ArrayList<>(List.of());
        for (int[] wektor : listaWektorow){
            idWektora++;

            PlecakKlasa plecakKlasa = createPlecakKlasa(wektor, listaElementow, dlugoscWektora);
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
        }
        return plecak;
    }
}