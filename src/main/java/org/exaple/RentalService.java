package org.exaple;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@SpringBootApplication

public class RentalService {
    private final CarStorage carStorage;
    private final RentalStorage rentalStorage;

    public RentalService(CarStorage carStorage, RentalStorage rentalStorage) {
        this.carStorage = carStorage;
        this.rentalStorage = rentalStorage;
    }
    public Optional<Car> findCarByVin(String vin) {
        return carStorage.getCarsList()
                .stream()
                .filter(Car -> Car.getVin().equals(vin)).findFirst();
    }

    public double estimatePrice(String vin, LocalDate newRentalStartDate, LocalDate newRentalEndDate) {
        Optional<Car> carOptional = findCarByVin(vin);

        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            Type type = car.getType();
            int dailyCost = 100;
            long days = ChronoUnit.DAYS.between(newRentalStartDate, newRentalEndDate);
            int typeRate;

            switch (type) {
                case ECONOMY:
                    typeRate = 1;
                    break;
                case STANDARD:
                    typeRate = 2;
                    break;
                case PREMIUM:
                    typeRate = 3;
                    break;
                default:
                    typeRate = 1;
                    break;
            }
            return dailyCost * days * typeRate;
        }
        return 0;
    }

    public Rental makeRent(int userID, Car car, LocalDate newRentalStartDate, LocalDate newRentalEndDate) {
        Optional<Car> foundcar= findCarByVin(car.getVin());
        if (foundcar.isPresent() &&isAvailable(car.getVin(), newRentalStartDate, newRentalEndDate)) {
            double price= estimatePrice(car.getVin(), newRentalStartDate, newRentalEndDate);
            Rental rental = new Rental(new User(userID), car, newRentalStartDate, newRentalEndDate);
            rentalStorage.addRental(rental);
            return rental;
        } else {
            return null;
        }
    }

    public boolean isAvailable(String vin, LocalDate startDateofNewRental, LocalDate endDateofNewRental) {
        boolean carDoesNotExist = findCarByVin(vin).isEmpty();
        if (carDoesNotExist) {
            return false;
            //bo nie ma takiego auta
        }
        if (rentalStorage.getAllRentals().isEmpty()) {
            return true;
            //bo nie ma takiego rentala
        }
        List<Rental> rentalsForVin = rentalStorage.getAllRentals().stream()
                .filter(rental -> rental.getCar().getVin().equals(vin))
                .toList();
        /* W tym przypadku, elementy strumienia (które są obiektami org.exaple.Car)
         są zbierane do nowej listy.*/
        for (Rental rental : rentalsForVin) {
            if (isOverlappingDates(startDateofNewRental, endDateofNewRental, rental)) {
                return false;
            }
        }
        return true;
    }
    /*Tu na koncu return true oznacza, ze nie znaleziono zadnego konfliktu
    * wypozyczen i samochod jest dostepny.Czyli auto istnieje, lista rentali
    * jest pusta LUB jesli przeszukamy liste rentali i rental nowy nie
    * nakłada się ze starym.Czyli return true. */

    //IsBetween sluzy dla isOverlapping
    public boolean isBetween(LocalDate periodStart, LocalDate periodEnd, LocalDate checkingDate) {
        return checkingDate.isAfter(periodStart) && checkingDate.isBefore(periodEnd);
    } //sprawdzana data musi być po starcie i przed końcem

    //czy daty wynajmow nakladaja sie na siebie
    // is OverLapping sluzy dla isAvailable
    public boolean isOverlappingDates(LocalDate newRentalStartDate, LocalDate newRentalEndDate, Rental existingRent) {
        LocalDate existingStartDate = existingRent.getStartDate();
        LocalDate existingEndDate = existingRent.getEndDate();
        return (isBetween(existingStartDate, existingEndDate, newRentalStartDate) ||
                isBetween(existingStartDate, existingEndDate, newRentalEndDate));
    }
public void throwExceptionMethod(Exception typeOfException, String message) throws Exception{
      throw new Exception("Blad:", typeOfException);
}



}