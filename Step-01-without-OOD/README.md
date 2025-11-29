# آزمایش دوم - اصول شی‌گرایی (SOLID Principles)

## معرفی پروژه

این پروژه یک سیستم رزرو هتل است که برای آموزش و تمرین اصول SOLID طراحی شده است. در این آزمایش، ابتدا قابلیت‌های جدیدی به سیستم اضافه می‌شود و سپس کد از نظر رعایت اصول SOLID بررسی و تحلیل می‌شود.

### ساختار سیستم

سیستم شامل کلاس‌های زیر است:
- **Customer**: اطلاعات مشتری
- **Room** و **LuxuryRoom**: اطلاعات اتاق‌ها
- **Reservation**: اطلاعات رزرواسیون
- **ReservationService**: سرویس اصلی رزرواسیون
- **PaymentProcessor**: پردازش پرداخت
- **MessageSender** و **EmailSender**: ارسال پیام و ایمیل

---

## بخش ۱: افزودن قابلیت ارسال پیامک (SMS)

### توضیحات
در این بخش، قابلیت ارسال پیامک به سیستم اضافه شده است. این کار با ایجاد کلاس `SmsSender` مشابه `EmailSender` انجام شده است.

### تغییرات اعمال شده

#### ۱. کلاس MessageSender
**فایل:** `src/services/MessageSender.java`

**تغییرات:**
- افزودن متد `sendSmsMessage(String to, String message)` به اینترفیس

**کد:**
```java
public interface MessageSender {
    public void sendEmail(String to, String message);
    public void sendSmsMessage(String to, String message);  // متد جدید
}
```

#### ۲. کلاس EmailSender
**فایل:** `src/services/EmailSender.java`

**تغییرات:**
- افزودن پیاده‌سازی متد `sendSmsMessage()` (بدنه خالی، چون EmailSender پیامک ارسال نمی‌کند)

**کد:**
```java
public void sendSmsMessage(String to, String message){
    // EmailSender doesn't send SMS
}
```

#### ۳. کلاس SmsSender (جدید)
**فایل:** `src/services/SmsSender.java`

**تغییرات:**
- ایجاد کلاس جدید `SmsSender` که `MessageSender` را پیاده‌سازی می‌کند
- پیاده‌سازی متد `sendSmsMessage()` برای ارسال پیامک

**کد:**
```java
class SmsSender implements MessageSender{
    public void sendEmail(String to, String message){
        // SmsSender doesn't send email
    }
    
    public void sendSmsMessage(String to, String message){
        System.out.println("Sending SMS to " + to + ": " + message);
    }
}
```

#### ۴. کلاس ReservationService
**فایل:** `src/services/ReservationService.java`

**تغییرات:**
- افزودن case جدید `SMS` در switch statement مربوط به notifier
- استفاده از `SmsSender` برای ارسال پیامک به شماره موبایل مشتری
- اصلاح باگ: تغییر `switch (this.notifier)` به `switch (notifier)` برای استفاده از پارامتر متد

**کد:**
```java
switch (notifier){
    case EMAIL :
        EmailSender emailSender = new EmailSender();
        emailSender.sendEmail(res.customer.email, "Your reservation confirmed!");
        break;
    case SMS:  // case جدید
        SmsSender smsSender = new SmsSender();
        smsSender.sendSmsMessage(res.customer.mobile, "Your reservation confirmed!");
        break;
    default:
        System.out.println("There is no Message Provider");
}
```

### جدول خلاصه تغییرات

| کلاس تغییر یافته | توضیح کوتاه در مورد تغییر |
|------------------|---------------------------|
| MessageSender | افزودن یک تابع برای ارسال پیامک به نام `sendSmsMessage` |
| EmailSender | افزودن پیاده‌سازی متد `sendSmsMessage()` (بدنه خالی) |
| SmsSender | ایجاد کلاس جدید برای ارسال پیامک |
| ReservationService | افزودن case SMS در switch statement و استفاده از SmsSender |

### کامیت
این قابلیت در کامیت زیر اضافه شده است:
```
commit fdf669b - Add SMS sending functionality
```

---

## بخش ۲: افزودن روش پرداخت حضورى (On-Site Payment)

### توضیحات
در این بخش، روش پرداخت حضورى به سیستم اضافه شده است. این کار با افزودن متد جدید به کلاس `PaymentProcessor` انجام شده است.

### تغییرات اعمال شده

#### ۱. کلاس PaymentProcessor
**فایل:** `src/services/PaymentProcessor.java`

**تغییرات:**
- افزودن متد `onSitePayment(double amount)` برای پرداخت حضورى

**کد:**
```java
public void onSitePayment(double amount){ 
    System.out.println("Paid on-site: " + amount); 
}
```

#### ۲. کلاس ReservationService
**فایل:** `src/services/ReservationService.java`

**تغییرات:**
- افزودن case جدید `ONSITE` در switch statement مربوط به paymentType
- فراخوانی متد `onSitePayment()` برای پرداخت حضورى

**کد:**
```java
switch (paymentType){
    case CARD:
        paymentProcessor.payByCard(res.totalPrice());
        break;
    case PAYPAL:
        paymentProcessor.payByPayPal(res.totalPrice());
        break;
    case CASH:
        paymentProcessor.payByCash(res.totalPrice());
        break;
    case ONSITE:  // case جدید
        paymentProcessor.onSitePayment(res.totalPrice());
        break;
}
```

### جدول خلاصه تغییرات

| کلاس تغییر یافته | توضیح کوتاه در مورد تغییر |
|------------------|---------------------------|
| PaymentProcessor | افزودن یک تابع برای پرداخت حضورى به نام `onSitePayment` |
| ReservationService | افزودن case ONSITE در switch statement و فراخوانی `onSitePayment()` |

### کامیت
این قابلیت توسط هم‌تیمی در کامیت جداگانه اضافه خواهد شد.

---

## بخش ۳: تحلیل اصول شی‌گرایی (SOLID Principles)

### توضیحات
در این بخش، کد اولیه (پیش از تغییرات) از نظر رعایت یا نقض اصول SOLID بررسی و تحلیل می‌شود.

### اصول SOLID

1. **SRP (Single Responsibility Principle)**: اصل تک مسئولیتی - هر کلاس باید فقط یک دلیل برای تغییر داشته باشد
2. **OCP (Open-Closed Principle)**: اصل باز-بسته - کلاس‌ها باید برای گسترش باز و برای تغییر بسته باشند
3. **LSP (Liskov Substitution Principle)**: اصل جایگزینی لیسکوف - زیرکلاس‌ها باید بتوانند جایگزین کلاس والد شوند
4. **ISP (Interface Segregation Principle)**: اصل جداسازی واسط‌ها - کلاینت‌ها نباید به متدهایی که استفاده نمی‌کنند وابسته باشند
5. **DIP (Dependency Inversion Principle)**: اصل وارونگی وابستگی - وابستگی باید به انتزاع‌ها باشد نه پیاده‌سازی‌های مشخص
6. **PLK (Principle of Least Knowledge / Law of Demeter)**: اصل کمترین آگاهی - یک شی باید فقط با دوستان مستقیم خود ارتباط برقرار کند
7. **CRP (Common Reuse Principle)**: اصل استفاده مشترک - کلاس‌هایی که با هم استفاده می‌شوند باید در یک بسته قرار گیرند

### خلاصه تحلیل اصول SOLID

| اصل | تعداد موارد برقراری | تعداد موارد نقض |
|-----|---------------------|----------------|
| SRP | ۵ کلاس | ۱ کلاس (ReservationService) |
| OCP | ۱ کلاس (LuxuryRoom) | ۲ کلاس (ReservationService, PaymentProcessor) |
| LSP | ۱ مورد | ۱ مورد (پس از گسترش) |
| ISP | ۱ مورد (در کد اولیه) | ۱ مورد (پس از افزودن SMS) |
| DIP | ۰ مورد | ۱ کلاس (ReservationService) |
| PLK | ۱ مورد | ۱ مورد (ReservationService) |
| CRP | ۱ مورد | ۰ مورد |

### تحلیل تفصیلی اصول SOLID

در این بخش، کد اولیه (قبل از تغییرات Step 1) از نظر رعایت یا نقض اصول SOLID بررسی شده است.

| اصل | مورد | کلاس | علت برقراری / نقض |
|-----|------|------|-------------------|
| **SRP** | مورد برقراری | `Customer`, `Room`, `Reservation`, `PaymentProcessor`, `EmailSender` | این کلاس‌ها هر کدام یک مسئولیت مشخص دارند: Customer برای اطلاعات مشتری، Room برای اطلاعات اتاق، Reservation برای اطلاعات رزرو، PaymentProcessor برای پردازش پرداخت، و EmailSender برای ارسال ایمیل. |
| **SRP** | مورد نقض | `ReservationService` | این کلاس چندین مسئولیت دارد: ۱) پردازش رزرواسیون ۲) اعمال تخفیف شهر ۳) مدیریت پرداخت ۴) ارسال اعلان. برای افزودن هر قابلیت جدید (مثل SMS یا پرداخت جدید) باید این کلاس تغییر کند. |
| **OCP** | مورد برقراری | `LuxuryRoom` | این کلاس با ارث‌بری از Room، قابلیت‌های جدید (مثل addFreeDinner) را بدون تغییر کلاس والد اضافه می‌کند. |
| **OCP** | مورد نقض | `ReservationService` | برای افزودن روش پرداخت جدید یا روش ارسال پیام جدید، باید switch statement را تغییر داد. کلاس برای گسترش باز نیست و نیاز به تغییر دارد. |
| **OCP** | مورد نقض | `PaymentProcessor` | برای افزودن روش پرداخت جدید، باید متد جدید به کلاس اضافه شود. این کلاس برای گسترش باز نیست. |
| **LSP** | مورد برقراری | `LuxuryRoom` | LuxuryRoom می‌تواند در هر جایی که Room استفاده می‌شود جایگزین شود و رفتار صحیح را حفظ کند. |
| **LSP** | مورد نقض ۱ | `EmailSender` و `MessageSender` | اگرچه EmailSender پیاده‌سازی MessageSender است، اما در کد اولیه، اینترفیس MessageSender فقط sendEmail دارد که برای EmailSender مناسب است. اما اگر بخواهیم SmsSender اضافه کنیم، EmailSender باید متد sendSmsMessage را هم پیاده‌سازی کند که برای آن بی‌معنی است. |
| **LSP** | مورد نقض ۲ | - | در کد اولیه، نقض واضحی از LSP وجود ندارد، اما طراحی فعلی برای گسترش مشکل‌ساز است. |
| **ISP** | مورد برقراری | `MessageSender` (در کد اولیه) | در کد اولیه، اینترفیس MessageSender فقط یک متد دارد که برای EmailSender مناسب است. |
| **ISP** | مورد نقض | `MessageSender` (پس از افزودن SMS) | پس از افزودن قابلیت SMS، اینترفیس MessageSender شامل sendEmail و sendSmsMessage می‌شود. EmailSender مجبور است sendSmsMessage را پیاده‌سازی کند (حتی با بدنه خالی) و SmsSender مجبور است sendEmail را پیاده‌سازی کند. این نقض ISP است چون کلاینت‌ها به متدهایی وابسته می‌شوند که استفاده نمی‌کنند. |
| **DIP** | مورد برقراری | - | در کد اولیه، استفاده از اینترفیس MessageSender وجود دارد اما به درستی استفاده نشده است. |
| **DIP** | مورد نقض | `ReservationService` | ReservationService مستقیماً کلاس‌های مشخص PaymentProcessor و EmailSender را ایجاد می‌کند (خط ۸ و ۳۸). باید به انتزاع‌ها (اینترفیس‌ها) وابسته باشد نه پیاده‌سازی‌های مشخص. |
| **PLK** | مورد برقراری | `Reservation.totalPrice()` | متد totalPrice() از room.price استفاده می‌کند که دسترسی مستقیم به فیلد است، اما این در محدوده کلاس Reservation است. |
| **PLK** | مورد نقض | `ReservationService` | در خط ۱۱، ۱۳، ۱۵، ۳۲، ۳۹: دسترسی زنجیره‌ای به فیلدها مانند `res.customer.name`، `res.customer.city`، `res.customer.email`، `res.room.number`، `res.room.type`. این نقض قانون Demeter است چون ReservationService باید فقط با Reservation (دوست مستقیم) ارتباط برقرار کند، نه با Customer و Room. |
| **CRP** | مورد برقراری | بسته‌بندی کلاس‌ها | کلاس‌های مرتبط (models، services، constants) در بسته‌های مناسب قرار گرفته‌اند. |
| **CRP** | مورد نقض | - | در کد اولیه، نقض واضحی از CRP وجود ندارد. |

---

## بخش ۴: خلاصه و نتیجه‌گیری

### خلاصه تغییرات

در این پروژه، دو قابلیت جدید به سیستم رزرو هتل اضافه شد:

1. **قابلیت ارسال پیامک (SMS)**: با ایجاد کلاس `SmsSender` و به‌روزرسانی `MessageSender` و `ReservationService`
2. **قابلیت پرداخت حضورى (On-Site Payment)**: با افزودن متد `onSitePayment` به `PaymentProcessor` و به‌روزرسانی `ReservationService`

### مشکلات شناسایی شده

بر اساس تحلیل اصول SOLID، مشکلات زیر در کد اولیه شناسایی شد:

#### ۱. نقض SRP در ReservationService
کلاس `ReservationService` مسئولیت‌های متعددی دارد که باعث می‌شود برای هر تغییر کوچک نیاز به تغییر این کلاس باشد.

#### ۲. نقض OCP در ReservationService و PaymentProcessor
برای افزودن روش پرداخت یا ارسال پیام جدید، باید کد موجود تغییر کند. این کلاس‌ها برای گسترش باز نیستند.

#### ۳. نقض ISP در MessageSender
پس از افزودن SMS، کلاینت‌ها مجبور به پیاده‌سازی متدهایی می‌شوند که استفاده نمی‌کنند.

#### ۴. نقض DIP در ReservationService
این کلاس مستقیماً به پیاده‌سازی‌های مشخص (`PaymentProcessor` و `EmailSender`) وابسته است، نه به انتزاع‌ها.

#### ۵. نقض PLK (Law of Demeter) در ReservationService
دسترسی زنجیره‌ای به فیلدهای اشیاء دیگر (`res.customer.name`، `res.room.price` و غیره) که باعث وابستگی زیاد می‌شود.

### پیشنهادات بهبود

برای رفع مشکلات شناسایی شده، پیشنهادات زیر ارائه می‌شود:

#### ۱. رعایت SRP
- جداسازی مسئولیت‌های `ReservationService` به کلاس‌های جداگانه:
  - `DiscountCalculator`: برای محاسبه تخفیف‌ها
  - `NotificationService`: برای مدیریت ارسال اعلان‌ها
  - `ReservationService`: فقط برای هماهنگی فرآیند رزرو

#### ۲. رعایت OCP
- استفاده از Strategy Pattern برای روش‌های پرداخت
- استفاده از Factory Pattern برای ایجاد پردازشگرهای پرداخت
- استفاده از اینترفیس‌ها برای ارسال پیام

#### ۳. رعایت ISP
- جداسازی `MessageSender` به اینترفیس‌های کوچکتر:
  - `EmailSender` interface با متد `sendEmail()`
  - `SmsSender` interface با متد `sendSms()`

#### ۴. رعایت DIP
- استفاده از Dependency Injection
- وابستگی `ReservationService` به اینترفیس‌ها به جای کلاس‌های مشخص
- استفاده از اینترفیس `PaymentProcessor` به جای کلاس مشخص

#### ۵. رعایت PLK
- افزودن متدهای getter مناسب در کلاس‌های `Reservation`، `Customer` و `Room`
- کاهش دسترسی مستقیم به فیلدها
- استفاده از متدهای کلاس‌های دوست مستقیم

### نتیجه‌گیری

کد اولیه دارای چندین نقض از اصول SOLID است که باعث می‌شود افزودن قابلیت‌های جدید (مثل SMS و پرداخت حضورى) نیاز به تغییر کد موجود داشته باشد. این مشکلات در Step 1 به وضوح مشاهده شد. برای بهبود طراحی، باید از الگوهای طراحی مناسب و اصول SOLID استفاده شود تا سیستم برای گسترش باز و برای تغییر بسته باشد.

---

## نحوه اجرای پروژه

### پیش‌نیازها
- Java JDK 8 یا بالاتر
- Git

### اجرای برنامه
```bash
cd Step-01-without-OOD
javac -d out src/**/*.java
java -cp out Main
```

### تست قابلیت‌های جدید

#### تست ارسال پیامک
```java
service.makeReservation(res, PaymentMethods.PAYPAL, Notifier.SMS);
```

#### تست پرداخت حضورى
```java
service.makeReservation(res, PaymentMethods.ONSITE, Notifier.EMAIL);
```

---

## اطلاعات پروژه

- **نام آزمایش:** آزمایش دوم - اصول شی‌گرایی
- **درس:** مهندسی نرم‌افزار
- **ترم:** پاییز ۱۴۰۴
- **مخزن اصلی:** [base-project-for-solid](https://github.com/yahyaPoursoltani/base-project-for-solid)

---

## مشارکت اعضای گروه

- **عضو ۱:** [نام] - قابلیت ارسال پیامک (SMS)
- **عضو ۲:** [نام] - قابلیت پرداخت حضورى (On-Site Payment)

---

## تاریخچه تغییرات

- **تاریخ:** [تاریخ] - افزودن قابلیت ارسال پیامک
- **تاریخ:** [تاریخ] - افزودن قابلیت پرداخت حضورى
- **تاریخ:** [تاریخ] - تحلیل اصول SOLID

