# Environment Monitor Server

This is a Spring Boot server that collect data from environment monitor device and stores it in a PostgreSQL database. Supports multiple environment monitor devices.

## REST API

#### data/range

Request Body:
  - monitor device username
  - start time
  - end time

Get environment data in a date range (data is stored in 15 minutes increment).

#### data/current/{monitorDeviceUsername}

Get the live (most recent within 5 seconds) environment data from monitor device.

#### data/add

Request Body:
  - temperature
  - humidity
  - co2 level
  - measured at
  - monitor device username
  - monitor device password

Add and update the most recent environment data from monitor device.

#### device/register

Request Body:
  - server admin password
  - monitor device username
  - monitor device password

Register an environment monitor device.

#### device/usernames

Get a list of all monitor device usernames.

#### server/auth

Request Body:
  - server admin password

Authenticate the server admin that can access this server.
