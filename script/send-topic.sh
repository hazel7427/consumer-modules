KAFKA_CONTAINER_NAME=kafka
BROKER=localhost:9092
TOPIC=chat-enter

echo "💬 보낼 메시지를 입력하세요 (예: {\"roomId\": 123, \"userId\": 456}):"
read MESSAGE

echo "$MESSAGE" | docker exec -i $KAFKA_CONTAINER_NAME kafka-console-producer \
  --broker-list $BROKER \
  --topic $TOPIC

echo "✅ 메시지 전송 완료!"
