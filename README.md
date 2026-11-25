# Cloud Computing e Infraestructura – E-Commerce

## 1. Introducción

En esta práctica se ha preparado el proyecto E-Commerce para ejecutarse mediante contenedores Docker.

Se han utilizado Docker y Docker Compose para iniciar los servicios de la aplicación.

## 2. Servicios utilizados

- **Base de datos:** MariaDB 11.4.
- **Microservicio PSP:** proyecto Java desarrollado en la asignatura de Programación de Servicios y Procesos.
- **Red:** red privada Docker llamada `red_ecommerce`.
- **Volumen:** `datos_mysql`, utilizado para conservar los datos de la base de datos.

## 3. Organización del proyecto

- `AD`: proyecto de Acceso a Datos.
- `PSP`: proyecto de Programación de Servicios y Procesos.
- `database/init`: contiene el archivo SQL de inicialización.
- `Documentacion`: documentación de la práctica.
- `docker-compose.yml`: configuración de los servicios.

## 4. Ejecución

Es necesario tener Docker Desktop instalado y en funcionamiento.

Desde la carpeta principal del proyecto, ejecutar:

```bash
docker compose up --build
```

Este comando construye la imagen del servicio PSP y arranca los contenedores.

## 5. Comprobación

Para comprobar los contenedores:

```bash
docker compose ps
```

Para comprobar las tablas de la base de datos:

```bash
docker exec -it ecomarket_db mariadb -uroot -padmin -e "SHOW TABLES IN ecomarket_db;"
```

Se han comprobado las siguientes tablas:

- categorias
- clientes
- detalle_pedido
- pedidos
- productos

## 6. Persistencia

La base de datos utiliza un volumen Docker llamado `datos_mysql`, que permite conservar los datos aunque se detengan los contenedores.

## 7. Finalizar la ejecución

Para detener los servicios:

```bash
docker compose down
```

Este comando detiene y elimina los contenedores, pero conserva el volumen de datos.
