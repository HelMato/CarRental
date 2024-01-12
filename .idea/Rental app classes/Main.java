import java.time.LocalDate;
import java.util.Optional;

public class Main {
    public static void main(String[] args){

        CarStorage carStorage = CarStorage.getInstance();
        carStorage.addCar(new Car("Skoda", "Fabia", "abc", Type.PREMIUM));

        RentalStorage rentalStorage = RentalStorage.getInstance();
        RentalService rentalService = new RentalService(carStorage, rentalStorage);

        double price = rentalService.estimatePrice(
                "abc",
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3)
        );
        System.out.println("Estimated Price: " + price);

        Optional<Car> carOptional = rentalService.findCarByVin("abc");

        carOptional.ifPresent(car -> System.out.println("Found car: " + car));

        Car car1 = new Car("Ford", "Mondeo", "W011321213213", Type.STANDARD);
        carStorage.addCar(car1);

        System.out.println("All Cars: " + carStorage.getCarsList());
    }
}
