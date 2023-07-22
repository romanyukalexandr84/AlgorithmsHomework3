import java.util.Iterator;

public class MainProgram {
    public static void main(String[] args) {
        HashMap<String, String> ourMap = new HashMap<>();

        ourMap.put("+79053551122", "Михаил");
        ourMap.put("+79053551123", "Сергей");
        ourMap.put("+79053551124", "Мария");
        ourMap.put("+79053551125", "Петр");
        ourMap.put("+79053551126", "Ирина");
        ourMap.put("+79053551127", "Дмитрий");
        ourMap.put("+79053551128", "Иван");
        ourMap.put("+79053551129", "Николай");

        Iterator <String> iter = ourMap.iterator();
        while (iter.hasNext()) {
            if (!iter.next().equals("null")) {
                System.out.println(iter.next());
            }
        }
    }
}
