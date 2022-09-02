import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Basket {
    private final Product[] goods;
    private double totalValue = 0;

    public Basket(Product[] goods) {
        this.goods = goods;
    }

    public void addToCart(int productNum, int amount) {
        goods[productNum].changeItemInBasket(amount);
        totalValue += goods[productNum].getPrice() * amount;
    }

    public void printGoodsList() {

        System.out.print(" №       Название   Цена за шт.     В корзине        Стоимость\n" + "");
        for (int i = 0; i < goods.length; i++) {
            System.out.printf("%2d. %13s %12.2f %10d %17.2f\n", i + 1,
                    goods[i].getName(), goods[i].getPrice(),
                    goods[i].getInBasket(), goods[i].getInBasket() * goods[i].getPrice());
        }
        System.out.printf("ИТОГО Товаров в корзине на %10.2f\n\n", totalValue);
        System.out.print("""
                Добавьте товар в корзину  (№ и количество<ENTER>).
                Убрать товар из корзины   (№ и количество со знаком <->)
                Обнулить товар в корзине  (№ и <0>).
                Для завершения работы введите <end>.
                """);
    }

    public void printCart() {
        System.out.print("Ваша корзина:\n");
        for (Product item : goods) {
            if (item.getInBasket() > 0) {
                System.out.printf("%13s %3d шт. %6.2f руб/шт. %8.2f в сумме\n",
                        item.getName(), item.getInBasket(), item.getPrice(),
                        item.getInBasket() * item.getPrice());
            }
        }
        System.out.printf("ИТОГО Товаров в корзине на %10.2f\n\n", totalValue);
    }

    public void saveTxt(File textFile) throws FileNotFoundException {
        var pw = new PrintWriter(textFile);
        for (int i = 0; i < goods.length; i++) {
            if (goods[i].getInBasket() > 0) {
                pw.printf("%d %d\n", i, goods[i].getInBasket());
            }
        }
        pw.close();
    }

    public Basket loadFromTxtFile(File textFile) throws FileNotFoundException {
        Scanner sc = new Scanner(textFile);
        while (sc.hasNext()) {
            String[] d = sc.nextLine().split(" ");
            int productNumber = Integer.parseInt(d[0]);
            int inBasket = Integer.parseInt(d[1]);
            goods[productNumber].changeItemInBasket(inBasket);
        }
        return this;
    }
}
