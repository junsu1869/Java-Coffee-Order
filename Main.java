
import java.util.Scanner;

public class Main {

    // 양의 정수 입력
    public static int inputPrice(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            try {
                int price = Integer.parseInt(input);

                if (price <= 0) {
                    System.out.println("0보다 큰 금액을 입력해주세요.");
                    continue;
                }

                return price;

            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
            }
        }
    }

    // 메뉴 번호 및 주문 번호 입력
    public static int inputChoice(Scanner scanner) {
        while (true) {
            System.out.print("선택: ");
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);

            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, "UTF-8");

        // 공동주문 생성
        System.out.print("카페 이름: ");
        String cafeName = scanner.nextLine();

        int minimumPrice =
                inputPrice(scanner, "최소 주문 금액: ");

        OrderManager manager =
                new OrderManager(cafeName, minimumPrice);

        // 생성자 주문 등록
        System.out.println("\n--- 공동주문 생성 ---");

        System.out.print("생성자 이름: ");
        String name = scanner.nextLine();

        System.out.print("메뉴명: ");
        String menu = scanner.nextLine();

        int price = inputPrice(scanner, "가격: ");

        manager.addOrder(name, menu, price);
        manager.printStatus();

        // 공동주문 메뉴
        while (true) {
            System.out.println("\n--- 공동주문 메뉴 ---");
            System.out.println("1. 공동주문 참여");
            System.out.println("2. 현재 주문 내역 확인");
            System.out.println("3. 최소 주문 금액 확인");
            System.out.println("4. 공동주문 마감");
            System.out.println("5. 최종주문");
            System.out.println("6. 주문 수정");
            System.out.println("7. 주문 취소");
            System.out.println("8. 프로그램 종료");

            int choice = inputChoice(scanner);

            if (choice == 1) {
                // 공동주문 참여
                if (manager.isClosed()) {
                    System.out.println("이미 마감된 공동주문입니다.");
                    continue;
                }

                System.out.print("참여자 이름: ");
                name = scanner.nextLine();

                System.out.print("메뉴명: ");
                menu = scanner.nextLine();

                price = inputPrice(scanner, "가격: ");

                boolean added = manager.addOrder(name, menu, price);

                if (added) {
                    System.out.println("주문이 추가되었습니다.");
                    manager.printStatus();
                } else {
                    System.out.println("주문을 추가할 수 없습니다.");
                }

            } else if (choice == 2) {
                // 현재 주문 내역 확인
                manager.printOrders();

            } else if (choice == 3) {
                // 최소 주문 금액 확인
                manager.printStatus();

            } else if (choice == 4) {
                // 공동주문 마감
                boolean closed = manager.closeOrder();

                if (closed) {
                    System.out.println("\n공동주문이 마감되었습니다.");
                    manager.printOrders();
                    manager.printStatus();

                } else if (manager.isClosed()) {
                    System.out.println("이미 마감된 공동주문입니다.");

                } else {
                    System.out.println(
                        "최소 주문 금액을 달성해야 마감할 수 있습니다.");
                    manager.printStatus();
                }

            } else if (choice == 5) {
                // 최종주문
                if (!manager.isClosed()) {
                    System.out.println("공동주문을 먼저 마감해주세요.");
                    continue;
                }

                boolean completed = manager.completeOrder();

                if (completed) {
                    System.out.println("\n--- 최종 주문 확인 ---");
                    System.out.println("카페 이름: "
                            + manager.getCafeName());

                    manager.printOrders();
                    manager.printStatus();

                    System.out.println("\n최종주문이 완료되었습니다.");
                    System.out.println("프로그램을 종료합니다.");
                    break;

                } else {
                    System.out.println("최종주문을 진행할 수 없습니다.");
                }

            } else if (choice == 6) {
                // 주문 수정
                if (manager.isClosed()) {
                    System.out.println(
                        "마감된 공동주문은 수정할 수 없습니다.");
                    continue;
                }

                manager.printOrders();

                System.out.println("\n수정할 주문 번호를 입력해주세요.");
                int orderNumber = inputChoice(scanner);

                // 새 메뉴를 입력하기 전에 주문 번호 확인
                if (!manager.isValidOrderNumber(orderNumber)) {
                    System.out.println("존재하지 않는 주문 번호입니다.");
                    continue;
                }

                System.out.print("새 메뉴명: ");
                String newMenu = scanner.nextLine();

                int newPrice = inputPrice(scanner, "새 가격: ");

                boolean updated =
                        manager.updateOrder(orderNumber, newMenu, newPrice);

                if (updated) {
                    System.out.println("주문이 수정되었습니다.");
                    manager.printOrders();
                    manager.printStatus();
                } else {
                    System.out.println("주문을 수정할 수 없습니다.");
                }

            } else if (choice == 7) {
                // 주문 취소
                if (manager.isClosed()) {
                    System.out.println(
                        "마감된 공동주문은 취소할 수 없습니다.");
                    continue;
                }

                manager.printOrders();

                System.out.println("\n취소할 주문 번호를 입력해주세요.");
                int orderNumber = inputChoice(scanner);

                if (!manager.isValidOrderNumber(orderNumber)) {
                    System.out.println("존재하지 않는 주문 번호입니다.");
                    continue;
                }

                boolean canceled = manager.cancelOrder(orderNumber);

                if (canceled) {
                    System.out.println("주문이 취소되었습니다.");
                    manager.printOrders();
                    manager.printStatus();
                } else {
                    System.out.println("주문을 취소할 수 없습니다.");
                }

            } else if (choice == 8) {
                // 프로그램 종료
                System.out.println("프로그램을 종료합니다.");
                break;

            } else {
                System.out.println("잘못된 메뉴 번호입니다.");
            }
        }
    }
}