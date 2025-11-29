# به نام خدا

# آشنایی با اصول شی‌گرایی (موسوم به اصول SOLID)

## اهداف

در این آزمایش هدف بر آن است که دانشجویان با به کارگیری اصول SOLID در یک پروژه ی عملی ساده آشنا شوند. این پروژه شامل یک سیستم رزرو هتل است که در مراحل مختلف، قابلیت‌های جدید به آن اضافه می‌شود و سپس کد از نظر رعایت اصول SOLID بررسی و اصلاح می‌شود.

## نیازمندی‌ها

آشنایی اولیه با مفاهیم برنامه نویسی و طراحی شی‌گرا که دانشجویان قبلاً در درس برنامه سازی پیشرفته با آن آشنا شده‌اند.

## ابزارهای مورد استفاده

- یک Java IDE مانند IntelliJ IDEA و یا Eclipse به همراه JDK حداقل نسخه ۸
- Git و GitHub برای مدیریت نسخه و همکاری گروهی

## منابع آموزشی

- اصول SOLID در برنامه‌نویسی شی‌گرا
- الگوهای طراحی (Design Patterns)
- Dependency Injection

---

## معرفی پروژه

این پروژه یک سیستم رزرو هتل است که برای آموزش و تمرین اصول SOLID طراحی شده است. در این آزمایش، ابتدا قابلیت‌های جدیدی به سیستم اضافه می‌شود و سپس کد از نظر رعایت اصول SOLID بررسی و تحلیل می‌شود.

### ساختار سیستم اولیه

سیستم شامل کلاس‌های زیر است:
- **Customer**: اطلاعات مشتری
- **Room** و **LuxuryRoom**: اطلاعات اتاق‌ها
- **Reservation**: اطلاعات رزرواسیون
- **ReservationService**: سرویس اصلی رزرواسیون
- **PaymentProcessor**: پردازش پرداخت
- **MessageSender** و **EmailSender**: ارسال پیام و ایمیل

### ساختار پوشه‌ها

- **base-project-for-solid/**: پروژه اولیه (کد پایه)
- **Step-01-without-OOD/**: خروجی گام اول (افزودن قابلیت‌های جدید بدون رعایت اصول SOLID)
- **Step-02-with-OOD/**: خروجی گام سوم (اصلاح موارد نقض و رعایت اصول SOLID)

---

## گام اول: افزودن دو قابلیت جدید

در این گام، دو قابلیت جدید به سیستم اضافه شده است:

### ۱. افزودن قابلیت ارسال پیامک (SMS)

#### توضیحات
در این بخش، قابلیت ارسال پیامک به سیستم اضافه شده است. این کار با ایجاد کلاس `SmsSender` مشابه `EmailSender` انجام شده است.

#### تغییرات اعمال شده

**الف) کلاس MessageSender**
- **فایل:** `Step-01-without-OOD/src/services/MessageSender.java`
- **تغییرات:** افزودن متد `sendSmsMessage(String to, String message)` به اینترفیس

```java
public interface MessageSender {
    public void sendEmail(String to, String message);
    public void sendSmsMessage(String to, String message);  // متد جدید
}
```

**ب) کلاس EmailSender**
- **فایل:** `Step-01-without-OOD/src/services/EmailSender.java`
- **تغییرات:** افزودن پیاده‌سازی متد `sendSmsMessage()` (بدنه خالی)

```java
public void sendSmsMessage(String to, String message){
    // EmailSender doesn't send SMS
}
```

**ج) کلاس SmsSender (جدید)**
- **فایل:** `Step-01-without-OOD/src/services/SmsSender.java`
- **تغییرات:** ایجاد کلاس جدید برای ارسال پیامک

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

**د) کلاس ReservationService**
- **فایل:** `Step-01-without-OOD/src/services/ReservationService.java`
- **تغییرات:** 
  - افزودن case جدید `SMS` در switch statement
  - اصلاح باگ: تغییر `switch (this.notifier)` به `switch (notifier)`

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

#### جدول خلاصه تغییرات

| کلاس تغییر یافته | توضیح کوتاه در مورد تغییر |
|------------------|---------------------------|
| MessageSender | افزودن یک تابع برای ارسال پیامک به نام `sendSmsMessage` |
| EmailSender | افزودن پیاده‌سازی متد `sendSmsMessage()` (بدنه خالی) |
| SmsSender | ایجاد کلاس جدید برای ارسال پیامک |
| ReservationService | افزودن case SMS در switch statement و استفاده از SmsSender |

---

### ۲. افزودن روش پرداخت حضورى (On-Site Payment)

#### توضیحات
در این بخش، روش پرداخت حضورى به سیستم اضافه شده است. این کار با افزودن متد جدید به کلاس `PaymentProcessor` انجام شده است.

#### تغییرات اعمال شده

**الف) کلاس PaymentProcessor**
- **فایل:** `Step-01-without-OOD/src/services/PaymentProcessor.java`
- **تغییرات:** افزودن متد `onSitePayment(double amount)` برای پرداخت حضورى

```java
public void onSitePayment(double amount){ 
    System.out.println("Paid on-site: " + amount); 
}
```

**ب) کلاس ReservationService**
- **فایل:** `Step-01-without-OOD/src/services/ReservationService.java`
- **تغییرات:** افزودن case جدید `ONSITE` در switch statement

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

#### جدول خلاصه تغییرات

| کلاس تغییر یافته | توضیح کوتاه در مورد تغییر |
|------------------|---------------------------|
| PaymentProcessor | افزودن یک تابع برای پرداخت حضورى به نام `onSitePayment` |
| ReservationService | افزودن case ONSITE در switch statement و فراخوانی `onSitePayment()` |

---

## گام دوم: تحلیل اصول شی‌گرایی (SOLID Principles)

در این بخش، کد اولیه (پیش از تغییرات) از نظر رعایت یا نقض اصول SOLID بررسی و تحلیل شده است.

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

## گام سوم: اصلاح موارد نقض

در این گام، کد اولیه بازنویسی شده و تمام موارد نقض اصول SOLID که در گام دوم شناسایی شدند، اصلاح شده‌اند. هدف از این بازنویسی، ایجاد کدی است که اصول شی‌گرایی را رعایت کند و برای گسترش باز و برای تغییر بسته باشد.

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
- **فایل:** `Step-02-with-OOD/src/services/DiscountCalculator.java`

**ب) کلاس InvoiceGenerator**
- **مسئولیت:** تولید و نمایش فاکتور
- **فایل:** `Step-02-with-OOD/src/services/InvoiceGenerator.java`

**ج) کلاس NotificationService**
- **مسئولیت:** مدیریت ارسال اعلان‌ها
- **فایل:** `Step-02-with-OOD/src/services/NotificationService.java`

**نتیجه:** حالا `ReservationService` فقط مسئول هماهنگی فرآیند رزرو است و هر کلاس یک مسئولیت مشخص دارد.

---

### ۲. اصلاح نقض OCP (Open-Closed Principle)

#### مشکل شناسایی شده:
- برای افزودن روش پرداخت جدید، باید `PaymentProcessor` و `ReservationService` تغییر کنند
- برای افزودن روش ارسال پیام جدید، باید `ReservationService` تغییر کند

#### راه حل:
استفاده از **Strategy Pattern** برای روش‌های پرداخت:

**الف) اینترفیس PaymentStrategy**
- **فایل:** `Step-02-with-OOD/src/services/PaymentStrategy.java`

**ب) کلاس‌های پیاده‌سازی Strategy**
- `CardPayment.java` - پرداخت با کارت
- `CashPayment.java` - پرداخت نقدی
- `PayPalPayment.java` - پرداخت با PayPal

**ج) کلاس PaymentProcessorFactory**
- **فایل:** `Step-02-with-OOD/src/services/PaymentProcessorFactory.java`

**نتیجه:** برای افزودن روش پرداخت جدید (مثل OnSitePayment)، فقط نیاز به ایجاد کلاس جدید و افزودن case در Factory است. نیازی به تغییر کد موجود نیست.

---

### ۳. اصلاح نقض ISP (Interface Segregation Principle)

#### مشکل شناسایی شده:
اینترفیس `MessageSender` شامل متدهای `sendEmail` و `sendSmsMessage` بود که باعث می‌شد کلاینت‌ها مجبور به پیاده‌سازی متدهایی شوند که استفاده نمی‌کنند.

#### راه حل:
جداسازی اینترفیس‌ها به اینترفیس‌های کوچکتر و متمرکز:

**الف) اینترفیس EmailNotifier**
- **فایل:** `Step-02-with-OOD/src/services/EmailNotifier.java`

**ب) اینترفیس SmsNotifier**
- **فایل:** `Step-02-with-OOD/src/services/SmsNotifier.java`

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
- استفاده از constructor injection
- وابستگی به اینترفیس‌ها به جای کلاس‌های مشخص

**ب) Dependency Injection در Main:**
- ایجاد نمونه‌های کلاس‌ها و تزریق آن‌ها به `ReservationService`

**نتیجه:** `ReservationService` به انتزاع‌ها وابسته است و می‌تواند با هر پیاده‌سازی از اینترفیس‌ها کار کند.

---

### ۵. اصلاح نقض PLK (Principle of Least Knowledge / Law of Demeter)

#### مشکل شناسایی شده:
دسترسی زنجیره‌ای به فیلدها مانند `res.customer.name`، `res.room.price` و غیره.

#### راه حل:
افزودن متدهای getter و استفاده از آن‌ها به جای دسترسی مستقیم به فیلدها:

**الف) کلاس Customer:**
- Private کردن فیلدها
- افزودن getter methods: `getName()`, `getEmail()`, `getCity()`, `getMobile()`

**ب) کلاس Room:**
- Private کردن فیلدها
- افزودن getter/setter methods: `getNumber()`, `getType()`, `getPrice()`, `setPrice()`

**ج) کلاس Reservation:**
- Private کردن فیلدها
- افزودن getter methods: `getRoom()`, `getCustomer()`, `getNights()`

**نتیجه:** `ReservationService` فقط با دوستان مستقیم خود (`Reservation`) ارتباط برقرار می‌کند و دسترسی زنجیره‌ای حذف شده است.

---

### خلاصه تغییرات گام سوم

#### کلاس‌های جدید ایجاد شده:

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

#### کلاس‌های حذف شده:

1. **PaymentProcessor** - جایگزین با Strategy Pattern
2. **MessageSender** - جایگزین با EmailNotifier و SmsNotifier

#### کلاس‌های تغییر یافته:

1. **ReservationService** - بازنویسی کامل با استفاده از Dependency Injection
2. **Customer** - افزودن getter methods و private کردن فیلدها
3. **Room** - افزودن getter/setter methods و private کردن فیلدها
4. **Reservation** - افزودن getter methods و private کردن فیلدها
5. **EmailSender** - تغییر به پیاده‌سازی EmailNotifier
6. **LuxuryRoom** - استفاده از getter methods
7. **Main** - استفاده از Dependency Injection

---

## گام چهارم: خلاصه و نتیجه‌گیری

### خلاصه تغییرات

در این پروژه، دو قابلیت جدید به سیستم رزرو هتل اضافه شد:

1. **قابلیت ارسال پیامک (SMS)**: با ایجاد کلاس `SmsSender` و به‌روزرسانی `MessageSender` و `ReservationService`
2. **قابلیت پرداخت حضورى (On-Site Payment)**: با افزودن متد `onSitePayment` به `PaymentProcessor` و به‌روزرسانی `ReservationService`

### مشکلات شناسایی شده

بر اساس تحلیل اصول SOLID، مشکلات زیر در کد اولیه شناسایی شد:

1. **نقض SRP در ReservationService**: کلاس `ReservationService` مسئولیت‌های متعددی داشت که باعث می‌شود برای هر تغییر کوچک نیاز به تغییر این کلاس باشد.

2. **نقض OCP در ReservationService و PaymentProcessor**: برای افزودن روش پرداخت یا ارسال پیام جدید، باید کد موجود تغییر کند. این کلاس‌ها برای گسترش باز نیستند.

3. **نقض ISP در MessageSender**: پس از افزودن SMS، کلاینت‌ها مجبور به پیاده‌سازی متدهایی می‌شوند که استفاده نمی‌کنند.

4. **نقض DIP در ReservationService**: این کلاس مستقیماً به پیاده‌سازی‌های مشخص (`PaymentProcessor` و `EmailSender`) وابسته است، نه به انتزاع‌ها.

5. **نقض PLK (Law of Demeter) در ReservationService**: دسترسی زنجیره‌ای به فیلدهای اشیاء دیگر (`res.customer.name`، `res.room.price` و غیره) که باعث وابستگی زیاد می‌شود.

### پیشنهادات بهبود

برای رفع مشکلات شناسایی شده، پیشنهادات زیر ارائه شد و در گام سوم اعمال شد:

1. **رعایت SRP**: جداسازی مسئولیت‌های `ReservationService` به کلاس‌های جداگانه (DiscountCalculator، InvoiceGenerator، NotificationService)

2. **رعایت OCP**: استفاده از Strategy Pattern برای روش‌های پرداخت و Factory Pattern برای ایجاد پردازشگرهای پرداخت

3. **رعایت ISP**: جداسازی `MessageSender` به اینترفیس‌های کوچکتر (EmailNotifier و SmsNotifier)

4. **رعایت DIP**: استفاده از Dependency Injection و وابستگی `ReservationService` به اینترفیس‌ها به جای کلاس‌های مشخص

5. **رعایت PLK**: افزودن متدهای getter مناسب در کلاس‌های `Reservation`، `Customer` و `Room` و کاهش دسترسی مستقیم به فیلدها

### نتیجه‌گیری

کد اولیه دارای چندین نقض از اصول SOLID بود که باعث می‌شود افزودن قابلیت‌های جدید (مثل SMS و پرداخت حضورى) نیاز به تغییر کد موجود داشته باشد. این مشکلات در گام اول به وضوح مشاهده شد.

در گام سوم، با اعمال اصلاحات و استفاده از الگوهای طراحی مناسب، تمام موارد نقض برطرف شدند:

✅ **SRP**: هر کلاس یک مسئولیت مشخص دارد  
✅ **OCP**: سیستم برای گسترش باز و برای تغییر بسته است  
✅ **LSP**: زیرکلاس‌ها می‌توانند جایگزین کلاس والد شوند  
✅ **ISP**: اینترفیس‌ها کوچک و متمرکز هستند  
✅ **DIP**: وابستگی به انتزاع‌ها است نه پیاده‌سازی‌ها  
✅ **PLK**: دسترسی فقط از طریق دوستان مستقیم  
✅ **CRP**: کلاس‌های مرتبط در بسته‌های مناسب قرار دارند  

کد حالا قابل نگهداری‌تر، قابل گسترش‌تر و قابل تست‌تر است.

---

## نحوه اجرای پروژه

### پیش‌نیازها
- Java JDK 8 یا بالاتر
- Git

### اجرای برنامه

#### گام اول (Step-01-without-OOD):
```bash
cd Step-01-without-OOD
mkdir -p out
javac -d out src/**/*.java
java -cp out Main
```

#### گام سوم (Step-02-with-OOD):
```bash
cd Step-02-with-OOD
mkdir -p out
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

- **عضو ۱:** آریان انصاری - قابلیت ارسال پیامک (SMS)
- **عضو ۲:** حامد صبور - قابلیت پرداخت حضورى (On-Site Payment)

---

## تاریخچه تغییرات

- **تاریخ:** ۷/۹/۱۴۰۴ - افزودن قابلیت ارسال پیامک
- **تاریخ:** ۷/۹/۱۴۰۴ - افزودن قابلیت پرداخت حضورى
- **تاریخ:** ۷/۹/۱۴۰۴ - تحلیل اصول SOLID
- **تاریخ:** ۷/۹/۱۴۰۴ - اصلاح موارد نقض و رعایت اصول SOLID

