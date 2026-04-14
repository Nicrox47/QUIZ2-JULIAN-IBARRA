# Quiz 2 - Doctor / Hospital / Security

## Lo implementado
- Roles válidos: `ADMIN` y `DOCTOR`
- CRUD completo de hospitales
- CRUD completo de doctores
- Validación para que el `username` usado en un doctor pertenezca a un usuario con rol `DOCTOR`
- Endpoint `GET /api/doctor/me` que usa el usuario autenticado del token para devolver la información del doctor y su hospital
- Seguridad con JWT
- Base de datos H2 en memoria

## Rutas principales
### Auth
- `POST /api/auth/signup`
- `POST /api/auth/login`

### Hospital
- `POST /api/hospitals`
- `GET /api/hospitals`
- `GET /api/hospitals/{id}`
- `PUT /api/hospitals/{id}`
- `DELETE /api/hospitals/{id}`

### Doctor
- `POST /api/doctors`
- `GET /api/doctors`
- `GET /api/doctors/{id}`
- `PUT /api/doctors/{id}`
- `DELETE /api/doctors/{id}`
- `GET /api/doctor/me`

## Flujo recomendado para probar
1. Crear un `ADMIN`
2. Crear un usuario con rol `DOCTOR`
3. Hacer login con el admin
4. Crear un hospital
5. Crear el doctor usando el `username` del usuario DOCTOR
6. Hacer login con el doctor
7. Consumir `GET /api/doctor/me`

## Ejemplo signup
```json
{
  "username": "admin1",
  "password": "Admin#123",
  "role": "ADMIN"
}
```

```json
{
  "username": "doctor1",
  "password": "Doctor#123",
  "role": "DOCTOR"
}
```

## Ejemplo crear hospital
```json
{
  "name": "Hospital Central"
}
```

## Ejemplo crear doctor
```json
{
  "username": "doctor1",
  "name": "Juan",
  "lastName": "Montoya",
  "age": 28,
  "specialization": "Cardiología",
  "hospitalId": 1
}
```
