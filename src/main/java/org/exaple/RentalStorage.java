package org.exaple;

import java.util.ArrayList;
import java.util.List;


public class RentalStorage {
    private static RentalStorage rentalStorage;
    private final List<Rental> rentalList = new ArrayList<>();

    private RentalStorage() {
    }
    public static RentalStorage getInstance() {
        if (rentalStorage == null) {
            rentalStorage = new RentalStorage();
        }
        return rentalStorage;
    }

    public List<Rental> getAllRentals() {
        return rentalList;
    }
    public void addRental(Rental rental) {
        rentalList.add(rental);
    }

    public void removeRental(Rental rent){

        rentalList.removeIf(rental-> rental.equals(rent));
    }
    /*Ogólnie rzecz biorąc, zarówno filter w strumieniu,
    jak i removeIf w liście są używane do filtrowania elementów
    na podstawie warunku. Wybór jednego lub drugiego zależy
    od kontekstu i preferencji programisty.
    W tym konkretnym przypadku, użycie removeIf jest całkowicie
    odpowiednie do celu usuwania wynajmów na podstawie samochodu.
    * */
    public void purgerentalList(){
        rentalList.clear();
    }
}
