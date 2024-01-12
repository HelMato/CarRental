import java.util.ArrayList;
import java.util.List;
public class CarStorage {
    private static CarStorage carsStorage; //jest tylko jedna instancja tej klasy
    private final List<Car> carsList = new ArrayList<>();

    private CarStorage() {
    }
    public static CarStorage getInstance() {
        if (carsStorage == null) {
            carsStorage = new CarStorage();
        }
        return carsStorage;
    }

    public List<Car> getAllCars() {
        return carsList;
    }

    public void addCar(Car car) {
        carsList.add(car);
    }

}