Jewellery Shop Application
 
A full-stack eCommerce application tailored for a jewellery business. It supports role-based access for Admins, Vendors, and Customers, allowing for product management, cart operations, order placement, secure payment processing, and customer feedback.
 
---
 
Tech Stack
 
- Backend: Spring Boot, Spring Data JPA, REST APIs
- Database: MySQL 
- Validation: Jakarta Validation (Bean Validation)
- Security: JWT (JSON web token)
- ORM: Hibernate
- Tools & Others: Maven, Lombok, Postman
 
---
 User Roles
 
Admin - Monitors the whole appliction
Vendor - Add/update/delete products & categories 
Customer - Browse, add to cart, place orders, make payments, provide feedback
 
---
 
Entity Relationships (JPA)
 
- User ↔ Customer: One-to-One
- Customer ↔ Cart: One-to-One
- Customer ↔ Product: One-to-Many
- Customer ↔ Order: One-to-Many
- Cart ↔ CartItems ↔ Product: One-to-Many / Many-to-One
- Category ↔ Product: One-to-Many
- Orders ↔ OrderItems ↔ Product: One-to-Many / Many-to-One
- OrderItem ↔ Feedback: One-to-One
- Orders ↔ Payment: One-to-One
