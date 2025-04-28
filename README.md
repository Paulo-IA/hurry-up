![HurryUp Logo](https://sdmntprwestus2.oaiusercontent.com/files/00000000-7a7c-61f8-96a9-4949d8c9320c/raw?se=2025-04-28T01%3A07%3A20Z&sp=r&sv=2024-08-04&sr=b&scid=8195461e-572e-5d1e-879e-b8d79e888779&skoid=b53ae837-f585-4db7-b46f-2d0322fce5a9&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2025-04-27T14%3A29%3A31Z&ske=2025-04-28T14%3A29%3A31Z&sks=b&skv=2024-08-04&sig=OrMPQGRcea3jUUf5vdIA4J3IiAwfYxz3pQBBHwLPMSk%3D)

# HurryUP

## Description
HurryUp is a platform for managing events, allowing users to create, update, and track events with a countdown of the remaining days.

## Requirements

### FR [Functional Requirements]
- The system must allow creating an event
- The system must allow updating an event
- The system must allow deleting an event
- The system must list all events
- The system must retrieve a single event

### BR [Business Rules]
- The system will calculate the days remaining until the event
- Each event must include a name, description, date, and image, with image and description as optional fields
- The event list must support filtering by title or description

### NFR [Non Functional Requirements]
- All data must be persisted in a PostgreSQL Database
- The API must be consumed by a frontend

## Future Features
- Store files at s3 bucket
- Auto-Description with AI Assistant 