![HurryUp Logo](./src/main/resources/static/hurryup-logo-2x1.png)

# HurryUP

## Description
**HurryUp** is a platform for managing events, allowing users to create, update, and track events with a countdown of the remaining days.

## Requirements

### FR [Functional Requirements]
- [] The system must allow creating an event
- [] The system must allow updating an event
- [] The system must allow deleting an event
- [x] The system must list all events
- [] The system must retrieve a single event

### BR [Business Rules]
- [] The system will calculate the days remaining until the event
- [] Each event must include a name, description, date, and image, with image and description as optional fields
- [] The event list must support filtering by title or description
- [] Create error handling
- [] The data must be validated

### NFR [Non Functional Requirements]
- [x] All data must be persisted in a PostgreSQL Database
- [] The API must be consumed by a frontend

## Future Features
- Store files at s3 bucket
- Auto-Description with AI Assistant 