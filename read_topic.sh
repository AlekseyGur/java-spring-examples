docker exec -it kafka kafka-console-consumer \
    --bootstrap-server PLAINTEXT://localhost:9092 \
    --topic kafka-demo-1 \
    --from-beginning