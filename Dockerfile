# --- Giai đoạn 1: Build Frontend (nếu có) ---
FROM node:16.14.0-alpine AS frontend-build
WORKDIR /app/frontend
COPY package*.json ./
#RUN npm config set registry https://mirror.pvcb.vn/repository/npm-registry-proxy
#RUN npm install @fortawesome/fontawesome-svg-core @fortawesome/free-solid-svg-icons @fortawesome/react-fontawesome --force
RUN npm install --force
COPY . .
RUN npm run build

# --- Giai đoạn 2: Build Backend ---
FROM openjdk:11-jdk-slim AS data-cms-backend-build
# Metadata
LABEL maintainer="tungdn"
LABEL description="Dự án quản trị dữ liệu"
WORKDIR /app
# Copy các file cần thiết cho Maven
COPY --from=frontend-build /app/frontend/build /app/static
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
# Cấp quyền thực thi cho mvnw
RUN chmod +x /app/mvnw
# Tải dependencies
RUN ./mvnw dependency:go-offline -B
# Copy source code
COPY src src
# Build file JAR
RUN ./mvnw package -Pprod,api-docs -DskipTests

# --- Giai đoạn 3: Tạo image cuối cùng ---
FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=data-cms-backend-build /app/target/*.jar /app/data-cms.jar
EXPOSE 8080
CMD ["java", "-jar", "data-cms.jar"]
