İlk olarak PostgreSql ile çalıştığım için docker-compose.yml çalıştırmanız gerekmektedir.

# Docker Dosyası Çalıştırma

Terminal üzerinden "docker-compose up" çalıştırınız.

Ardından proje 8082 portunda ayağa kalkacaktır.

# Proje İçerisindeki Katman Bilgileri

# 1. Adapter Katmanı
Hexagonal mimaride, sistemin dış dünya ile etkileşim kurduğu yerdir.
Sistem dışındaki giriş (inbound) ve çıkış (outbound) noktalarını yönetir.

**Alt Dizini: inbound.web**
  - **CustomerController**: Giriş noktalarını temsil eder. Genelde HTTP isteklerini (REST API) karşılar.
**Inbound (Giriş)**, dış dünyadan (örn. kullanıcı, başka sistemler) gelen talepleri uygulama katmanına iletir.

**Alt Dizini: outbound.persistence**
  - **CustomerEntity:** Veritabanındaki müşteri tablosuna karşılık gelen bir JPA (veya ORM) nesnesidir.Hibernate gibi araçlar tarafından kullanılmak üzere tasarlanmıştır.
  - **CustomerMapper:** CustomerEntity (veritabanı modeli) ile Customer (domain modeli) arasında dönüşüm sağlar. Hexagonal mimaride, domain ile veri kaynağı arasındaki bağımlılığı en aza indirmek için önemlidir.
  - **CustomerPersistenceAdapter:**Veritabanı ile iletişim kuran ve domain katmanına uygun veriler sağlayan adaptördür.Persistence (kalıcılık) işlemlerini yönetir (örneğin, kaydetme, silme, güncelleme).

# 2. Application Katmanı
İş mantığının bulunduğu yerdir. Hexagonal mimaride çekirdek olarak adlandırılabilir.
CustomerService gibi uygulama iş akışlarını yöneten servis sınıfları bulunur.
Adaptörler tarafından çağrılır, domain katmanı ile adaptörler arasında köprü görevi görür.

  - **CustomerService:** İş mantığını içerir. Örneğin:
- Yeni bir müşteri oluşturma.
- Müşteriye ait sipariş tamamlama ve e-posta gönderme.
- Veritabanı veya diğer dış sistemlerle direkt iletişim kurmaz. Bunun yerine adaptörleri kullanır.

# 3. Domain Katmanı
Sistemin iş kurallarını ve temel mantığını içerir. Hexagonal mimarinin kalbi olarak görülebilir.
Bağımsız bir şekilde tasarlanır; başka bir framework, kütüphane veya sistemden etkilenmez.

  - **Customer:** Müşteri bilgilerini temsil eden domain sınıfıdır. İş kuralları burada yer alabilir. Veri kaynağı, servis, veya diğer sistemlerle ilgilenmez. Sadece saf iş mantığı içerir.

# 4. Infrastructure Katmanı
Uygulamanın çalışması için gerekli teknik altyapıyı temsil eder.
Veritabanı erişimi gibi düşük seviye detaylar burada bulunur.

  - **CustomerRepository:**  Spring Data JPA'nın sağladığı bir arabirimdir. Veritabanında CRUD (oluşturma, okuma, güncelleme, silme) işlemleri yapılmasını sağlar. Domain ya da servis katmanından direkt kullanılmaz, CustomerPersistenceAdapter tarafından çağrılır.
