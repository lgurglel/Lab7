package managers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import tasck.Ticket;

import java.io.*;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class FileManager {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ZonedDateTime.class, new TypeAdapter<ZonedDateTime>() {
                @Override
                public void write(JsonWriter out, ZonedDateTime value) throws IOException {
                    out.value(value.toString());
                }

                @Override
                public ZonedDateTime read(JsonReader in) throws IOException {
                    return ZonedDateTime.parse(in.nextString());
                }
            })
            .enableComplexMapKeySerialization()
            .create();
    private String envVariable;

    public FileManager(String envVariable) {
        this.envVariable = envVariable;
    }

    public void writeCollection(Collection<?> collection) {
        if (System.getenv().get(envVariable) != null) {
            try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(System.getenv().get(envVariable)))) {
                outputStreamWriter.write(GSON.toJson(collection));
                ResponseOutputer.append("Коллекция успешна сохранена в файл!");
            } catch (IOException exception) {
                ResponseOutputer.appendError("Загрузочный файл является директорией/не может быть открыт!");
            }
        } else ResponseOutputer.appendError("Системная переменная с загрузочным файлом не найдена!");
    }

    public ArrayList<Ticket> readCollection() {
        if (System.getenv().get(envVariable) != null) {
            try (BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(new FileInputStream(System.getenv().get(envVariable))))) {
                ArrayList<Ticket> collection;
                Type collectionType = new TypeToken<ArrayList<Ticket>>() {
                }.getType();
                collection = GSON.fromJson(inputStreamReader.readLine().trim(), collectionType);
                System.out.println("Коллекция успешна загружена!");
                return collection;
            } catch (FileNotFoundException exception) {
                System.out.println("Загрузочный файл не найден!");
            } catch (NoSuchElementException exception) {
                System.out.println("Загрузочный файл пуст!");
            } catch (JsonParseException | NullPointerException exception) {
                System.out.println("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (IllegalStateException exception) {
                System.out.println("Непредвиденная ошибка!");
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else System.out.println("Системная переменная с загрузочным файлом не найдена!");
        return new ArrayList<Ticket>();
    }

    public String readFile(String filePath) throws FileNotFoundException {
        java.util.Scanner scanner = new java.util.Scanner(new File(filePath));
        String fileString = "";
        while (scanner.hasNext()) {
            fileString += scanner.nextLine() + "\n";

        }
        return fileString;
    }
}
