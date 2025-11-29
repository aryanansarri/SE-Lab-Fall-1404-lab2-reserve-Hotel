# آزمایش دوم - اصول شی‌گرایی (SOLID Principles)
## گام سوم: اصلاح موارد نقض

### معرفی

در این گام، کد اولیه بازنویسی شده و تمام موارد نقض اصول SOLID که در گام دوم شناسایی شدند، اصلاح شده‌اند. هدف از این بازنویسی، ایجاد کدی است که اصول شی‌گرایی را رعایت کند و برای گسترش باز و برای تغییر بسته باشد.

---

## اصلاحات انجام شده

### ۱. اصلاح نقض SRP (Single Responsibility Principle)

#### مشکل شناسایی شده:
کلاس `ReservationService` مسئولیت‌های متعددی داشت:
- پردازش رزرواسیون
- اعمال تخفیف
- مدیریت پرداخت
- ارسال اعلان
- تولید فاکتور

#### راه حل:
مسئولیت‌های `ReservationService` به کلاس‌های جداگانه تقسیم شد:

**الف) کلاس DiscountCalculator**
- **مسئولیت:** محاسبه و اعمال تخفیف‌ها
- **فایل:** `src/services/DiscountCalculator.java`

```java
public class DiscountCalculator {
    public void applyCityDiscount(Room room, String city) {
        if (city.equals("Paris")) {
            System.out.println("Apply city discount for Paris!");
            room.setPrice(room.getPrice() * 0.9);
        }
    }
}
```

**ب) کلاس InvoiceGenerator**
- **مسئولیت:** تولید و نمایش فاکتور
- **فایل:** `src/services/InvoiceGenerator.java`

```java
public class InvoiceGenerator {
    public void generateInvoice(Reservation reservation) {
        Customer customer = reservation.getCustomer();
        Room room = reservation.getRoom();
        
        System.out.println("----- INVOICE -----");
        System.out.println("hotel.Customer: " + customer.getName());
        System.out.println("hotel.Room: " + room.getNumber() + " (" + room.getType() + ")");
        System.out.println("Total: " + reservation.totalPrice());
        System.out.println("-------------------");
    }
}
```

**ج) کلاس NotificationService**
- **مسئولیت:** مدیریت ارسال اعلان‌ها
- **فایل:** `src/services/NotificationService.java`

```java
public class NotificationService {
    private EmailNotifier emailNotifier;
    private SmsNotifier smsNotifier;
    
    public NotificationService(EmailNotifier emailNotifier, SmsNotifier smsNotifier) {
        this.emailNotifier = emailNotifier;
        this.smsNotifier = smsNotifier;
    }
    
    public void sendNotification(Customer customer, Notifier notifier, String message) {
        switch (notifier) {
            case EMAIL:
                emailNotifier.sendEmail(customer.getEmail(), message);
                break;
            case SMS:
                smsNotifier.sendSms(customer.getMobile(), message);
                break;
        }
    }
}
```

**نتیجه:** حالا `ReservationService` فقط مسئول هماهنگی فرآیند رزرو است و هر کلاس یک مسئولیت مشخص دارد.

---

### ۲. اصلاح نقض OCP (Open-Closed Principle)

#### مشکل شناسایی شده:
- برای افزودن روش پرداخت جدید، باید `PaymentProcessor` و `ReservationService` تغییر کنند
- برای افزودن روش ارسال پیام جدید، باید `ReservationService` تغییر کند

#### راه حل:
استفاده از **Strategy Pattern** برای روش‌های پرداخت:

**الف) اینترفیس PaymentStrategy**
- **فایل:** `src/services/PaymentStrategy.java`

```java
public interface PaymentStrategy {
    void pay(double amount);
}
```

**ب) کلاس‌های پیاده‌سازی Strategy**
- `CardPayment.java` - پرداخت با کارت
- `CashPayment.java` - پرداخت نقدی
- `PayPalPayment.java` - پرداخت با PayPal

**ج) کلاس PaymentProcessorFactory**
- **فایل:** `src/services/PaymentProcessorFactory.java`

```java
public class PaymentProcessorFactory {
    public static PaymentStrategy createPaymentStrategy(PaymentMethods paymentType) {
        switch (paymentType) {
            case CARD:
                return new CardPayment();
            case CASH:
                return new CashPayment();
            case PAYPAL:
                return new PayPalPayment();
            default:
                throw new IllegalArgumentException("Unknown payment type: " + paymentType);
        }
    }
}
```

**استفاده در ReservationService:**
```java
PaymentStrategy paymentStrategy = PaymentProcessorFactory.createPaymentStrategy(paymentType);
paymentStrategy.pay(res.totalPrice());
```

**نتیجه:** برای افزودن روش پرداخت جدید (مثل OnSitePayment)، فقط نیاز به ایجاد کلاس جدید و افزودن case در Factory است. نیازی به تغییر کد موجود نیست.

---

### ۳. اصلاح نقض ISP (Interface Segregation Principle)

#### مشکل شناسایی شده:
اینترفیس `MessageSender` شامل متدهای `sendEmail` و `sendSmsMessage` بود که باعث می‌شد کلاینت‌ها مجبور به پیاده‌سازی متدهایی شوند که استفاده نمی‌کنند.

#### راه حل:
جداسازی اینترفیس‌ها به اینترفیس‌های کوچکتر و متمرکز:

**الف) اینترفیس EmailNotifier**
- **فایل:** `src/services/EmailNotifier.java`

```java
public interface EmailNotifier {
    void sendEmail(String to, String message);
}
```

**ب) اینترفیس SmsNotifier**
- **فایل:** `src/services/SmsNotifier.java`

```java
public interface SmsNotifier {
    void sendSms(String to, String message);
}
```

**ج) کلاس‌های پیاده‌سازی**
- `EmailSender` فقط `EmailNotifier` را پیاده‌سازی می‌کند
- `SmsSender` فقط `SmsNotifier` را پیاده‌سازی می‌کند

**نتیجه:** هر کلاینت فقط به متدهایی که نیاز دارد وابسته است و مجبور به پیاده‌سازی متدهای غیرضروری نیست.

---

### ۴. اصلاح نقض DIP (Dependency Inversion Principle)

#### مشکل شناسایی شده:
`ReservationService` مستقیماً به پیاده‌سازی‌های مشخص (`PaymentProcessor` و `EmailSender`) وابسته بود.

#### راه حل:
استفاده از **Dependency Injection** و وابستگی به انتزاع‌ها:

**الف) تغییر ReservationService:**
```java
public class ReservationService {
    private DiscountCalculator discountCalculator;
    private InvoiceGenerator invoiceGenerator;
    private NotificationService notificationService;
    
    public ReservationService(DiscountCalculator discountCalculator, 
                             InvoiceGenerator invoiceGenerator,
                             NotificationService notificationService) {
        this.discountCalculator = discountCalculator;
        this.invoiceGenerator = invoiceGenerator;
        this.notificationService = notificationService;
    }
    // ...
}
```

**ب) استفاده از اینترفیس‌ها:**
- `ReservationService` به `PaymentStrategy` (اینترفیس) وابسته است، نه به کلاس‌های مشخص
- `NotificationService` به `EmailNotifier` و `SmsNotifier` (اینترفیس‌ها) وابسته است

**ج) Dependency Injection در Main:**
```java
EmailNotifier emailNotifier = new EmailSender();
SmsNotifier smsNotifier = new SmsSender();
NotificationService notificationService = new NotificationService(emailNotifier, smsNotifier);

ReservationService service = new ReservationService(
    discountCalculator, 
    invoiceGenerator, 
    notificationService
);
```

**نتیجه:** `ReservationService` به انتزاع‌ها وابسته است و می‌تواند با هر پیاده‌سازی از اینترفیس‌ها کار کند.

---

### ۵. اصلاح نقض PLK (Principle of Least Knowledge / Law of Demeter)

#### مشکل شناسایی شده:
دسترسی زنجیره‌ای به فیلدها مانند `res.customer.name`، `res.room.price` و غیره.

#### راه حل:
افزودن متدهای getter و استفاده از آن‌ها به جای دسترسی مستقیم به فیلدها:

**الف) کلاس Customer:**
```java
public class Customer {
    private String name;
    private String email;
    private String city;
    private String mobile;
    
    // Getter methods
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCity() { return city; }
    public String getMobile() { return mobile; }
}
```

**ب) کلاس Room:**
```java
public class Room {
    private String number;
    private String type;
    private double price;
    
    // Getter methods
    public String getNumber() { return number; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
```

**ج) کلاس Reservation:**
```java
public class Reservation {
    private Room room;
    private Customer customer;
    private int nights;
    
    // Getter methods
    public Room getRoom() { return room; }
    public Customer getCustomer() { return customer; }
    public int getNights() { return nights; }
}
```

**استفاده در ReservationService:**
```java
// قبل: res.customer.name
// بعد: res.getCustomer().getName()

// قبل: res.room.price
// بعد: res.getRoom().getPrice()
```

**نتیجه:** `ReservationService` فقط با دوستان مستقیم خود (`Reservation`) ارتباط برقرار می‌کند و دسترسی زنجیره‌ای حذف شده است.

---

## خلاصه تغییرات

### کلاس‌های جدید ایجاد شده:

1. **DiscountCalculator** - محاسبه تخفیف‌ها
2. **InvoiceGenerator** - تولید فاکتور
3. **NotificationService** - مدیریت اعلان‌ها
4. **PaymentStrategy** (interface) - استراتژی پرداخت
5. **CardPayment** - پرداخت با کارت
6. **CashPayment** - پرداخت نقدی
7. **PayPalPayment** - پرداخت با PayPal
8. **PaymentProcessorFactory** - ایجاد استراتژی پرداخت
9. **EmailNotifier** (interface) - اینترفیس ارسال ایمیل
10. **SmsNotifier** (interface) - اینترفیس ارسال پیامک
11. **SmsSender** - ارسال پیامک

### کلاس‌های حذف شده:

1. **PaymentProcessor** - جایگزین با Strategy Pattern
2. **MessageSender** - جایگزین با EmailNotifier و SmsNotifier

### کلاس‌های تغییر یافته:

1. **ReservationService** - بازنویسی کامل با استفاده از Dependency Injection
2. **Customer** - افزودن getter methods و private کردن فیلدها
3. **Room** - افزودن getter/setter methods و private کردن فیلدها
4. **Reservation** - افزودن getter methods و private کردن فیلدها
5. **EmailSender** - تغییر به پیاده‌سازی EmailNotifier
6. **LuxuryRoom** - استفاده از getter methods
7. **Main** - استفاده از Dependency Injection

---

## مقایسه قبل و بعد

### قبل از اصلاح (Step-01):
- ❌ `ReservationService` دارای ۵ مسئولیت
- ❌ برای افزودن پرداخت جدید نیاز به تغییر کد موجود
- ❌ اینترفیس بزرگ با متدهای غیرضروری
- ❌ وابستگی به کلاس‌های مشخص
- ❌ دسترسی زنجیره‌ای به فیلدها

### بعد از اصلاح (Step-02):
- ✅ هر کلاس یک مسئولیت مشخص
- ✅ افزودن قابلیت جدید بدون تغییر کد موجود (OCP)
- ✅ اینترفیس‌های کوچک و متمرکز (ISP)
- ✅ وابستگی به انتزاع‌ها (DIP)
- ✅ استفاده از getter methods (PLK)

---

## نحوه اجرا

```bash
cd Step-02-with-OOD
mkdir -p out
javac -d out src/**/*.java
java -cp out Main
```

---

## نتیجه‌گیری

با اعمال اصلاحات فوق، تمام موارد نقض اصول SOLID برطرف شدند:

✅ **SRP**: هر کلاس یک مسئولیت مشخص دارد  
✅ **OCP**: سیستم برای گسترش باز و برای تغییر بسته است  
✅ **LSP**: زیرکلاس‌ها می‌توانند جایگزین کلاس والد شوند  
✅ **ISP**: اینترفیس‌ها کوچک و متمرکز هستند  
✅ **DIP**: وابستگی به انتزاع‌ها است نه پیاده‌سازی‌ها  
✅ **PLK**: دسترسی فقط از طریق دوستان مستقیم  
✅ **CRP**: کلاس‌های مرتبط در بسته‌های مناسب قرار دارند  

کد حالا قابل نگهداری‌تر، قابل گسترش‌تر و قابل تست‌تر است.

