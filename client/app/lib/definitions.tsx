export type Airport = {
    id: number;
    code: string
    name: string;
    city: string;
    country: string;
}

export type Flight = {
    id: number;
    flightNumber: string;
    airline: string;
    origin: Airport;
    destination: Airport;
    departureTime: string;
    arrivalTime: string;
    departureTimezone: string;
    arrivalTimezone: string;
    duration: number;
    price: number;
    currency: string;
    availableSeats: number;
}