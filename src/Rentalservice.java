import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RentalService {
    private CarStorage carStorage;
    private RentalStorage rentalStorage;

    public RentalService(CarStorage carStorage, RentalStorage rentalStorage) {
        this.carStorage = carStorage;
        this.rentalStorage = rentalStorage;
    }
    public Optional<Car> getCar(String vin) {
        return carStorage.getCarsList().stream().filter(Car -> Car.getVin().equals(vin)).findFirst();
    }

    public double estimatePrice(String vin, LocalDate startDate, LocalDate endDate) {
        Optional<Car> carOptional = getCar(vin);

        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            Type type = car.getType();
            int dailyCost = 100;
            long days = ChronoUnit.DAYS.between(startDate, endDate);
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

    public Rental makeRent(int userID, String vin, LocalDate startDate, LocalDate endDate) {
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
        Optional<Car> auto = getCar(vin);
        if (auto.isEmpty()) {
            return false;
            //bo nie ma takiego auta
        }
        if (rentalStorage.getAllRentals().isEmpty()) {
            return true;
            //bo nie ma takiego rentala
        }
        List<Rental> rentalList = rentalStorage.getAllRentals()
                .stream().filter(rental -> rental.getCar().getVin().equals(vin))
                .collect(Collectors.toList());
        for (Rental rental : rentalList) {
            if (isStartAfterEndDate(startDate, endDate, rental)) {
                return false;
            }
        }
        return true;
    }
    boolean isStartAfterEndDate(LocalDate startDate, LocalDate endDate, Rental newRental) {
        boolean isNewRentalStartDateAfterOldRentalEnd = startDate.isAfter(newRental.getEndDate());
        boolean isNewRentalEndAfterOldRentalStart = endDate.isAfter(newRental.getStartDate());
        return isNewRentalStartDateAfterOldRentalEnd && isNewRentalEndAfterOldRentalStart;
    } 
    public Optional<Car> findCarByVin(String vin) {
        return carStorage.getCarsList()
                .stream()
                .filter(Car -> Car.getVin().equals(vin)).findFirst();
    }

}