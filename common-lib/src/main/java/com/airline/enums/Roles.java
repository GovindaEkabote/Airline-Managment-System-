package com.airline.enums;

public enum Roles {

    SUPER_ADMIN,
    ADMIN,
    AIRLINE_OWNER,
    AIRLINE_ADMIN,
    FLIGHT_OPERATOR,
    SUPPORT_AGENT,
    USER,
    GUEST;

}
/*
| Role              | Main responsibility                                                                                      |
| ----------------- | -------------------------------------------------------------------------------------------------------- |
| `SUPER_ADMIN`     | Full platform control; manage admins, airlines, global configuration, system-wide access                 |
| `ADMIN`           | Manage users, airlines, airports, cities, flights, bookings, and operational data                        |
| `AIRLINE_OWNER`   | Manage their airline, aircraft, routes, flight schedules, fares, and airline staff                       |
| `AIRLINE_ADMIN`   | Day-to-day airline operations; manage flights, schedules, aircraft, fares, and airline users             |
| `FLIGHT_OPERATOR` | Manage flight operations, schedules, aircraft assignments, delays, cancellations, and operational status |
| `SUPPORT_AGENT`   | Handle customer issues, booking problems, cancellations, refunds, and support tickets                    |
| `USER`            | Search flights, make bookings, manage passengers, cancel bookings, view tickets, and manage profile      |
| `GUEST`           | Search/view publicly available flights and airline information without an account                        |

* */