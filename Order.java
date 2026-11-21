
public class Order {
    String name;
    String menu;
    int price;

    public Order(String name, String menu, int price) {
        this.name = name;
        this.menu = menu;
        this.price = price;
    }

    // 주문 메뉴와 가격 수정
    public void updateOrder(String newMenu, int newPrice) {
        this.menu = newMenu;
        this.price = newPrice;
    }
}
