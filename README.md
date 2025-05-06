![HurryUp Logo](./src/main/resources/static/hurryup-logo-2x1.png)

# HurryUP

## Description
**HurryUp** is a platform for managing events, allowing users to create, update, and track events with a countdown of the remaining days.

## Requirements

### FR [Functional Requirements]
- [ ] The system must allow creating an event
- [x] The system must allow updating an event
- [x] The system must allow deleting an event
- [ ] The system must list all events
- [x] The system must retrieve a single event
- [x] The system must allow to save a event img url

### BR [Business Rules]
- [x] The system will calculate the days remaining until the event
- [x] The system will calculate the working days remaining until the event
- [x] Each event must include a name, description, date, and image, with image and description as optional fields
- [x] The system must allow creating a presigned url to upload the image
- [x] The event list must support filtering by title or description
- [x] The event description must have a maximum of 140 characters
- [x] Create error handling
- [x] The data must be validated

### NFR [Non Functional Requirements]
- [x] All data must be persisted in a PostgreSQL Database
- [ ] The API must be consumed by a frontend
- [ ] Create tests
- [ ] Create Docs

## Future Features
- Store files at s3 bucket
- Auto-Description with AI Assistant 