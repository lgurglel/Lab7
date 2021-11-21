package managers;

import consoleWork.Outputer;
import exceptions.NotDeclaredValueException;
import tasck.*;

import java.util.Locale;


public class TicketAdder {
    private java.util.Scanner userScanner;
    private boolean fileMode;

    public TicketAdder(java.util.Scanner userScanner) {
        this.userScanner = userScanner;
        fileMode = false;
    }

    public String createNewName() {
        Outputer.println("Введите название билета: ");
        String name = userScanner.nextLine().trim();
        if (name != null && !name.equals("")) {
            return name;
        } else
            Outputer.println("Название билета введено неверно!");
        return createNewName();
    }

    public Long readX() {
        try {
            Outputer.println("Введите координату X:");
            String strX = userScanner.nextLine().trim();
            Long x = Long.parseLong(strX);
            if (x > -17) {
                return Long.parseLong(strX);
            } else {
                Outputer.println("X должен быть целым числом, больше -17.");
                return readX();
            }
        } catch (Exception e) {
            Outputer.println("Ошибка. Некорректная координата X.");
            return readX();
        }
    }

    public Double readY() {
        try {
            Outputer.println("Введите координату Y:");
            String strY = userScanner.nextLine().trim();
            Double yVal = Double.parseDouble(strY);
            if (strY != null)
                return yVal;
            else
                throw new Exception();
        } catch (Exception e) {
            Outputer.println("Ошибка. Некорректная координата Y. Y должен быть целым числом и не равен null.");
            return readY();
        }
    }

    public Coordinates createNewCoordinates() {
        return new Coordinates(readX(), readY());
    }

    public Float createNewPrice() {
        try {
            Outputer.println("Введите цену:");
            String priceStr = userScanner.nextLine().trim();
            Float price;
            if (priceStr != null && !priceStr.equals("")) {
                price = Float.parseFloat(priceStr);
                if (price > 0) {
                    return price;
                } else
                    Outputer.println("Цена должна быть больше 0!");
                return createNewPrice();
            } else
                throw new Exception();
        } catch (Exception e) {
            Outputer.println("Ошибка. Неверно указена цена.");
            return createNewPrice();
        }
    }

    public Long createNewDiscount() {
        try {
            Outputer.println("Введите скидку:");
            String discountStr = userScanner.nextLine().trim();
            Long discount;
            if (discountStr != null && !discountStr.equals("")) {
                discount = Long.parseLong(discountStr);
                if (discount > 0 && discount <= 100) {
                    return discount;
                } else {
                    Outputer.println("Ошибка. Скидка должна быть больше 0, а максимальное значение 100");
                    return createNewDiscount();
                }
            } else
                Outputer.println("Скидка не установлена.");
            return 0l;
        } catch (Exception e) {
            Outputer.println("Неверный формат скидки, если нет скидки, то просто нажмите 'Enter'");
            return createNewDiscount();
        }
    }

    public String createNewComment() {
        Outputer.println("Введите комментарий: ");
        String comment = userScanner.nextLine().trim();
        if (comment != null && !comment.equals("")) {
            return comment;
        } else
            Outputer.println("Комментарий не установлен.");
        return "";
    }

    public TicketType createNewType() {
        try {
            Outputer.println("Выберете и введите из списка тип билета: " + TicketType.nameList());
            String typeStr = userScanner.nextLine().trim();
            if (typeStr != null) {
                if(typeStr.isEmpty()) return null;
                return TicketType.valueOf(typeStr.toUpperCase(Locale.ROOT));
            } else
                Outputer.println("Тип билета не установлен.");
                return null;
        } catch (Exception e) {
            Outputer.println("Ошибка. Некорректный тип. Введите тип из перечисленных.");
            return createNewType();
        }
    }

    public String createNewVenueName() {
        Outputer.println("Введите название места проведения:");
        String name = userScanner.nextLine().trim();
        if (name != null && !name.equals("")) {
            return name;
        } else
            Outputer.println("Название места проведения введено неверно!");
        return createNewVenueName();
    }

    public int createNewCapacity() {
        try {
            Outputer.println("Введите колличество мест:");
            String capacityStr = userScanner.nextLine().trim();
            int capacity;
            if (capacityStr != null && !capacityStr.equals("")) {
                capacity = Integer.parseInt(capacityStr);
                if (capacity > 0) {
                    return capacity;
                } else
                    Outputer.println("Колличество месть должно быть больше 0.");
                return createNewCapacity();
            } else
                throw new Exception();
        } catch (Exception e) {
            Outputer.println("Ошибка. Неверный формат.");
            return createNewCapacity();
        }
    }

    public Venue createNewVenue() {
        return new Venue(createNewVenueName(), createNewCapacity(), createNewAddress());
    }

    public String createNewStreet() {
        Outputer.println("Введите название улицы:");
        String name = userScanner.nextLine().trim();
        if (name != null && !name.equals("")) {
            return name;
        } else
            Outputer.println("Название улицы не установленно.");
        return "";
    }

    public String createNewZipCode() {
        Outputer.println("Введите ZipCode (длинна строки должна быть не меньше 10):");
        String name = userScanner.nextLine().trim();
        if (name != null && !name.equals("") && name.length() > 9) {
            return name;
        } else
            Outputer.println("ZipCode не установлен.");
        return "";
    }

        private Address createNewAddress() {
            return new Address(createNewStreet(), createNewZipCode());
        }

    public void setFileMode() {
        fileMode = true;
    }

    public void setUserMode() {
        this.fileMode = false;
    }

    public boolean askAboutChangingField(String ask) {
        String res = ask + " (+/-):";
        String answer;
        while (true) {
            try {
                System.out.println(res);
                answer = userScanner.nextLine().trim();
                if (fileMode) System.out.println(answer);
                if (!answer.equals("+") && !answer.equals("-")) throw new NotDeclaredValueException();
                break;
            } catch (NotDeclaredValueException exception) {
                System.out.println("Ответ должен быть представлен знаками '+' или '-'!");
            }
        }
        return answer.equals("+");
    }

}
