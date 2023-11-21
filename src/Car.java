public class Car {
    private final String brand;
    private final String model;
    private final String vin;
    private final Type type;

    public Car(String brand, String model, String vin, Type type){
        this.brand = brand;
        this.model = model;
        this.vin = vin;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getVin() {
        return vin;
    }

}