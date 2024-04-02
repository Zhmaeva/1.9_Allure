package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private static String[] _cities = new String[] { "Абакан","Анадырь","Архангельск","Астрахань","Барнаул","Белгород","Биробиджан","Благовещенск","Брянск","Великий Новгород","Владивосток","Владикавказ","Владимир","Волгоград","Вологда","Воронеж","Горно-Алтайск","Грозный","Екатеринбург","Иваново","Ижевск","Иркутск","Йошкар-Ола","Казань","Калининград","Калуга","Кемерово","Киров","Кострома","Краснодар","Красноярск","Курган","Курск","Кызыл","Липецк","Магадан","Магас","Майкоп","Махачкала","Москва","Мурманск","Нальчик","Нарьян-Мар","Нижний Новгород","Новосибирск","Омск","Орёл","Оренбург","Пенза","Пермь","Петрозаводск","Петропавловск-Камчатский","Псков","Ростов-на-Дону","Рязань","Салехард","Самара","Санкт-Петербург","Саранск","Саратов","Севастополь","Симферополь","Смоленск","Ставрополь","Сыктывкар","Тамбов","Тверь","Томск","Тула","Тюмень","Улан-Удэ","Ульяновск","Уфа","Хабаровск","Ханты-Мансийск","Чебоксары","Челябинск","Черкесск","Чита","Элиста","Южно-Сахалинск","Якутск","Ярославль" };
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        String date = LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        return date;
    }

    public static String generateCity(String locale) {
        Random random = new Random();
        int randomIndex = random.nextInt(DataGenerator._cities.length - 1);

        return DataGenerator._cities[randomIndex];
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().fullName().replace('ё', 'e');

        return name;
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String phone = faker.phoneNumber().phoneNumber();

        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            UserInfo user = new UserInfo(
                    generateCity(locale),
                    generateName(locale),
                    generatePhone(locale));

            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
