FROM maven:3.6.3-jdk-11-slim

COPY /compose/production/backend/entrypoint.sh /app/entrypoint.sh
RUN apt-get update && apt-get install dos2unix && dos2unix /compose/production/backend/entrypoint.sh && chmod +x /compose/production/backend/entrypoint.sh

#Start application
WORKDIR /app
ENTRYPOINT ["/app/entrypoint.sh"]
CMD ["bash"]