import java.time.LocalDate;
import java.util.Optional;

public class Main {
 public static void main(String[] args) {
  CarStorage carsStorage = CarStorage.getInstance();
  carsStorage.addCar(new Car("Skoda", "Fabia", "abc", Type.PREMIUM));
  RentalStorage rentalStorage = RentalStorage.getInstance();
  RentalService rentalService = new RentalService(carsStorage, rentalStorage);

  double abc = rentalService.optionalPrice (
   "abc",
           LocalDate.now().plusDays(1),
   LocalDate.now().plusDays(3)
  );
  System.out.println(abc);
  /*
  rentalService rent(vin, userId)
  czy samochod istnieje
  czy jest dostepny
  zwrocic status wynajecia
  rentalService.isAvailable(vin, startDate, endDate....
  rentalService.optionalPrice(via,startDate, endDate
*/
 }

 ;
}


  Optional<Car> abc=carsStorage.getAllCars().stream()
          .filter(car->car.getVin().equals("abc"))
          .findFirst();


  Car car1 = new Car("ford","mondeo", "W011321213213", Type.STANDARD);
  carsStorage.addCar(car1);
  System.out.println(carsStorage.getAllCars());
 }
}