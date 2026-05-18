#!/bin/bash

# Script para iniciar PostgreSQL + Scriptorium localmente

set -e

BLUE='\033[0;34m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${BLUE}═══════════════════════════════════════════════════════${NC}"
echo -e "${BLUE}  🐘 Iniciando PostgreSQL en Docker${NC}"
echo -e "${BLUE}═══════════════════════════════════════════════════════${NC}"

# Ir al directorio raíz del proyecto
cd "$(dirname "$0")"

# Iniciar PostgreSQL
echo -e "\n${YELLOW}→ Levantando contenedor PostgreSQL...${NC}"
docker compose up -d

# Esperar a que PostgreSQL esté listo
echo -e "${YELLOW}→ Esperando a que PostgreSQL esté listo...${NC}"
sleep 5

# Verificar que está sano
max_attempts=30
attempt=0
while [ $attempt -lt $max_attempts ]; do
  if docker exec scriptorium-postgres pg_isready -U postgres -d scriptorium > /dev/null 2>&1; then
    echo -e "${GREEN}✓ PostgreSQL está listo${NC}"
    break
  fi
  attempt=$((attempt + 1))
  echo -e "${YELLOW}  Intento $attempt/$max_attempts...${NC}"
  sleep 1
done

if [ $attempt -eq $max_attempts ]; then
  echo -e "${RED}✗ PostgreSQL no respondió en tiempo${NC}"
  exit 1
fi

# Mostrar información
echo -e "\n${GREEN}═══════════════════════════════════════════════════════${NC}"
echo -e "${GREEN}  ✓ PostgreSQL está corriendo${NC}"
echo -e "${GREEN}═══════════════════════════════════════════════════════${NC}"
echo -e "\n${BLUE}Información de Conexión:${NC}"
echo -e "  Host:     ${GREEN}localhost${NC}"
echo -e "  Port:     ${GREEN}5432${NC}"
echo -e "  Database: ${GREEN}scriptorium${NC}"
echo -e "  User:     ${GREEN}postgres${NC}"
echo -e "  Password: ${GREEN}12345678${NC}"

echo -e "\n${BLUE}Estados de las tablas:${NC}"
docker exec scriptorium-postgres psql -U postgres -d scriptorium -c "\dt" 2>/dev/null | tail -n +3

echo -e "\n${BLUE}Comandos útiles:${NC}"
echo -e "  ${YELLOW}Ver logs:${NC}          docker compose logs -f postgres"
echo -e "  ${YELLOW}Conectar psql:${NC}     docker exec -it scriptorium-postgres psql -U postgres -d scriptorium"
echo -e "  ${YELLOW}Parar:${NC}             docker compose down"
echo -e "  ${YELLOW}Parar + Limpiar:${NC}   docker compose down -v"

echo -e "\n${GREEN}✓ Listo para ejecutar la aplicación Spring Boot${NC}"
echo -e "   Ejecuta: ${YELLOW}cd scriptorium && mvn spring-boot:run${NC}\n"
