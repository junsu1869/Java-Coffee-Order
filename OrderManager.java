
import java.util.ArrayList;

public class OrderManager {
    private String cafeName;
    private int minimumPrice;
    private ArrayList<Order> orders = new ArrayList<>();

    private boolean closed = false;
    private boolean ordered = false;

    public OrderManager(String cafeName, int minimumPrice) {
        this.cafeName = cafeName;
        this.minimumPrice = minimumPrice;
    }

    // 카페 이름 반환
    public String getCafeName() {
        return cafeName;
    }

    // 마감 여부 확인
    public boolean isClosed() {
        return closed;
    }

    // 주문 추가
    public boolean addOrder(String name, String menu, int price) {
        if (closed || ordered || price <= 0) {
            return false;
        }

        orders.add(new Order(name, menu, price));
        return true;
    }

    // 주문 번호 유효성 확인
    public boolean isValidOrderNumber(int orderNumber) {
        return orderNumber >= 1 && orderNumber <= orders.size();
    }

    // 총 주문 금액 계산
    public int getTotalPrice() {
        int total = 0;

        for (Order order : orders) {
            total += order.price;
        }

        return total;
    }

    // 최소 주문 금액 달성 여부
    public boolean isMinimumReached() {
        return getTotalPrice() >= minimumPrice;
    }

    // 공동주문 상태
    public String getStatus() {
        if (ordered) {
            return "주문 완료";
        } else if (closed) {
            return "마감";
        } else if (isMinimumReached()) {
            return "주문 가능";
        } else {
            return "모집 중";
        }
    }

    // 총 주문 금액 및 상태 출력
    public void printStatus() {
        int total = getTotalPrice();

        System.out.println("\n현재 총 주문 금액: " + total + "원");

        if (isMinimumReached()) {
            System.out.println("최소 주문 금액을 달성했습니다.");
        } else {
            System.out.println("남은 금액: "
                    + (minimumPrice - total) + "원");
        }

        System.out.println("상태: " + getStatus());
    }

    // 현재 주문 내역 출력
    public void printOrders() {
        System.out.println("\n--- 현재 주문 내역 ---");

        if (orders.isEmpty()) {
            System.out.println("등록된 주문이 없습니다.");
        }

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);

            System.out.println(
                (i + 1) + ". " +
                order.name + " - " +
                order.menu + " - " +
                order.price + "원"
            );
        }

        System.out.println("총 주문 금액: "
                + getTotalPrice() + "원");
    }

    // 주문 수정
    public boolean updateOrder(int orderNumber,
                               String newMenu, int newPrice) {
        if (closed || ordered || newPrice <= 0) {
            return false;
        }

        if (!isValidOrderNumber(orderNumber)) {
            return false;
        }

        Order order = orders.get(orderNumber - 1);
        order.updateOrder(newMenu, newPrice);
        return true;
    }

    // 주문 취소
    public boolean cancelOrder(int orderNumber) {
        if (closed || ordered) {
            return false;
        }

        if (!isValidOrderNumber(orderNumber)) {
            return false;
        }

        orders.remove(orderNumber - 1);
        return true;
    }

    // 공동주문 마감
    public boolean closeOrder() {
        if (closed || ordered) {
            return false;
        }

        // 최소 주문 금액을 달성해야 마감 가능
        if (!isMinimumReached()) {
            return false;
        }

        closed = true;
        return true;
    }

    // 최종주문 확정
    public boolean completeOrder() {
        if (!closed || ordered || !isMinimumReached()) {
            return false;
        }

        ordered = true;
        return true;
    }
}
