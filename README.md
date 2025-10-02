Rnt Social Api — بررسی کد (code Review)
· java
# RNT_Social_Api — بررسی کد (Code Review)


**هدف:** بررسی مرحله‌به‌مرحله‌ی پروژه‌ی `RNT_Social_Api` و ثبت نکات فنی، مشکلات امنیتی، پیشنهادهای بهبود و تیک‌لیست‌های انجام‌شده.


---


## نمای کلی مخزن (snapshot)
- زبان اصلی: Java
- شاخه پیش‌فرض: `main`
- فایل‌ها/پوشه‌های سطح بالا (توصیه می‌کنم ابتدا اینها را بررسی کنیم):
  - `pom.xml`
  - `Dockerfile`, `docker-compose.yaml`, `docker-compose-develop.yaml`
  - `src/` (کد اصلی: controllers, services, models, config...)
  - `.github/workflows` (CI)
  - `logs/` و `LOG_PATH_IS_UNDEFINED`
  - `.mvn/`, `mvnw`, `mvnw.cmd`


---


## ترتیب پیشنهادی بررسی (مرحله به مرحله)
1. **پیکربندی و وابستگی‌ها** — `pom.xml` (نسخه جاوا، dependencyها، pluginها، dependencyManagement، نسخه‌های امن)
2. **دایره‌ی اجرا / کانتینر** — `Dockerfile` و `docker-compose*` (سایز تصویر، لایه‌ها، secrets، healthcheck)
3. **ساختار پروژه و نام‌گذاری** — ساختار `src/main/java` (پکیج‌ها، نام‌گذاری کلاس‌ها، جداشدن لایه‌ها)
4. **لایه‌ی وب / API** — controllers (روت‌ها، ورودی/خروجی DTOها، ولیدیشن، مدیریت خطا)
5. **لایه‌ی سرویس و منطق کسب‌و‌کار** — سرویس‌ها، تراکنش‌ها، تعامل با دیتابیس
6. **دسترسی به داده‌ها** — repositories/DAOs (پرس‌وجوها، SQL ایمن، paging)
7. **پیکربندی و secrets** — application.properties/yml، مدیریت کلیدها و رمزها
8. **لاگینگ و مانیتورینگ** — logger config، مسیر لاگ، سطح لاگینگ، اطلاعات حساس
9. **تست‌ها و CI** — تست‌های واحد/انتگرشن، workflowهای GitHub Actions
10. **مسائل امنیتی** — auth/authz، JWT/session، rate limiting، input sanitization


---


## تیک‌لیستِ هر بخش (نمونه — برای `pom.xml`)
- [ ] بررسی نسخه‌های dependency (آیا قدیمی/دارای CVE هستند؟)
- [ ] استفاده از نسخه‌بندی ثابت به‌جای `LATEST`
- [ ] pluginهای build (shade, maven-compiler-plugin) تنظیم شده؟
- [ ] properties برای نسخهٔ جاوا (`maven.compiler.source/target`)
- [ ] آیا dependency scope درست تنظیم شده؟ (test, provided...)



