package org.exaple;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootApplication
public class CarStorage {
    private static CarStorage carStorage;
/*static, co oznacza, że jest to pole klasy,
a nie egzemplarza danej instancji.
Dzięki temu, nawet jeśli nie ma jeszcze instancji klasy,
 możemy uzyskać dostęp do tego pola na poziomie klasy.*/
   /*carStorage i carsList to obiekty róznego typu
   * i maja rozne zastosowanie*/
    private final List<Car> carsList= new ArrayList<>();
    /*final tutaj oznaczamy, ze REFERENCJA do obiektu jest niezmienialna,
    * ale sama zawartosc listy moze byc zmieniona, co potem podkresla
    * addCar() i purgeList()
    * final zabezpiecza obiekt, czyli carsList, zeby referencja
    * na niego nie byla zmieniona
    * Oznaczanie pól jako final, gdy to możliwe,
    * może zwiększyć czytelność kodu, ponieważ programiści
    * będą wiedzieli, że ta wartość nie powinna być zmieniana
    * po inicjalizacji.
    * ArrayList jest dynamiczną tablicą, która automatycznie
    * dostosowuje swój rozmiar w miarę dodawania i usuwania elementów.*/

    private CarStorage(){

    }
    /*robimy private konstruktor i go robimy pustego,
 * bo nie chcemy, zeby byly tworzone obiekty tej klasy
 * jest tylko JEDNA instancja, a ten pusty konstruktor
 *  ma na celu ograniczenie dostępu do konstruktora
 * klasy org.exaple.CarStorage z zewnątrz klasy.
  Chociaż konstruktor jest pusty, to nadal istotny, ponieważ sprawia,
  że nie jest on dostępny dla kodu spoza klasy.
 */
public static CarStorage getInstance(){
        if (carStorage==null){
            carStorage= new CarStorage();
        }
        return carStorage;
}
    /*
   Singleton. Wzorzec ten wymaga, aby dostęp do
    jednej instancji klasy odbywał się poprzez
   specjalną statyczną metodę (getInstance() w tym przypadku),
   a nie przez bezpośrednie tworzenie nowych instancji przez konstruktor.
    */
    public List<Car> getCarsList() {
        return carsList;
    }

    public Optional<Car> getCar(String vin) {
        return carsList.stream()
                .filter(car -> car.getVin().equals(vin))
                .findFirst();
    }
    /* 1. nazwa metody to getCar
    2. przetwarzamy cała liste carsList na stream obeiktow org.exaple.Car
    3. na tym steamie robimy operacje filter.
        car to zmienna, ktora reprezentuje pojedynczy obiekt w strumieniu,
        obiekt typu org.exaple.Car;
        car ->: Wyrażenie lambda, które przyjmuje pojedynczy element
        strumienia (obiekt typu org.exaple.Car) i przypisuje go do zmiennej
        lokalnej o nazwie car
        POTEM na tym jedym elemencie car (obiekt typu org.exaple.Car)
        wywolujemy metode getVin i porownujemy go equals z vin
        przekazanym jako parametr, czyli tym vin-em, jakiego szukamy

car.getVin().equals(vin): Warunek filtrujący. Porównuje numer VIN
(getVin()) obiektu org.exaple.Car z zadanym numerem VIN (vin).
Jeśli są równe, to warunek jest spełniony, co oznacza,
że obiekt org.exaple.Car przechodzi przez filtr.
        Podobnie jak w petli foreach, car to nazwa zmiennej.
        Byloby tak org.exaple.Car car: carsList {car.getVin() itd }
     4. potem na wyfiltrowanym strumieniu (zawierajacym samochody,
     ktore spelnily nasze warunki), wywolujemy findFirst(),
     ktore to zwraca pierwszy element, ktory spelnil podstawiony
     przed chwila warunek

    *      */
    /*Optional<org.exaple.Car> zwraca obiekt typu Optional<org.exaple.Car>
    *  .filter(car -> car.getVin().equals(vin))
    * po zastosowaniu filtra, ta metoda zwraca
    * pierwszy samochód spełniający warunek.
    * Jeśli nie ma pasującego samochodu, to zwraca pusty Optional
    * Stream w Java, dostarczają wygodny sposób operowania na kolekcjach obiektów
    * carsList.stream(): Metoda stream() na liście carsList tworzy strumień obiektów org.exaple.Car.
    * Strumień to sekwencja elementów, które mogą być przetwarzane w sposób sekwencyjny lub równoległy.
    * .filter(car -> car.getVin().equals(vin)): Metoda filter służy do wybierania
    * tylko tych obiektów org.exaple.Car z strumienia, które spełniają warunek
    * podany w wyrażeniu lambda. W tym przypadku, wybierane są tylko te samochody,
    * których numer VIN jest równy temu, który podano jako argument.
    * */

    public void addCar(Car car) {
        carsList.add(car);
    }
    public void purgeList(){
carsList.clear();
    }
    /* tu jest metoda purgeList bo jesli lista przechowuje
     * tymczasowe lub sesyjne to czyszczenie zwalnia pamiec
     *   lub czasami istnieje potrzeba zresetowania stanu
     * aplikacji do stanu poczatkowego
     * jezeli lista samochodow w magazynie czesto sie zmienia,
     * to czyszczenie i ponowna aktualizacja danych jest potrzebna
     * */
    public void removeCar(String vin){
        carsList.removeIf(car-> car!=null && car.getVin().equals(vin));
    }


}
