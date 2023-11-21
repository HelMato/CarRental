public enum Type {
    ECONOMY(1), STANDARD(2), PREMIUM(3);

    private final double multiplier;

    Type(double multiplier){

        this.multiplier=multiplier;
    }
public double getMultiplier(){

        return multiplier;
}



}
