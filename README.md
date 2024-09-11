
# Online Ticketing System

## Genel Bakış
Bu proje, uçaklar ve otobüsler için çevrimiçi biletleme sistemine dayalı bir mikroservis tabanlı projedir. Proje üç ana hizmetten oluşmaktadır:

- **Email Servisi**: Kullanıcılara e-posta bildirimlerini yönetir.
- **Trip Servisi**: Yolculukları yönetir; yolculuk oluşturma, güncelleme ve mevcut yolculukları arama işlemlerini içerir.
- **User Servisi**: Kullanıcı bilgilerini yönetir; kayıt, giriş ve rol tabanlı erişim kontrolünü içerir.

## Mikroservisler

### 1. Email Servisi
- **Ana Sınıf**: `EmailServiceApplication.java`
- **Sorumluluklar**:
  - Kullanıcı kaydı veya bilet rezervasyonundan sonra e-posta bildirimleri gönderir.
- **Önemli Dizinler**:
  - `controller`: E-posta işlemleri için gelen HTTP isteklerini yönetir.
  - `entity`: Veritabanı modellerini temsil eden varlıkları içerir.
  - `service`: E-posta gönderme iş mantığını uygular.

### 2. Trip Servisi
- **Ana Sınıf**: `TripManagementServiceApplication.java`
- **Sorumluluklar**:
  - Uçaklar ve otobüsler için yolculukları yönetir; yolculuk oluşturma, güncelleme ve silme işlemlerini içerir.
  - Mevcut yolculukları aramak için API'lar sağlar.
- **Önemli Dizinler**:
  - `controller`: Yolculuk işlemleri ile ilgili gelen HTTP isteklerini yönetir.
  - `entity`: Veritabanı modellerini temsil eden varlıkları içerir.
  - `service`: Yolculukları yönetme iş mantığını uygular.
  - `repository`: Veritabanı ile etkileşimi sağlayan arayüzler.
  - `client`: Diğer mikroservislerle iletişimi yönetir.
  - `exception`: Hata yönetimi için özel istisnalar.
  - `utils`: Ortak işlevler için yardımcı sınıflar.

### 3. User Servisi
- **Ana Sınıf**: `UserServiceApplication.java`
- **Sorumluluklar**:
  - Kullanıcı kaydı, giriş ve rol tabanlı erişim kontrolünü yönetir.
  - Kullanıcı yönetimi için API'lar sağlar.
- **Önemli Dizinler**:
  - `controller`: Kullanıcı işlemleri ile ilgili gelen HTTP isteklerini yönetir.
  - `entity`: Veritabanı modellerini temsil eden varlıkları içerir.
  - `service`: Kullanıcıları yönetme iş mantığını uygular.
  - `repository`: Veritabanı ile etkileşimi sağlayan arayüzler.
  - `utils`: Ortak işlevler için yardımcı sınıflar.

## Kurulum ve Kurulum

### Gereksinimler
- Java 17 veya üzeri
- Maven 3.6+
- PostgreSQL veya MySQL

### Projeyi Çalıştırma Adımları
1. **Depoyu Klonlayın**:
    ```bash
    git clone <repository-url>
    cd FT-Final-Case
    ```

2. **Veritabanı Kurulumu**:
    - Her hizmet için PostgreSQL veya MySQL veritabanı oluşturun.
    - Her servisteki `application.properties` veya `application.yml` dosyalarını veritabanı bilgilerinize göre güncelleyin.

3. **Her Hizmeti Derleyin ve Çalıştırın**:
    - Her servis dizinine (`email-service`, `trip-service`, `user-service`) gidin ve şu komutları çalıştırın:
      ```bash
      mvn clean install
      mvn spring-boot:run
      ```

4. **API'lara Erişim**:
    - Servislere şu adreslerden erişebilirsiniz:
      - Email Servisi: `http://localhost:8081`
      - Trip Servisi: `http://localhost:8082`
      - User Servisi: `http://localhost:8083`

## API Dokümantasyonu
Her servis, etkileşim için bir dizi RESTful API sağlar. Aşağıda ana uç noktalar belirtilmiştir:

### Email Servisi
- `POST /send-email`: Sağlanan bilgilere göre e-posta gönderir.

### Trip Servisi
- `GET /trips`: Mevcut yolculukların bir listesini döner.
- `POST /trips`: Yeni bir yolculuk oluşturur.
- `PUT /trips/{id}`: Mevcut bir yolculuğu günceller.
- `DELETE /trips/{id}`: Bir yolculuğu siler.

### User Servisi
- `POST /register`: Yeni bir kullanıcı kaydeder.
- `POST /login`: Bir kullanıcıyı doğrular ve bir JWT döner.
- `GET /users`: Kullanıcıların bir listesini döner.
- `PUT /users/{id}`: Kullanıcı bilgilerini günceller.
- `DELETE /users/{id}`: Bir kullanıcıyı siler.

