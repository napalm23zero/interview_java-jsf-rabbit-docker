# ┌─────────────────────────────────────┐
# │  The Dark Side of Devcontainers     │
# │                                     │
# │  For dev and study use ONLY.        │
# │  Deploy this to production?         │
# │  I will personally find you,        │
# │  and trust me, you won’t like it.   │
# │                                     │
# │  *You've been warned, apprentice.*  │
# └─────────────────────────────────────┘

# RabbitMQ Dockerfile
FROM rabbitmq:3-management

LABEL maintainer="Rodrigo Dantas"
LABEL email="rodrigo.dantas@hustletech.dev"

# Expose RabbitMQ ports
EXPOSE ${RABBITMQ_AMQP_INTERNAL_PORT} ${RABBITMQ_MANAGEMENT_INTERNAL_PORT}

CMD ["rabbitmq-server"]
