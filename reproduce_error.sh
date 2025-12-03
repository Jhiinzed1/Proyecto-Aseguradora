#!/bin/bash

# 1. Crear Cliente
echo "Creando Cliente..."
CLIENTE_ID=$(curl -s -X POST http://localhost:8080/clientes/guardar -H "Content-Type: application/json" -d '{"nombre":"Test Error","email":"error@test.com"}' | grep -o '"id":[0-9]*' | head -1 | cut -d':' -f2)
echo "Cliente ID: $CLIENTE_ID"

# 2. Crear TipoPoliza
echo "Creando TipoPoliza..."
TIPO_ID=$(curl -s -X POST http://localhost:8080/tipopolizas/guardar -H "Content-Type: application/json" -d '{"nombre":"Tipo Error","preciobase":100.0}' | grep -o '"id":[0-9]*' | head -1 | cut -d':' -f2)
echo "TipoPoliza ID: $TIPO_ID"

# 3. Crear BienAsegurado
echo "Creando BienAsegurado..."
BIEN_ID=$(curl -s -X POST http://localhost:8080/bienes/guardar -H "Content-Type: application/json" -d "{\"descripcion\":\"Bien Error\",\"valor\":500.0,\"tipoPoliza\":{\"id\":$TIPO_ID}}" | grep -o '"id":[0-9]*' | head -1 | cut -d':' -f2)
echo "BienAsegurado ID: $BIEN_ID"

# 4. Crear Poliza (Aquí esperamos el error)
echo "Creando Poliza..."
curl -v -X POST http://localhost:8080/polizas/guardar -H "Content-Type: application/json" -d "{\"numeroPoliza\":\"POL-ERROR\",\"fechaInicio\":\"2024-01-01\",\"fechaFin\":\"2025-01-01\",\"cliente\":{\"id\":$CLIENTE_ID},\"tipoPoliza\":{\"id\":$TIPO_ID},\"bienAsegurado\":{\"id\":$BIEN_ID}}"
