import java.time.LocalDate;

public class Rental {
    private final User user;
    private final Car car;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

    public Rental(User user, Car car, LocalDate dateFrom, LocalDate dateTo) {
        this.user = user;
        this.car = car;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public User getUser() {
        return user;
    }

    public Car getCar() {
        return car;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }
}