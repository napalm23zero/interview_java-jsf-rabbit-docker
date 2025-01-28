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

# Consumer Dockerfile
FROM maven:3.8-openjdk-17-slim

LABEL maintainer="Rodrigo Dantas"
LABEL email="rodrigo.dantas@hustletech.dev"

# Installing essential tools for a proper dev environment
RUN apt-get update && \
    apt-get install -y curl git python3 python3-pip && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Install Node.js LTS version from Nodesource
RUN curl -fsSL https://deb.nodesource.com/setup_lts.x | bash - && \
    apt-get install -y nodejs && \
    npm install -g npm@latest && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Verify installations
RUN java -version && \
    mvn -version && \
    node -v && \
    npm -v

# Set working directory
WORKDIR /workspace

# Expose the default port for the consumer
EXPOSE ${PRODUCER_INTERNAL_PORT} ${CONSUMER_INTERNAL_PORT}

# Command to keep the container alive during dev
CMD ["tail", "-f", "/dev/null"]
