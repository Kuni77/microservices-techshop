# Projet E-Commerce Microservices - TechShop

## 🎯 Objectif du Projet
Créer une plateforme e-commerce complète utilisant une architecture microservices réactive avec Spring WebFlux, découverte de services via Eureka, communication asynchrone avec RabbitMQ, recherche avancée avec Elasticsearch, et sécurité JWT.

## 🏗️ Architecture Globale

### Services Principaux
1. **API Gateway** (Spring Cloud Gateway)
2. **Service Discovery** (Eureka Server)
3. **User Service** (Gestion des utilisateurs)
4. **Product Service** (Catalogue produits)
5. **Order Service** (Gestion des commandes)
6. **Payment Service** (Traitement des paiements)
7. **Notification Service** (Notifications email/SMS)
8. **Search Service** (Recherche avec Elasticsearch)

## 📋 Détail des Services

### 1. Eureka Server (Service Discovery)
```yaml
# application.yml
server:
  port: 8761
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

**Fonctionnalités :**
- Enregistrement automatique des services
- Health checks
- Load balancing

### 2. API Gateway
**Port :** 8080  
**Technologies :** Spring Cloud Gateway, JWT Security

**Fonctionnalités :**
- Routage des requêtes vers les microservices
- Authentification JWT centralisée
- Rate limiting
- CORS configuration
- Swagger UI centralisé

**Routes principales :**
```
/api/auth/**     → User Service
/api/users/**    → User Service  
/api/products/** → Product Service
/api/orders/**   → Order Service
/api/payments/** → Payment Service
/api/search/**   → Search Service
```

### 3. User Service
**Port :** 8081  
**Base de données :** PostgreSQL  
**Technologies :** Spring WebFlux, R2DBC, JWT, BCrypt

**Endpoints :**
- `POST /api/auth/register` - Inscription utilisateur
- `POST /api/auth/login` - Connexion (retourne JWT)
- `POST /api/auth/refresh` - Refresh token
- `GET /api/users/profile` - Profil utilisateur
- `PUT /api/users/profile` - Mise à jour profil
- `GET /api/users/{id}` - Détails utilisateur (ADMIN)

**Entités :**
```java
@Table("users")
public class User {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role; // USER, ADMIN
    private LocalDateTime createdAt;
    private boolean enabled;
}
```

**Fonctionnalités :**
- Authentification JWT
- Hash des mots de passe (BCrypt)
- Rôles utilisateurs
- Validation email
- Profils utilisateur

### 4. Product Service
**Port :** 8082  
**Base de données :** MongoDB  
**Technologies :** Spring WebFlux, Spring Data MongoDB Reactive

**Endpoints :**
- `GET /api/products` - Liste des produits (pagination, filtres)
- `GET /api/products/{id}` - Détail produit
- `POST /api/products` - Créer produit (ADMIN)
- `PUT /api/products/{id}` - Modifier produit (ADMIN)
- `DELETE /api/products/{id}` - Supprimer produit (ADMIN)
- `GET /api/products/category/{category}` - Produits par catégorie

**Entités :**
```java
@Document("products")
public class Product {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private List<String> images;
    private Integer stock;
    private Double rating;
    private List<String> tags;
    private LocalDateTime createdAt;
    private boolean active;
}
```

**Fonctionnalités :**
- CRUD complet des produits
- Gestion du stock
- Catégorisation
- Upload d'images
- Système de notation

### 5. Order Service
**Port :** 8083  
**Base de données :** PostgreSQL  
**Technologies :** Spring WebFlux, R2DBC

**Endpoints :**
- `POST /api/orders` - Créer commande
- `GET /api/orders` - Mes commandes
- `GET /api/orders/{id}` - Détail commande
- `PUT /api/orders/{id}/cancel` - Annuler commande
- `GET /api/orders/admin` - Toutes les commandes (ADMIN)

**Entités :**
```java
@Table("orders")
public class Order {
    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private OrderStatus status; // PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    private LocalDateTime createdAt;
    private String shippingAddress;
}

@Table("order_items")
public class OrderItem {
    private Long id;
    private Long orderId;
    private String productId;
    private Integer quantity;
    private BigDecimal unitPrice;
}
```

**Fonctionnalités :**
- Création de commandes
- Calcul automatique des totaux
- Gestion des statuts
- Historique des commandes

### 6. Payment Service
**Port :** 8084  
**Base de données :** PostgreSQL  
**Technologies :** Spring WebFlux, R2DBC

**Endpoints :**
- `POST /api/payments/process` - Traiter paiement
- `GET /api/payments/{orderId}` - Statut paiement
- `POST /api/payments/refund/{paymentId}` - Remboursement (ADMIN)

**Entités :**
```java
@Table("payments")
public class Payment {
    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private PaymentMethod method; // CARD, PAYPAL, BANK_TRANSFER
    private PaymentStatus status; // PENDING, SUCCESS, FAILED, REFUNDED
    private String transactionId;
    private LocalDateTime processedAt;
}
```

### 7. Search Service
**Port :** 8085  
**Base de données :** Elasticsearch  
**Technologies :** Spring WebFlux, Spring Data Elasticsearch

**Endpoints :**
- `GET /api/search/products?q={query}` - Recherche produits
- `GET /api/search/suggestions?q={query}` - Auto-complétion
- `POST /api/search/index/products` - Réindexer produits (ADMIN)

**Fonctionnalités :**
- Recherche full-text
- Filtres avancés (prix, catégorie, note)
- Auto-complétion
- Recherche facettée
- Analytics de recherche

### 8. Notification Service
**Port :** 8086  
**Technologies :** Spring WebFlux, RabbitMQ, JavaMail

**Fonctionnalités :**
- Notifications par email
- Templates d'emails
- Queue RabbitMQ pour les notifications asynchrones
- Historique des notifications

## 🔄 Communication Inter-Services

### RabbitMQ Queues
- `user.registered` - Nouvel utilisateur inscrit
- `order.created` - Nouvelle commande
- `order.status.changed` - Changement statut commande
- `payment.processed` - Paiement traité
- `product.updated` - Produit mis à jour (pour Elasticsearch)

### Events
```java
// Exemple d'event
public class OrderCreatedEvent {
    private Long orderId;
    private Long userId;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}
```

## 🔒 Sécurité JWT

### Configuration
- **Secret Key** : Partagée entre tous les services
- **Expiration** : 1 heure pour access token, 7 jours pour refresh token
- **Claims** : userId, email, roles

### Flow d'authentification
1. Login → User Service génère JWT
2. Chaque requête → API Gateway valide JWT
3. JWT contient les infos utilisateur (pas besoin de re-query)

## 📊 Swagger/OpenAPI

Chaque service expose sa documentation Swagger :
- User Service : `http://localhost:8081/swagger-ui.html`
- Product Service : `http://localhost:8082/swagger-ui.html`
- etc.

API Gateway agrège toutes les docs : `http://localhost:8080/swagger-ui.html`

## 🗄️ Structure des Données

### Bases de données
- **PostgreSQL** : User Service, Order Service, Payment Service
- **MongoDB** : Product Service
- **Elasticsearch** : Search Service
- **Redis** : Cache, sessions

## 🐳 Déploiement Docker

```yaml
# docker-compose.yml
version: '3.8'
services:
  eureka-server:
    build: ./eureka-server
    ports:
      - "8761:8761"
  
  api-gateway:
    build: ./api-gateway
    ports:
      - "8080:8080"
    depends_on:
      - eureka-server
  
  rabbitmq:
    image: rabbitmq:3-management
    ports:
      - "5672:5672"
      - "15672:15672"
  
  elasticsearch:
    image: elasticsearch:8.5.0
    ports:
      - "9200:9200"
    environment:
      - discovery.type=single-node
  
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: techshop
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
  
  mongodb:
    image: mongo:6
    ports:
      - "27017:27017"
  
  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"
```

## 📈 Évolutions et Fonctionnalités Avancées

### Phase 1 (Base)
- ✅ Services de base
- ✅ Authentification JWT
- ✅ CRUD opérations
- ✅ Communication RabbitMQ

### Phase 2 (Intermédiaire)
- Circuit Breaker (Resilience4j)
- Monitoring (Micrometer + Prometheus)
- Logging centralisé (ELK Stack)
- Tests d'intégration

### Phase 3 (Avancée)
- Event Sourcing
- CQRS pattern
- Distributed tracing (Zipkin)
- Kubernetes deployment

## 🧪 Tests

### Types de tests
- **Unit tests** : JUnit 5 + Mockito
- **Integration tests** : @SpringBootTest avec TestContainers
- **Contract tests** : Spring Cloud Contract
- **E2E tests** : WebTestClient

### Exemple de test
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceIntegrationTest {
    
    @Autowired
    private WebTestClient webTestClient;
    
    @Test
    void shouldCreateProduct() {
        Product product = new Product("Test Product", new BigDecimal("99.99"));
        
        webTestClient.post()
            .uri("/api/products")
            .bodyValue(product)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Product.class)
            .value(p -> assertThat(p.getName()).isEqualTo("Test Product"));
    }
}
```

## 🚀 Plan d'Apprentissage

### Semaine 1-2 : Setup et bases
- Configuration Eureka Server
- API Gateway avec routage de base
- User Service avec JWT

### Semaine 3-4 : Services métier
- Product Service (MongoDB)
- Order Service (PostgreSQL)
- Communication RabbitMQ

### Semaine 5-6 : Recherche et paiement
- Search Service (Elasticsearch)
- Payment Service
- Notification Service

### Semaine 7-8 : Intégration et tests
- Tests complets
- Documentation Swagger
- Déploiement Docker

Ce projet vous permettra de maîtriser l'écosystème Spring réactif dans un contexte réel et évolutif !
