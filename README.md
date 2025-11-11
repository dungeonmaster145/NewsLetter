# Newsletter Service

A Spring Boot application that sends pre-decided content to subscribers at specified intervals, organized by topics.

## Features

- **Subscriber Management**: Add and manage email subscribers.
- **Topic Management**: Create topics for content segregation.
- **Content Scheduling**: Enter content with specific send times.
- **Subscriptions**: Users subscribe to topics.
- **Automated Email Sending**: Service sends emails to subscribers of a topic at the scheduled time.
- **REST APIs**: Full CRUD operations for all entities.

## Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Database**: H2 (in-memory for development), can be switched to PostgreSQL for production
- **Email**: Spring Mail with SMTP (e.g., Gmail or SendGrid)
- **Scheduling**: Spring's @Scheduled for periodic checks

## Setup Instructions

### Prerequisites

- Java 17
- Maven 3.6+
- Git

### Clone the Repository

```bash
git clone <repository-url>
cd newsletter
```

### Configure Email

Update `src/main/resources/application.properties` with your SMTP settings:

For Gmail:
```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

Note: Use an app password if 2FA is enabled on Gmail.

For SendGrid (free tier):
```
spring.mail.host=smtp.sendgrid.net
spring.mail.port=587
spring.mail.username=apikey
spring.mail.password=your-sendgrid-api-key
```

### Run Tests

```bash
mvn test
```

Tests include unit tests for EmailService, NewsletterScheduler, and integration tests for controllers.

The application will start on `http://localhost:8080`.

H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`)

## API Endpoints

### Subscribers

- `GET /api/subscribers` - Get all subscribers
- `POST /api/subscribers` - Create subscriber (body: `{"email": "user@example.com"}`)
- `GET /api/subscribers/{id}` - Get subscriber by ID
- `DELETE /api/subscribers/{id}` - Delete subscriber

### Topics

- `GET /api/topics` - Get all topics
- `POST /api/topics` - Create topic (body: `{"name": "Tech News"}`)
- `GET /api/topics/{id}` - Get topic by ID
- `DELETE /api/topics/{id}` - Delete topic

### Contents

- `GET /api/contents` - Get all contents
- `POST /api/contents` - Create content (body: `{"topicId": 1, "text": "Content here", "sendTime": "2023-11-12T10:00:00"}`)
- `GET /api/contents/{id}` - Get content by ID
- `DELETE /api/contents/{id}` - Delete content

### Subscriptions

- `GET /api/subscriptions` - Get all subscriptions
- `POST /api/subscriptions` - Create subscription (body: `{"subscriberId": 1, "topicId": 1}`)
- `GET /api/subscriptions/{id}` - Get subscription by ID
- `DELETE /api/subscriptions/{id}` - Delete subscription

## Usage

1. **Add Topics**: Use POST /api/topics to create topics like "Sports", "Tech", etc.
2. **Add Subscribers**: Use POST /api/subscribers to add emails.
3. **Subscribe Users**: Use POST /api/subscriptions to link subscribers to topics.
4. **Schedule Content**: Use POST /api/contents with topicId, text, and future sendTime (ISO format).
5. **Automatic Sending**: The scheduler checks every minute and sends emails when sendTime is reached.

## Deployment

### Heroku

1. Create a Heroku app.
2. Add PostgreSQL add-on: `heroku addons:create heroku-postgresql:hobby-dev`
3. Update `application.properties` for production:
   ```
   spring.datasource.url=${DATABASE_URL}
   spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
   ```
4. Set environment variables for mail.
5. Deploy: `git push heroku main`

## Improvements

- **Authentication**: Add Spring Security for API protection.
- **Frontend**: Build a web UI with React/Angular for easier management.
- **Batch Emails**: Use bulk email services like SendGrid for better deliverability.
- **Retry Mechanism**: Implement retries for failed email sends.
- **Time Zones**: Handle user time zones for scheduling.
- **Unsubscribe**: Add unsubscribe links in emails.
- **Analytics**: Track open rates, etc., with email service integrations.
- **Scalability**: Use Redis for scheduling in distributed environments.
- **Testing**: Add unit and integration tests.

## Pitfalls and Known Issues

- **In-Memory DB**: H2 is for development; data is lost on restart. Use persistent DB in production.
- **Email Limits**: Free SMTP has sending limits (e.g., Gmail: 500/day). Upgrade for high volume.
- **Scheduling Precision**: @Scheduled runs every minute; for exact seconds, use Quartz.
- **Email Deliverability**: Emails may go to spam; use verified domains.
- **No Frontend**: All operations via APIs; requires API client or Postman.
- **No Validation**: Basic setup; add input validation to prevent errors.
- **Time Parsing**: Assumes ISO LocalDateTime; handle parsing errors.
- **Concurrency**: Multiple instances may send duplicate emails; use locks or distributed scheduling.

## Contributing

1. Fork the repo.
2. Create a feature branch.
3. Commit changes.
4. Push and create PR.

## License

MIT License.
