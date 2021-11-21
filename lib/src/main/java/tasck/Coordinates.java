package tasck;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Long x; //Значение поля должно быть больше -17, Поле не может быть null
    private Double y; //Поле не может быть null

    public Coordinates(Long x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Long getX() {
        return x;
    }

    public Double getY() {
        return y;
    }
}