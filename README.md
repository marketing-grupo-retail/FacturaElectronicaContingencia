# Factura Electrónica Contingencia

Este repositorio contiene el código de un módulo Java encargado de procesar las facturas que se encuentran en estado 2 y 3 (facturas que no fueron enviadas a la DIAN) para ser enviadas nuevamente.

## Características principales
- Procesa facturas pendientes de envío (estado 2 y 3).
- Permite reintentar el envío de facturas a la DIAN.
- Implementado en **Java 8**.
- Diseñado para ejecutarse sobre un servidor **WebSphere Application Server 8 (WAS 8)**.

## Estructura del proyecto
El código fuente principal se encuentra en la carpeta `FacturaElectronicaWeb/src/`.

## Requisitos
- Java 8
- Servidor WAS 8

## Uso
1. Clona el repositorio.
2. Configura el entorno Java y el servidor WAS 8.
3. Despliega el módulo y ejecuta el proceso de reenvío de facturas.

---
Para dudas o soporte, contacta al equipo de desarrollo de Grupo Retail.
