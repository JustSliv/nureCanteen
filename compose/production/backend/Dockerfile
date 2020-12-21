FROM maven:3.6.3-jdk-11-slim

COPY /compose/production/backend/entrypoint.sh /app/entrypoint.sh

# Backend
COPY ./compose/production/backend/entrypoint.sh /start_backend
RUN sed -i 's/\r$//g' /start_backend
RUN chmod +x /start_backend

#Start application
WORKDIR /app

ADD /backend /app
