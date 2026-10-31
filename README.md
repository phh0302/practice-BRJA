# HeroGame - Player Report (JavaFX + JDBC + MySQL)

Ứng dụng JavaFX hoàn chỉnh cho đề bài **Java Programming 2 - Set01 Practical Paper**.

## 1. Cấu trúc project

```
HeroGameApp/
 ├── pom.xml
 ├── sql/heroGame.sql                 <- script tạo database HeroGame
 └── src/main/
      ├── java/com/heroGame/
      │    ├── Main.java              <- điểm khởi động JavaFX
      │    ├── db/DBConnection.java   <- kết nối JDBC tới MySQL
      │    ├── model/Player.java      <- entity Player (getter/setter/constructor)
      │    ├── model/National.java    <- entity National
      │    ├── dao/PlayerDAO.java     <- add/remove/find/top10/show all Player
      │    ├── dao/NationalDAO.java   <- add/remove National
      │    └── controller/MainController.java <- xử lý sự kiện UI
      └── resources/com/heroGame/view/MainView.fxml  <- giao diện
```

## 2. Chuẩn bị Database (MySQL Server hoặc MySQL/MariaDB)

1. Mở MySQL Workbench / mysql CLI.
2. Chạy toàn bộ nội dung file `sql/heroGame.sql`. Script sẽ:
   - Tạo database `HeroGame`
   - Tạo bảng `National` và `Player` (đúng field theo đề bài, PlayerId & NationalId auto_increment PK)
   - Thêm sẵn dữ liệu mẫu đúng Table 1 trong đề (Player 1/2/3 - Vietnam/USA/Japan)

## 3. Cấu hình kết nối

Mở file `src/main/java/com/heroGame/db/DBConnection.java` và sửa lại cho đúng với máy bạn:

```java
private static final String URL = "jdbc:mysql://localhost:3306/HeroGame?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "root"; // đổi thành mật khẩu MySQL của bạn
```

## 4. Chạy chương trình

Yêu cầu: JDK 17+, Maven 3.8+.

```bash
cd HeroGameApp
mvn clean javafx:run
```

(Maven sẽ tự tải JavaFX và MySQL Connector/J theo `pom.xml`, không cần cài thủ công.)

Nếu bạn dùng IntelliJ / Eclipse: import project dạng Maven, đảm bảo đã cài JavaFX SDK plugin, rồi chạy class `Main.java`.

## 5. Các chức năng đã đáp ứng đúng yêu cầu đề bài

Bảng dưới khớp đúng tên hạng mục và tên method trong **Marking Schema** của đề (tổng 15 điểm):

| Marking Schema | Mark | Nơi hiện thực |
|---|---|---|
| Create Database and connection to Database | 5 | `sql/heroGame.sql`, `db/DBConnection.java` |
| Create Entity Player, National with setter, getter, constructor | 2 | `model/Player.java`, `model/National.java` |
| Create and implement method `insertPlayer`, `deletePlayer` | 2 | `PlayerDAO.insertPlayer()`, `PlayerDAO.deletePlayer()` |
| Create and implement method `displayAll` | 2 | `PlayerDAO.displayAll()` |
| Create and implement method `displayAllByPlayerName` | 2 | `PlayerDAO.displayAllByPlayerName()` |
| Create method `displayTop10` | 2 | `PlayerDAO.displayTop10()` |
| **Total** | **15** | |

Các method trên được gọi từ `MainController.java` tương ứng với các nút trên giao diện JavaFX (`Add Player`, `Remove Player`, `Show All`, `Find`, `Top 10 Players`). `NationalDAO.add/remove` và `National` entity phục vụ cho yêu cầu quản lý National trong đề, dù không có tên method bắt buộc riêng trong marking schema.

## 6. Mở rộng (tùy chọn, cộng điểm trình bày)

- Có thể thêm validate dữ liệu đầu vào chặt hơn (HighScore/Level phải là số dương).
- Có thể thêm chức năng Edit (đã có sẵn `PlayerDAO.update()`, chỉ cần thêm nút "Update" gọi tới).
- Có thể thêm biểu đồ (BarChart) thể hiện Top 10 bằng JavaFX Charts nếu đề yêu cầu minh họa trực quan.
