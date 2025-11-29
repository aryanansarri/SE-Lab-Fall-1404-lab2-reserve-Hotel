# پروژه پایه - سیستم رزرو هتل

این پروژه پایه برای آزمایش اصول SOLID است.

## ساختار پروژه

- `src/` - کدهای منبع پروژه
- `src/models/` - کلاس‌های مدل (Customer, Room, LuxuryRoom)
- `src/services/` - سرویس‌ها (ReservationService, PaymentProcessor, EmailSender)
- `src/constants/` - ثابت‌ها (PaymentMethods, Notifier)

## نحوه اجرا

```bash
mkdir -p out
javac -d out src/**/*.java
java -cp out Main
```

