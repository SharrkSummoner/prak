# Магазин книг

Простое приложение с графическим интерфейсом и связью с БД

# Зависимости

* Java 19 SDK

# Сборка проекта


```bash
# mvnw script like mvn utility
$ bash mvnw install
$ bash mvnw dependency:copy-dependencies
```

# Запуск
После сборки
```
$ DB_HOST=localhost \
DB_PORT=3306 \
DB_NAME=book \
DB_USER=root \
DB_PASS=root \
java --module-path=target/dependency/ --add-modules javafx.controls,javafx.fxml -cp target/prak-1.0-SNAPSHOT.jar com.example.prak.Main
```