import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RentalService {

    public Optional<Car> getCar(String vin) {
        return carStorage.getAllCars().stream().filter(car -> car.getVin().equals(vin)).findFirst();
    }


    public double estimatePrice(String vin, LocalDate startDate, LocalDate endDate) {
        Optional<Car> carOptional = getCar(vin);

        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            Type type = car.getType();
            int dailyCost = 100;
            int days = (int) Duration.between(startDate, endDate).toDays();
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

    public Rental rent(int userID, String vin, LocalDate startDate, LocalDate endDate) {
        Car car = getCar(vin).orElseThrow();
        if (isAvailable(vin, startDate, endDate)) {
            Rental rental = new Rental(new User(userID), car, startDate, endDate);
            rentalStorage.addRental(rental);
            return rental;
        } else {
            return null;
        }
    }

    public boolean isAvailable(String vin, LocalDate startDate, LocalDate endDate) {
        boolean carDoesNotExist = getCar(vin).isEmpty();
        if (carDoesNotExist) {
            return false;
        }
        if (rentalStorage.getAllRentals().isEmpty()) {
            return true;
        }
        List<Rental> rentalOptional = RentalStorage.getAllRentals().stream()
                .filter(rental -> rental.getCar().getVin().equals(vin))
                .findfFirst()
                .orElse(null);
        for (Rental rental : rentalOptional) {
            if (isStartoverendDate(startDate, endDate, rental)) {
                return false;
            }
        }
        return true;
    }

    private boolean isStartoverendDate(LocalDate startDate, LocalDate endDate, Rental rental) {
        boolean isEndBeforeRentalStart = endDate.isBefore(rental.getStartDate());
        boolean isStartDateAfterRentalEnd = startDate.isAfter(rental.getEndDate());
        return (isEndBeforeRentalStart || isStartDateAfterRentalEnd);

    }
        if(rentalOptional.isPresent())

    {
        Rental rental = rentalOptional.get();
        if (isStartoverendDate(startDate, endDate, rental)) {
            return false;
        } else {
            return true;
        }
    }
        return true;


    Car car = getCar(vin).orElseThrow();
    List<Rental> existingRentals = RentalStorage.rentalStorage.getRentalList();
        for(Rental rental:existingRentals) {
        if (rental.getCar().equals(car)) {
            if (startDate.isBefore(rental.getDateTo()) && endDate.isAfter(rental.getDateFrom())) {
                return false;
            }
        }
    }
                return true;

}



    /*

    RentalService.rent(vin, cośtamID, startDate, endDate --3
    czy samochod istnieje
    czy jest dostepny --4
            if
            true wynajac samochod
            else powiadomic o niedostepnosci
        zwrocic status wynajecia

    rentalService isavailable(vin, startDate, endDate) --2
        czy samochod istnieje
        czy istnieje rental dla tego samochodu
        15-20.09 -> data rentala
        15-20.09; 17-27.09; 16-18.09; 14-21.09

        if
            true
                data poczatkowa dostepnosci mniejsza od daty koncowej rentala
                data koncowa dostepnosci mniejsza od poczatkowej rentala
            false
                zwracamy informacje, ze auto dostepne
  rentalService.estimatePrice(vin, startDate, endDate) --2
  wyszukac samochod dla vinu --1
  ilosc dni * cena za dzien * wspolczynnik z Typu samochodu
    */

public Optional<Car> findCarByVin(String vin){
        return carStorage.getAllCars().stream()
        .filter(car->car.getVin().equals(vin))
        .findFirst();
        }

